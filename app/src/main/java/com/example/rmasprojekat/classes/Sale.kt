package com.example.rmasprojekat.classes

import com.google.android.gms.maps.model.LatLng

data class Sale(
    val opis: String = "",
    val prod: String = "",
    val datumIsteka: String = "",
    val lokacija: LatLng? = null,
    val slikaURL: String = "",
    val autor: String = "",
    val bodovi: Int = 0,
    val documentID: String = "",
    val imeAutora: String = "",
    val prezimeAutora: String = ""
)