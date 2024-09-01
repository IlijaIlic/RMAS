package com.example.rmasprojekat.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.rmasprojekat.repositories.UserRepository
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class RegisterVM(private val userRep: UserRepository?) : ViewModel() {

    private val _emailRegister = MutableStateFlow("")
    val emailRegister: StateFlow<String> = _emailRegister.asStateFlow()
    fun updateEmailTp(nT: String) {
        _emailRegister.value = nT
        okToRegister()
    }

    private val _passwordRegister = MutableStateFlow("")
    val passwordRegister: StateFlow<String> = _passwordRegister.asStateFlow()
    fun updatePasswordTp(nT: String) {
        _passwordRegister.value = nT
        okToRegister()
    }

    private val _phoneNumberRegister = MutableStateFlow("")
    val phoneNumberRegister: StateFlow<String> = _phoneNumberRegister.asStateFlow()
    fun updatePhoneTp(nT: String) {
        _phoneNumberRegister.value = nT
        okToRegister()
    }

    private val _imeRegister = MutableStateFlow("")
    val imeRegister: StateFlow<String> = _imeRegister.asStateFlow()
    fun updateImeTp(nT: String) {
        _imeRegister.value = nT
        okToRegister()
    }

    private val _prezimeRegister = MutableStateFlow("")
    val prezimeRegister: StateFlow<String> = _prezimeRegister.asStateFlow()
    fun updatePrezimeTp(nT: String) {
        _prezimeRegister.value = nT
        okToRegister()
    }

    private val _showPasswordRegister = MutableStateFlow(false)
    val showPasswordRegister: StateFlow<Boolean> = _showPasswordRegister.asStateFlow()
    fun updateShowPasswordLogin(bl: Boolean) {
        _showPasswordRegister.value = bl
    }

    private val _imageTaken = MutableStateFlow<File?>(null)
    val imageTaken: StateFlow<File?> = _imageTaken.asStateFlow()
    fun updateFile(fl: File) {
        _imageTaken.value = fl
        okToRegister()
    }

    private val _okImage = MutableStateFlow<Boolean>(false)
    val okImage: StateFlow<Boolean> = _okImage.asStateFlow()
    fun updateOkImage(bl: Boolean) {
        _okImage.value = bl
        okToRegister()
    }

    private val _firebaseOk = MutableStateFlow<Boolean>(false)
    val firebaseOk: StateFlow<Boolean> = _firebaseOk.asStateFlow()


    private val _loginAttempted = MutableStateFlow(false)
    val loginAttempted: StateFlow<Boolean> = _loginAttempted.asStateFlow()
    fun resetLoginAttempt() {
        _loginAttempted.value = false
    }


    fun createUser() {
        viewModelScope.launch {
            try {
                val user = userRep?.createUser(
                    _emailRegister.value,
                    _passwordRegister.value,
                    _imeRegister.value,
                    _prezimeRegister.value,
                    _phoneNumberRegister.value,
                    _imageTaken.value
                )
                _firebaseOk.value = (user?.uid != null)
            } catch (e: Exception) {
                _firebaseOk.value = false
            } finally {
                _loginAttempted.value = true
            }
        }
    }

    private val _cameraEvent = MutableStateFlow(false)
    val cameraEvent: StateFlow<Boolean> = _cameraEvent

    fun onOpenCamera() {
        _cameraEvent.value = true
    }

    fun onCameraEventHandled() {
        _cameraEvent.value = false
    }

    fun bitmapToFile(context: Context, bitmap: Bitmap): File? {
        val file = File(context.cacheDir, "image_${System.currentTimeMillis()}.jpg")
        return try {
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            _okImage.value = true
            file
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private val _okToRegister = MutableStateFlow<Boolean>(false)
    val okToRegister: StateFlow<Boolean> = _okToRegister.asStateFlow()
    fun okToRegister() {
        if (_emailRegister.value.isNotEmpty()
            && _imeRegister.value.isNotEmpty()
            && _passwordRegister.value.isNotEmpty()
            && _prezimeRegister.value.isNotEmpty()
            && _phoneNumberRegister.value.isNotEmpty()
            && okImage.value
        ) {
            _okToRegister.value = true

        } else {
            _okToRegister.value = false
        }

    }
}

class RegisterVMFactory(private val userRep: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterVM::class.java)) {
            return RegisterVM(userRep) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}