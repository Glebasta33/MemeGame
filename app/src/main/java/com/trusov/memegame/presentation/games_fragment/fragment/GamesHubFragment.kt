package com.trusov.memegame.presentation.games_fragment.fragment

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.trusov.memegame.App
import com.trusov.memegame.R
import com.trusov.memegame.databinding.FragmentGamesHubBinding
import com.trusov.memegame.di.ViewModelFactory
import com.trusov.memegame.presentation.games_fragment.adapter.GameAdapter
import com.trusov.memegame.presentation.games_fragment.view_model.GamesHubViewModel
import javax.inject.Inject

class GamesHubFragment : Fragment() {

    private var _binding: FragmentGamesHubBinding? = null
    private val binding: FragmentGamesHubBinding
        get() = _binding ?: throw RuntimeException("FragmentGamesHubBinding is null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GamesHubViewModel::class.java]
    }

    private lateinit var name: String

    override fun onAttach(context: Context) {
        (activity?.application as App).component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGamesHubBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkName()

        val gameAdapter = GameAdapter()
        binding.rvGamse.adapter = gameAdapter
        viewModel.getListOfGames().observe(viewLifecycleOwner) {
            gameAdapter.submitList(it.toMutableList())
        }
        setListeners(gameAdapter)
    }

    private fun setListeners(gameAdapter: GameAdapter) {
        binding.floatingActionButtonCreateNewGame.setOnClickListener {
            findNavController().navigate(R.id.action_gamesHubFragment_to_createNewGameFragment)
        }
        gameAdapter.onGameClickListener = {
            binding.cardQuestion.isGone = false
            binding.tvWelcome.text = "Введите пароль"
            binding.buttonEnter.setOnClickListener {
                val password = binding.etInput.text.toString()
                viewModel.registerToGame(name, password)
                binding.cardQuestion.isGone = true
                viewModel.auth.observe(viewLifecycleOwner) {
                    if (it) {
                        val args = Bundle().apply {
                            putString("password", password)
                        }
                        findNavController().navigate(R.id.action_gamesHubFragment_to_memesFragment, args)
                    } else {
                        Toast.makeText(activity, "Пароль введён неверно", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }

    private fun checkName() {
        val prefs = activity?.getPreferences(MODE_PRIVATE)
        if (prefs?.getString(PREFERENCES_NAME, null) == null) {
            binding.cardQuestion.isGone = false
            binding.tvWelcome.text = "Введите ваше имя"
            binding.buttonEnter.setOnClickListener {
                val name = binding.etInput.text.toString()
                prefs?.edit()?.putString(PREFERENCES_NAME, name)?.apply()
                binding.cardQuestion.isGone = true
            }
        } else {
            name = prefs.getString(PREFERENCES_NAME, "") ?: ""
        }
    }

    companion object {
        private const val PREFERENCES_NAME = "NAME"
    }

}