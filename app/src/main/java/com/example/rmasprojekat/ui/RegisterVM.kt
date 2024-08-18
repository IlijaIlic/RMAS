package com.example.rmasprojekat.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RegisterVM : ViewModel() {

    private val _emailRegister = MutableStateFlow("")
    val emailRegister: StateFlow<String> = _emailRegister.asStateFlow()
    fun updateEmailTp(nT: String) {
        _emailRegister.value = nT
    }

    private val _passwordRegister = MutableStateFlow("")
    val passwordRegister: StateFlow<String> = _passwordRegister.asStateFlow()
    fun updatePasswordTp(nT: String) {
        _passwordRegister.value = nT
    }

    private val _phoneNumberRegister = MutableStateFlow("")
    val phoneNumberRegister: StateFlow<String> = _phoneNumberRegister.asStateFlow()
    fun updatePhoneTp(nT: String) {
        _phoneNumberRegister.value = nT
    }

    private val _imeRegister = MutableStateFlow("")
    val imeRegister: StateFlow<String> = _imeRegister.asStateFlow()
    fun updateImeTp(nT: String) {
        _imeRegister.value = nT
    }

    private val _prezimeRegister = MutableStateFlow("")
    val prezimeRegister: StateFlow<String> = _prezimeRegister.asStateFlow()
    fun updatePrezimeTp(nT: String) {
        _prezimeRegister.value = nT
    }

    private val _showPasswordRegister = MutableStateFlow(false)
    val showPasswordRegister: StateFlow<Boolean> = _showPasswordRegister.asStateFlow()
    fun updateShowPasswordLogin(bl: Boolean) {
        _showPasswordRegister.value = bl
    }
}