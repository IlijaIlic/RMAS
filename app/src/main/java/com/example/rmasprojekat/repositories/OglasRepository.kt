package com.example.rmasprojekat.repositories

import android.util.Log
import com.example.rmasprojekat.ds.OglasDS
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.QuerySnapshot

import java.io.File

class OglasRepository(private val oglasDS: OglasDS) {

    suspend fun createOglas(
        prodavnica: String,
        datumIsteka: String,
        slika: File?,
        lokacija: LatLng?,
        opis: String,
    ): Boolean {
        Log.w("TEST", "U oglasRep")

        return oglasDS.addOglas(prodavnica, datumIsteka, slika, lokacija, opis)
    }

    suspend fun getAllSales(): QuerySnapshot? {
        return oglasDS.getAllAds()
    }
}