package com.example.rmasprojekat.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.rmasprojekat.repositories.OglasRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ViewSaleVM(private val oglasRep: OglasRepository) : ViewModel() {


    private val _documentID = MutableStateFlow("")
    val documentID: StateFlow<String> = _documentID.asStateFlow()
    fun updateDocumentID(nT: String) {
        _documentID.value = nT
    }

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

    private val _bodovi = MutableStateFlow(0)
    val bodovi: StateFlow<Int> = _bodovi.asStateFlow()
    fun updateBodovi(nB: Int) {
        _bodovi.value = nB
    }

    private val _autorIme = MutableStateFlow("")
    val autorIme: StateFlow<String> = _autorIme.asStateFlow()
    fun updateAutorIme(nT: String) {
        _autorIme.value = nT
    }

    private val _autorPrezime = MutableStateFlow("")
    val autorPrezime: StateFlow<String> = _autorPrezime.asStateFlow()
    fun updateAutorPrezime(nT: String) {
        _autorPrezime.value = nT
    }


    fun isLiked() {
        viewModelScope.launch {
            _likedSale.value = oglasRep.isLiked(_documentID.value)
        }
    }

    // likedSale = true => DISLIKE
    // likedSale = false => LIKE

    fun onLikeClick() {
        viewModelScope.launch {
            if (_likedSale.value) {
                _bodovi.value--
                oglasRep.removeFromLiked(_documentID.value, _autor.value)
            } else {
                _bodovi.value++
                oglasRep.addToLiked(_documentID.value, _autor.value)
            }
        }
    }
}

class ViewSaleVMFactory(private val oglasRep: OglasRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewSaleVM::class.java)) {
            return ViewSaleVM(oglasRep) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}