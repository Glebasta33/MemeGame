package com.trusov.memegame.presentation.memes_fragment.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.trusov.memegame.App
import com.trusov.memegame.databinding.FragmentMemesBinding
import com.trusov.memegame.di.ViewModelFactory
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

    override fun onAttach(context: Context) {
        (activity?.application as App).component.inject(this)
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
        viewModel.getMemes()
    }
}