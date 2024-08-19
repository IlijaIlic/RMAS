package com.example.rmasprojekat.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


//GOTOVO
class ProfileVM : ViewModel() {


    private val _imeProf = MutableStateFlow("Ilija")
    val imeProf: StateFlow<String> = _imeProf.asStateFlow()
    fun updateImeProf(nT: String) {
        _imeProf.value = nT
    }

    private val _prezimeProf = MutableStateFlow("Ilic")
    val prezimeProf: StateFlow<String> = _prezimeProf.asStateFlow()
    fun updatePrezmeProf(nT: String) {
        _prezimeProf.value = nT
    }

    private val _emailProf = MutableStateFlow("ilic.ilija002@gmail.com")
    val emailProf: StateFlow<String> = _emailProf.asStateFlow()
    fun updateEmailProf(nT: String) {
        _emailProf.value = nT
    }

    private val _brojTelefonaProf = MutableStateFlow("+38161615141")
    val brojTelefonaProf: StateFlow<String> = _brojTelefonaProf.asStateFlow()
    fun updateBrojTelefonaPRof(nT: String) {
        _brojTelefonaProf.value = nT
    }

    private val _brojBodovaProf = MutableStateFlow(150)
    val brojBodovaProf: StateFlow<Int> = _brojBodovaProf.asStateFlow()
    fun updateBrojBodovaProf(nb: Int) {
        _brojBodovaProf.value = nb
    }


    private val _serviceCheckedProf = MutableStateFlow(false)
    val serviceCheckedProf: StateFlow<Boolean> = _serviceCheckedProf.asStateFlow()
    fun updateServiceCheckedProf(bl: Boolean) {
        _serviceCheckedProf.value = bl
    }

}