package com.trusov.memegame.presentation.sign_up_fragment

sealed class SignUpState

class Error(
    val message: String
) : SignUpState()
object Success : SignUpState()
