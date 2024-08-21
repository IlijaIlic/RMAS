package com.example.rmasprojekat.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rmasprojekat.ds.UserDS
import com.example.rmasprojekat.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

//GOTOVO
class LoginVM(private val userRep: UserRepository?) : ViewModel() {


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

    fun createUser() {
        viewModelScope.launch { userRep?.createUser(_emailLogin.value, _passwordLogin.value) }
    }

}

class LoginVMFactory(private val userRep: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginVM::class.java)) {
            return LoginVM(userRep) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}