package com.example.rmasprojekat.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rmasprojekat.classes.UserClass
import com.example.rmasprojekat.repositories.OglasRepository
import com.example.rmasprojekat.repositories.UserRepository
import com.google.firebase.firestore.getField
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsersVM(private val usRep: UserRepository?) : ViewModel() {

    private val _usrUID = MutableStateFlow<String>("")
    val usrUID: StateFlow<String> = _usrUID.asStateFlow()

    private val _users = MutableStateFlow<List<UserClass>?>(null)
    val users: StateFlow<List<UserClass>?> = _users.asStateFlow()

    fun getAllUsers() {
        viewModelScope.launch {
            val users = usRep?.getAllUsers()

            val usersList = users?.documents?.map { usr ->
                val ime = usr.getString("ime") ?: ""
                val prezime = usr.getString("prezime") ?: ""
                val bodovi = usr.getField<Int>("bodovi") ?: 0
                val uid = usr.getString("uid") ?: ""


                UserClass(ime, prezime, uid, bodovi)
            } ?: emptyList()

            _users.value = usersList.sortedByDescending { it.bod }
        }
    }

    fun getCurrentUID() {
        _usrUID.value = usRep?.getUserTF()?.uid.toString()
    }
}

class UsersVMFactory(private val usRep: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersVM::class.java)) {
            return UsersVM(usRep) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}
