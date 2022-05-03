package com.trusov.memegame.presentation.memes_fragment.fragment

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.trusov.memegame.App
import com.trusov.memegame.databinding.PlayersTableFragmentBinding
import com.trusov.memegame.di.ViewModelFactory
import com.trusov.memegame.domain.entity.Meme
import com.trusov.memegame.presentation.memes_fragment.adapter.PlayersCardAdapter
import javax.inject.Inject

class PlayersTableFragment : Fragment() {

    private var _binding: PlayersTableFragmentBinding? = null
    private val binding: PlayersTableFragmentBinding
        get() = _binding ?: throw RuntimeException("FragmentPlayersTableBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        )[PlayersTableViewModel::class.java]
    }


    override fun onAttach(context: Context) {
        (activity?.application as App).component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PlayersTableFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvQuestion.text = "Текст вопроса "
        val memesAdapter = PlayersCardAdapter()
        binding.rvCardsOnTable.adapter = memesAdapter
        binding.rvCardsOnTable.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        arguments?.let { arguments ->
            val gameId = arguments.getString(GAME_ID) ?: ""
            viewModel.getPlayers(gameId).observe(viewLifecycleOwner) { players ->
                if (players.find { it.chosenMemeUrl == null } == null) {
                    players.toMutableList().shuffle()
                    memesAdapter.submitList(players)
                }
            }
        }
    }

    companion object {
        private const val GAME_ID = "GAME_ID"

        fun getInstance(gameId: String): PlayersTableFragment {
            return PlayersTableFragment().apply {
                arguments = Bundle().apply {
                    putString(GAME_ID, gameId)
                }
            }
        }
    }

}