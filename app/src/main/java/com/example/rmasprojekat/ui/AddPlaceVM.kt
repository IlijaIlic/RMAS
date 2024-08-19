package com.example.rmasprojekat.ui

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Locale
//GOTOV OSIM DATEPICKER (moze da se bira datum iz proslosti)
class AddPlaceVM : ViewModel() {

    private val _opisAdd = MutableStateFlow("")
    val opisAdd: StateFlow<String> = _opisAdd.asStateFlow()
    fun updateOpisTp(nT: String) {
        _opisAdd.value = nT
    }

    private val _selTextAdd = MutableStateFlow("Odaberite radnju")
    val selTextAdd: StateFlow<String> = _selTextAdd.asStateFlow()
    fun updateSelText(nT: String) {
        _selTextAdd.value = nT
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

}