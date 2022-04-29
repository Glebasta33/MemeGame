package com.trusov.memegame.presentation.memes_fragment.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.trusov.memegame.App
import com.trusov.memegame.R
import com.trusov.memegame.databinding.FragmentMemesBinding
import com.trusov.memegame.di.ViewModelFactory
import com.trusov.memegame.domain.entity.Player
import com.trusov.memegame.presentation.games_fragment.adapter.PlayerAdapter
import com.trusov.memegame.presentation.memes_fragment.adapter.MemesAdapter
import com.trusov.memegame.presentation.memes_fragment.view_model.MemesViewModel
import javax.inject.Inject

class MemesFragment : Fragment() {

    private var _binding: FragmentMemesBinding? = null
    private val binding: FragmentMemesBinding
        get() = _binding ?: throw RuntimeException("FragmentMemesBinding is null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MemesViewModel::class.java]
    }

    private lateinit var password: String

    override fun onAttach(context: Context) {
        (activity?.application as App).component.inject(this)
        viewModel.getMemes()
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { password = it.getString("password") ?: "password" }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMemesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val playerAdapter = PlayerAdapter()
        binding.rvPlayers.adapter = playerAdapter
        binding.rvPlayers.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        viewModel.getGame(password).observe(viewLifecycleOwner) { game ->
            game?.let {
                binding.tvGameTitle.text = game.title
                viewModel.getPlayers(game.id).observe(viewLifecycleOwner) {
                    playerAdapter.players.clear()
                    playerAdapter.players.addAll(it.toMutableList())
                    playerAdapter.notifyDataSetChanged()
                }

            }

        }
        val memesAdapter = MemesAdapter()
        binding.rvMemes.adapter = memesAdapter
        binding.rvMemes.layoutManager = GridLayoutManager(activity, 2)
        viewModel.memes.observe(viewLifecycleOwner) { memes ->
            memesAdapter.submitList(memes)
        }
        memesAdapter.onMemeClickListener = { meme ->
            val memeArg = Bundle().apply {
                putString("MEME_URL", meme.imageUrl)
            }
            findNavController().navigate(
                R.id.action_memesFragment_to_memeFullScreenFragment,
                memeArg
            )
            viewModel.getNewMeme(meme)
        }
        binding.buttonGetQuestion.setOnClickListener {
            viewModel.getRandomQuestion()
            binding.cardQuestion.isGone = false
        }
        binding.ivCloseQuestion.setOnClickListener {
            binding.cardQuestion.isGone = true
        }
        binding.progressBar.isGone = true
        viewModel.question.observe(viewLifecycleOwner) { question ->
            binding.textView.text = question
        }

    }
}