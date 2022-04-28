package com.trusov.memegame.presentation.sign_up_fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.trusov.memegame.App
import com.trusov.memegame.R
import com.trusov.memegame.databinding.FragmentSignUpBinding
import com.trusov.memegame.di.ViewModelFactory
import javax.inject.Inject


class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding: FragmentSignUpBinding
        get() = _binding ?: throw RuntimeException("FragmentSignUpBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        )[SignUpViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.applicationContext as App).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            buttonSignUp.setOnClickListener {
                val name = etName.text.toString()
                val email = etEmail.text.toString()
                val password1 = etPassword.text.toString()
                val password2 = etPassword2.text.toString()
                viewModel.signUp(name, email, password1, password2)
            }
        }
        viewModel.state.observe(viewLifecycleOwner) { state ->
            with(binding) {
                buttonSignUp.isEnabled = true
                progress.isGone = true
                when(state) {
                    is Loading -> {
                        buttonSignUp.isEnabled = false
                        progress.isGone = false
                    }
                    is Error -> {
                        Toast.makeText(activity, state.message, Toast.LENGTH_SHORT).show()
                    }
                    is Success -> {
                        findNavController().navigate(R.id.action_signUpFragment_to_gamesHubFragment)
                    }
                }
            }

        }
    }
}