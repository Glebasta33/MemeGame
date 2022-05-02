package com.trusov.memegame.presentation.meme_fullscreen_fragment.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.trusov.memegame.App
import com.trusov.memegame.databinding.FragmentMemeFullsreenBinding
import com.trusov.memegame.di.ViewModelFactory
import com.trusov.memegame.presentation.meme_fullscreen_fragment.view_model.MemeFullScreenViewModel
import javax.inject.Inject

class MemeFullScreenFragment : Fragment() {

    private var _binding: FragmentMemeFullsreenBinding? = null
    private val binding: FragmentMemeFullsreenBinding
        get() = _binding ?: throw RuntimeException("FragmentMemeFullsreenBinding is null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MemeFullScreenViewModel::class.java]
    }

    private lateinit var memeUrl: String

    override fun onAttach(context: Context) {
        (activity?.application as App).component.inject(this)
        arguments?.let { args ->
            memeUrl = args.getString(MEME_URL).toString()
        }
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMemeFullsreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Picasso.get().load(memeUrl).into(binding.ivMemeFullscreen)
        viewModel.chooseMeme(memeUrl)
    }

    companion object {
        private const val MEME_URL = "MEME_URL"

        fun createNewInstance(url: String): MemeFullScreenFragment {
            return MemeFullScreenFragment().apply {
                arguments = Bundle().apply {
                    putString(MEME_URL, url)
                }
            }
        }
    }

}