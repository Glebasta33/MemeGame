package com.trusov.memegame.presentation.new_game_fragment.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.trusov.memegame.App
import com.trusov.memegame.R
import com.trusov.memegame.databinding.FragmentCreateNewGameBinding
import com.trusov.memegame.domain.use_case.CreateNewGameUseCase
import javax.inject.Inject

class CreateNewGameFragment : Fragment() {

    private var _binding: FragmentCreateNewGameBinding? = null
    private val binding: FragmentCreateNewGameBinding
        get() = _binding ?: throw RuntimeException("FragmentCreateNewGameBinding is null")

    @Inject
    lateinit var createNewGameUseCase: CreateNewGameUseCase

    override fun onAttach(context: Context) {
        (activity?.application as App).component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateNewGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
    }

    private fun setListener() {
        with(binding) {
            btnCreateNewGame.setOnClickListener {
                if (etNameOfGame.text.isNullOrEmpty()) {
                    Toast.makeText(activity, "Введите название", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (etPasswordOfGame.text.isNullOrEmpty()) {
                    Toast.makeText(activity, "Введите пароль", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val title = etNameOfGame.text.toString()
                val password = etPasswordOfGame.text.toString()
                    try {
                        createNewGameUseCase(title, password)
                        val args = Bundle().apply {
                            putString("password", password)
                        }
                        findNavController().navigate(R.id.action_createNewGameFragment_to_memesFragment, args)
                    } catch (e: Exception) {
                        Log.d("CreateNewGameTest", "${e.message}")
                        e.printStackTrace()
                    }


            }
        }
    }
}