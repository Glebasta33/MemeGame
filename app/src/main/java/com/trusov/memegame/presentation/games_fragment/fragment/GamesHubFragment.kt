package com.trusov.memegame.presentation.games_fragment.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.trusov.memegame.App
import com.trusov.memegame.databinding.FragmentGamesHubBinding
import com.trusov.memegame.di.ViewModelFactory
import com.trusov.memegame.presentation.games_fragment.adapter.GameAdapter
import com.trusov.memegame.presentation.games_fragment.view_model.GamesHubViewModel
import com.trusov.memegame.presentation.util.NavigationController
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

    private lateinit var controller: NavigationController

    override fun onAttach(context: Context) {
        (activity?.application as App).component.inject(this)
        if (context is NavigationController) {
            controller = context
        }
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
        val gameAdapter = GameAdapter()
        binding.rvGamse.adapter = gameAdapter
        viewModel.getListOfGames().observe(viewLifecycleOwner) {
            gameAdapter.submitList(it.toMutableList())
        }
    }
}