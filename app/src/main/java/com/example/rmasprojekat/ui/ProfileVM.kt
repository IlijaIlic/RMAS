package com.example.rmasprojekat.ui

import android.app.ActivityManager
import android.app.Notification
import android.app.Service
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.rmasprojekat.repositories.UserRepository
import com.example.rmasprojekat.services.NearbyService
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


//GOTOVO
class ProfileVM(private val userRep: UserRepository?) : ViewModel() {


    private val _imeProf = MutableStateFlow("Ime")
    val imeProf: StateFlow<String> = _imeProf.asStateFlow()
    fun updateImeProf(nT: String) {
        _imeProf.value = nT
    }

    private val _prezimeProf = MutableStateFlow("Prezime")
    val prezimeProf: StateFlow<String> = _prezimeProf.asStateFlow()
    fun updatePrezmeProf(nT: String) {
        _prezimeProf.value = nT
    }

    private val _emailProf = MutableStateFlow("email")
    val emailProf: StateFlow<String> = _emailProf.asStateFlow()
    fun updateEmailProf(nT: String) {
        _emailProf.value = nT
    }

    private val _brojTelefonaProf = MutableStateFlow("brojTelefona")
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

    private val _imageURL = MutableStateFlow("///")
    val imageURL: StateFlow<String> = _imageURL.asStateFlow()
    fun updateImgURL(nT: String) {
        _imageURL.value = nT
    }

    fun getUserInfo() {
        viewModelScope.launch {
            val userSnap: QuerySnapshot? = userRep?.getUser()
            val user = userSnap?.documents?.firstOrNull()
            if (user != null) {
                _emailProf.value = user.getString("email").toString()
                _imeProf.value = user.getString("ime").toString()
                _prezimeProf.value = user.getString("prezime").toString()
                _brojTelefonaProf.value = user.getString("brTel").toString()
                _imageURL.value = user.get("slika").toString()
                _serviceCheckedProf.value = user.getBoolean("servis")!!
            }
        }

    }

    fun getUserTF(): FirebaseUser? {
        return userRep?.getUserTF()
    }


    fun serviceFunction(context: Context) {
        viewModelScope.launch {
            userRep?.changeServiceAllowed(_serviceCheckedProf.value)
        }
        val intent = Intent(context, NearbyService::class.java)
        if (_serviceCheckedProf.value) {
            //poslati false u FIRESTORE
            //ne stopira
            context.stopService(intent)
        } else {
            //poslati true u FIRESTORE
            ContextCompat.startForegroundService(context, intent)
        }
    }
}

class ProfileVMFactory(private val userRep: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileVM::class.java)) {
            return ProfileVM(userRep) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}