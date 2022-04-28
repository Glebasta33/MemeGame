package com.trusov.memegame.presentation.sign_up_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.trusov.memegame.domain.use_case.SignUpUseCase
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _state = MutableLiveData<SignUpState>()
    val state: LiveData<SignUpState> = _state

    fun signUp(name: String, email: String, password1: String, password2: String) {
        _state.value = Loading
        if (!checkInputs(name, email, password1, password2)) return
        if (!checkPasswords(password1, password2)) return
        signUpUseCase(name, email, password1)
        if (auth.currentUser == null) {
            _state.value = Error("Не удалось зарегистрироваться")
        } else {
            _state.value = Success
        }
    }

    private fun checkInputs(vararg inputs: String): Boolean {
        for (input in inputs) {
            if(input.isEmpty()) {
                _state.value = Error("Заполните все поля")
                return false
            }
        }
        return true
    }

    private fun checkPasswords(password1: String, password2: String): Boolean {
        if (password1.length < 6) {
            _state.value = Error("Заполните все поля")
            return false
        }
        if (password1 != password2) {
            _state.value = Error("Введённые пароли отличаются")
            return false
        }
        return true
    }

}