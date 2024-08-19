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
}