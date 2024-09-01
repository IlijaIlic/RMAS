package com.example.rmasprojekat.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rmasprojekat.repositories.OglasRepository
import com.example.rmasprojekat.repositories.UserRepository
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Locale

//GOTOV OSIM DATEPICKER (moze da se bira datum iz proslosti)
class AddPlaceVM(private val oglasRep: OglasRepository?) : ViewModel() {

    private val _opisAdd = MutableStateFlow("")
    val opisAdd: StateFlow<String> = _opisAdd.asStateFlow()
    fun updateOpisTp(nT: String) {
        _opisAdd.value = nT
        okToAddPlace()
    }

    private val _selTextAdd = MutableStateFlow("Odaberite radnju")
    val selTextAdd: StateFlow<String> = _selTextAdd.asStateFlow()
    fun updateSelText(nT: String) {
        _selTextAdd.value = nT
        okToAddPlace()
    }

    private val _isExpandedAdd = MutableStateFlow(false)
    val isExpandedAdd: StateFlow<Boolean> = _isExpandedAdd.asStateFlow()
    fun updateIsExpanded(bl: Boolean) {
        _isExpandedAdd.value = bl
    }

    private val _showDateAdd = MutableStateFlow(false)
    val showDateAdd: StateFlow<Boolean> = _showDateAdd.asStateFlow()
    fun updateShowDate(bl: Boolean) {
        _showDateAdd.value = bl
        okToAddPlace()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private val _dateState = mutableStateOf(
        DatePickerState(
            Locale("bs", "BA", "LAT"),
            initialSelectedDateMillis = System.currentTimeMillis()
        )
    )

    @OptIn(ExperimentalMaterial3Api::class)
    val dateState: MutableState<DatePickerState> = _dateState

    //Locale("sr", "RS", "LATN") ne RADI
    @OptIn(ExperimentalMaterial3Api::class)
    fun getFormattedDate(): String {
        val selectedDateMillis = dateState.value.selectedDateMillis
        return if (selectedDateMillis != null) {
            val sdf = SimpleDateFormat("dd. MMMM yyyy.", Locale("bs", "BS", "LAT"))
            sdf.format(selectedDateMillis)
        } else {
            val sdf = SimpleDateFormat("dd. MMMM yyyy.", Locale("bs", "BS", "LAT"))
            sdf.format(System.currentTimeMillis())
        }
    }

    private val _userLocation = MutableStateFlow<LatLng?>(null)
    val userLocation: StateFlow<LatLng?> = _userLocation.asStateFlow()

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(context: Context) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        Log.w("PERMISIJA", "IMA ")
        fusedLocationClient.lastLocation.addOnSuccessListener { loc ->
            loc?.let { _userLocation.value = LatLng(it.latitude, it.longitude) }
        }
    }


    private val _uploadSuc = MutableStateFlow<Boolean>(false)
    val uploadSuc: StateFlow<Boolean> = _uploadSuc.asStateFlow()

    fun onAddPlace(context: Context) {
        Log.w("TEST", "U onAddPlace")
        viewModelScope.launch {
            _uploadSuc.value =
                oglasRep!!.createOglas(
                    prodavnica = _selTextAdd.value,
                    datumIsteka = getFormattedDate(),
                    slika = _imageTaken.value,
                    lokacija = _userLocation.value,
                    opis = _opisAdd.value
                )
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

    private val _okImage = MutableStateFlow<Boolean>(false)
    val okImage: StateFlow<Boolean> = _okImage.asStateFlow()
    fun updateOkImage(bl: Boolean) {
        _okImage.value = bl
        okToAddPlace()
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

    private val _okToAdd = MutableStateFlow<Boolean>(false)
    val okToAdd: StateFlow<Boolean> = _okToAdd.asStateFlow()
    fun okToAddPlace() {
        if (_opisAdd.value.isNotEmpty()
            && _selTextAdd.value.isNotEmpty()
            && getFormattedDate().isNotEmpty()
            && okImage.value
        ) {
            _okToAdd.value = true

        } else {
            _okToAdd.value = false
        }

    }

    private val _imageTaken = MutableStateFlow<File?>(null)
    val imageTaken: StateFlow<File?> = _imageTaken.asStateFlow()
    fun updateFile(fl: File) {
        _imageTaken.value = fl
        okToAddPlace()
    }
}

class AddPlaceVMFactory(private val oglasRep: OglasRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddPlaceVM::class.java)) {
            return AddPlaceVM(oglasRep) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}