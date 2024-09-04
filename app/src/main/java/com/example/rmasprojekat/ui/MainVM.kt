package com.example.rmasprojekat.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rmasprojekat.classes.Sale
import com.example.rmasprojekat.repositories.OglasRepository
import com.example.rmasprojekat.repositories.UserRepository
import com.example.rmasprojekat.services.NearbyService
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.getField
import com.google.maps.android.compose.CameraPositionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//GOTOVO OSIM CAMERA POSITION
class MainVM(
    private val oglasRep: OglasRepository?,
    private val userRep: UserRepository?
) :
    ViewModel() {


    private val _sliderPosMain = MutableStateFlow(0f)
    val slidePosMain: StateFlow<Float> = _sliderPosMain.asStateFlow()
    fun updateSlidePos(pos: Float) {
        _sliderPosMain.value = pos
    }

    private val _openDialogMain = MutableStateFlow(false)
    val openDialogMain: StateFlow<Boolean> = _openDialogMain.asStateFlow()
    fun updateOpenDialog(bl: Boolean) {
        _openDialogMain.value = bl
    }

    private val _showDateMain = MutableStateFlow(false)
    val showDateMain: StateFlow<Boolean> = _showDateMain.asStateFlow()
    fun updateShowDate(bl: Boolean) {
        _showDateMain.value = bl
    }

    private val _isExpandedMain = MutableStateFlow(false)
    val isExpandedMain: StateFlow<Boolean> = _isExpandedMain.asStateFlow()
    fun updateIsExpanded(bl: Boolean) {
        _isExpandedMain.value = bl
    }

    private val _selTextMain = MutableStateFlow("Izaberite Lokaciju")
    val selTextMain: StateFlow<String> = _selTextMain.asStateFlow()
    fun updateSelTextMain(nt: String) {
        _selTextMain.value = nt
    }

    private val _isExpandedProdMain = MutableStateFlow(false)
    val isExpandedProdMain: StateFlow<Boolean> = _isExpandedProdMain.asStateFlow()
    fun updateIsExpandedProd(bl: Boolean) {
        _isExpandedProdMain.value = bl
    }

    private val _selTextProdMain = MutableStateFlow("")
    val selTextProdMain: StateFlow<String> = _selTextProdMain.asStateFlow()
    fun updateSelTextProdMain(nt: String) {
        _selTextProdMain.value = nt
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

    private var locationCallback: LocationCallback? = null

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(context: Context) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            5000
        ).build()

        if (locationCallback == null) {
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    val location: Location? = locationResult.lastLocation
                    location?.let {
                        _userLocation.value = LatLng(it.latitude, it.longitude)
                    }
                }
            }
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback!!, null)
    }

    fun stopLocationUpdates(context: Context) {
        locationCallback?.let {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            fusedLocationClient.removeLocationUpdates(it)
        }
    }

    private val _cameraPositionState =
        MutableStateFlow<CameraPositionState>(
            CameraPositionState(CameraPosition(LatLng(43.321445, 21.896104), 18f, 0f, 0f))
        )
    val cameraPositionState: StateFlow<CameraPositionState> = _cameraPositionState.asStateFlow()

    fun updateCameraPosition(cps: CameraPositionState) {
        _cameraPositionState.value = cps
    }

    //MOZDA DA SE IZBACI STARTFOREGROUNDSERVICE IZ KORUTINE???
    //IZBACITI ODAVDE
    fun getServiceAllowed(context: Context) {
        val intent = Intent(context, NearbyService::class.java)
        viewModelScope.launch {
            if (userRep?.getServiceAllowed() == true) {
                ContextCompat.startForegroundService(context, intent)
            } else {
                //za svaki slucaj stopirati ako postoji servis
                context.stopService(intent)
            }
        }
    }

    private val _sales = MutableStateFlow<List<Sale>?>(null)
    val sales: StateFlow<List<Sale>?> = _sales.asStateFlow()

    private val _tempSales = MutableStateFlow<List<Sale>?>(null)
    val tempSales: StateFlow<List<Sale>?> = _tempSales.asStateFlow()

    @OptIn(ExperimentalMaterial3Api::class)
    fun getAllSales() {
        viewModelScope.launch {
            val oglasi = oglasRep?.getAllSales()

            val oglasiList = oglasi?.documents?.map { doc ->
                val lat = doc.getField<Double>("lat") ?: 0.0
                val lng = doc.getField<Double>("lng") ?: 0.0
                val datum = doc.getString("datumIsteka") ?: ""
                val opis = doc.getString("opis") ?: ""
                val prod = doc.getString("prodavnica") ?: ""
                val autor = doc.getString("autor") ?: ""
                val bodovi = doc.getField<Int>("bodovi") ?: 0
                val slikaUrl = doc.getString("slika") ?: ""
                val docID = doc.id

                val autorInfo = userRep?.getUserByUID(autor)
                val user = autorInfo?.documents?.firstOrNull()
                var autorIme = ""
                var autorPrezime = ""
                if (user != null) {
                    autorIme = user.getString("ime").toString()
                    autorPrezime = user.getString("prezime").toString() ?: ""
                }

                val lokConv = LatLng(lat, lng)
                Log.w("BODOVI", bodovi.toString())
                Sale(
                    opis = opis,
                    prod = prod,
                    datumIsteka = datum,
                    lokacija = lokConv,
                    slikaURL = slikaUrl,
                    autor = autor,
                    bodovi = bodovi,
                    documentID = docID,
                    imeAutora = autorIme,
                    prezimeAutora = autorPrezime
                )
            } ?: emptyList()

            val todaysDateUNF = System.currentTimeMillis()
            val todaysDate = Date(todaysDateUNF)
            val dateFormat = SimpleDateFormat("dd. MMMM yyyy.", Locale("bs", "BS", "LAT"))

            val filteredOglasi = oglasiList.filter { sale ->
                val saleDate = dateFormat.parse(sale.datumIsteka)
                saleDate?.after(todaysDate) ?: false
            }

            _sales.value = filteredOglasi
            _tempSales.value = filteredOglasi

            filterSales()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun resetFilters() {
        viewModelScope.launch {
            _selTextProdMain.value = ""
            _dateState.value.selectedDateMillis = System.currentTimeMillis()
            getAllSales()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun filterSales() {
        viewModelScope.launch {
            _sales.value = _tempSales.value
            val allSales = _sales.value ?: return@launch

            val selectedDateMillis = dateState.value.selectedDateMillis
            val selectedDate = Date(selectedDateMillis!!)
            val dateFormat = SimpleDateFormat("dd. MMMM yyyy.", Locale("bs", "BS", "LAT"))


            val filteredSales = allSales.filter { sale ->

                val saleDate = dateFormat.parse(sale.datumIsteka)

                (_selTextProdMain.value.isEmpty() || sale.prod.contains(
                    _selTextProdMain.value,
                    ignoreCase = true
                )) &&
                        saleDate?.after(selectedDate) ?: false
            }

            _sales.value = filteredSales
        }
    }
}

class MainVMFactory(private val oglasRep: OglasRepository, private val userRep: UserRepository?) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainVM::class.java)) {
            return MainVM(oglasRep, userRep) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}