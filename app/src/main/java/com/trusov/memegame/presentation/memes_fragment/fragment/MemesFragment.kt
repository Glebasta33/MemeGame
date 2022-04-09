package com.trusov.memegame.presentation.memes_fragment.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.trusov.memegame.App
import com.trusov.memegame.databinding.FragmentMemesBinding
import com.trusov.memegame.di.ViewModelFactory
import com.trusov.memegame.presentation.memes_fragment.adapter.MemesAdapter
import com.trusov.memegame.presentation.memes_fragment.view_model.MemesViewModel
import com.trusov.memegame.presentation.util.NavigationController
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

    private lateinit var controller: NavigationController

    override fun onAttach(context: Context) {
        (activity?.application as App).component.inject(this)
        if (context is NavigationController) {
            controller = context
        }
        viewModel.getMemes()
        super.onAttach(context)
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
        val memesAdapter = MemesAdapter()
        binding.rvMemes.adapter = memesAdapter
        binding.rvMemes.layoutManager = GridLayoutManager(activity, 2)
        viewModel.memes.observe(viewLifecycleOwner) { memes ->
            memesAdapter.submitList(memes)
        }
        memesAdapter.onMemeClickListener = { meme ->
            controller.launchMemeFullScreenFragment(meme)
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