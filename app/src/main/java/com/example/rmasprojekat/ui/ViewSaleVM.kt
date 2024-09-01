package com.example.rmasprojekat.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ViewSaleVM : ViewModel() {

    private val _prodavnicaSale = MutableStateFlow("Idea")
    val prodavnicaSale: StateFlow<String> = _prodavnicaSale.asStateFlow()
    fun updateProdavnSale(nT: String) {
        _prodavnicaSale.value = nT
    }

    private val _opisSale = MutableStateFlow("OPIS")
    val opisSale: StateFlow<String> = _opisSale.asStateFlow()
    fun updateOpisSale(nT: String) {
        _opisSale.value = nT
    }

    private val _likedSale = MutableStateFlow(false)
    val likedSale: StateFlow<Boolean> = _likedSale.asStateFlow()
    fun updateLikedSale(bl: Boolean) {
        _likedSale.value = bl
    }

    private val _autorSale = MutableStateFlow("Ilija Ilic")
    val autorSale: StateFlow<String> = _autorSale.asStateFlow()
    fun updateLikedSale(nT: String) {
        _autorSale.value = nT
    }

    private val _datumIsteka = MutableStateFlow("")
    val datumIsteka: StateFlow<String> = _datumIsteka.asStateFlow()
    fun updateDatumISteka(nT: String) {
        _datumIsteka.value = nT
    }

    private val _slikaURL = MutableStateFlow("")
    val slikaURL: StateFlow<String> = _slikaURL.asStateFlow()
    fun updateSlikaURL(nT: String) {
        _slikaURL.value = nT
    }

    private val _autor = MutableStateFlow("")
    val autor: StateFlow<String> = _autor.asStateFlow()
    fun updateAutor(nT: String) {
        _autor.value = nT
    }

    private val _bodovi = MutableStateFlow("")
    val bodovi: StateFlow<String> = _bodovi.asStateFlow()
    fun updateBodovi(nT: String) {
        _bodovi.value = nT
    }
}