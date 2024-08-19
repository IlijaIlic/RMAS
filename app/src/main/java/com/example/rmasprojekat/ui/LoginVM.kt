package com.example.rmasprojekat.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

//GOTOVO
class LoginVM() : ViewModel() {

    private val _emailLogin = MutableStateFlow("")
    val emailLogin: StateFlow<String> = _emailLogin.asStateFlow()
    fun updateEmailTp(nT: String) {
        _emailLogin.value = nT
    }

    private val _passwordLogin = MutableStateFlow("")
    val passwordLogin: StateFlow<String> = _passwordLogin.asStateFlow()
    fun updatePasswordTp(nT: String) {
        _passwordLogin.value = nT
    }

    private val _showPasswordLogin = MutableStateFlow(false)
    val showPasswordLogin: StateFlow<Boolean> = _showPasswordLogin.asStateFlow()
    fun updateShowPasswordLogin(bl: Boolean) {
        _showPasswordLogin.value = bl
    }
}