package com.example.rmasprojekat.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rmasprojekat.classes.Sale
import com.example.rmasprojekat.repositories.OglasRepository
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.getField
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HistoryVM(private val oglasRep: OglasRepository) : ViewModel() {
    private val _historyList = MutableStateFlow<List<Sale>?>(null)
    val historyList: StateFlow<List<Sale>?> = _historyList.asStateFlow()

    fun getHistoryOfAddedPlaces() {
        viewModelScope.launch {
            val historyOglasi = oglasRep.getHistorySales()

            val historyOglasiList = historyOglasi?.documents?.map { doc ->
                val lat = doc.getField<Double>("lat") ?: 0.0
                val lng = doc.getField<Double>("lng") ?: 0.0
                val datum = doc.getString("datumIsteka") ?: ""
                val opis = doc.getString("opis") ?: ""
                val prod = doc.getString("prodavnica") ?: ""
                val autor = doc.getString("autor") ?: ""
                val bodovi = doc.getField<Int>("bodovi") ?: 0
                val slikaUrl = doc.getString("slika") ?: ""
                val docID = doc.id

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
                    documentID = docID
                )
            } ?: emptyList()
            _historyList.value = historyOglasiList
        }
    }

    fun deleteSale(docID: String) {
        viewModelScope.launch {
            oglasRep.deteleSale(docID)
            _historyList.value = emptyList()
            getHistoryOfAddedPlaces()
        }
    }
}

class HistoryVMFactory(private val oglasRep: OglasRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryVM::class.java)) {
            return HistoryVM(oglasRep) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}