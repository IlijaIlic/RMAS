package com.example.rmasprojekat.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rmasprojekat.classes.Sale
import com.example.rmasprojekat.repositories.OglasRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SaveSalesVM(private val oglasRep: OglasRepository) : ViewModel() {

    private val _savedSalesList = MutableStateFlow<List<Sale>?>(null)
    val savedSalesList: StateFlow<List<Sale>?> = _savedSalesList.asStateFlow()

    fun getSavedSales() {
        viewModelScope.launch {
            _savedSalesList.value = oglasRep.getSavedSales()
        }
    }
}

class SaveSalesVMFactory(private val oglasRep: OglasRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SaveSalesVM::class.java)) {
            return SaveSalesVM(oglasRep) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}