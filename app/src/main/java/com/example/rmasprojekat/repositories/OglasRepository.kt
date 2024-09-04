package com.example.rmasprojekat.repositories

import android.util.Log
import com.example.rmasprojekat.classes.Sale
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

    suspend fun isLiked(docID: String): Boolean {
        return oglasDS.isLiked(docID)
    }


    suspend fun addToLiked(docID: String, autorID: String): Boolean {
        return oglasDS.addToLiked(docID, autorID)
    }

    suspend fun removeFromLiked(docID: String, autorID: String): Boolean {
        return oglasDS.removeFromLiked(docID, autorID)
    }

    suspend fun getHistorySales(): QuerySnapshot? {
        return oglasDS.getHistorySales()
    }

    suspend fun getSavedSales(): List<Sale>? {
        return oglasDS.getSavedSales()
    }

    suspend fun deteleSale(docID: String): Boolean {
        return oglasDS.deleteSale(docID)
    }
}