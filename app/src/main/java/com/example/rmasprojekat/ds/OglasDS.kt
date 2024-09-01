package com.example.rmasprojekat.ds

import android.net.Uri
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import java.io.File

class OglasDS(
    private var auth: FirebaseAuth = Firebase.auth,
    private var db: FirebaseFirestore = Firebase.firestore
) {

    suspend fun addOglas(
        prodavnica: String,
        datumIsteka: String,
        slika: File?,
        lokacija: LatLng?,
        opis: String,
    ): Boolean {
        try {
            var lokLat: Double = 0.0
            var lokLng: Double = 0.0
            Log.w("TEST", "U oglasDs try")
            if (lokacija != null) {
                lokLat = lokacija.latitude
                lokLng = lokacija.longitude
            }

            val imgUrl = uploadImageToFirebase(slika)
            Log.w("SLIKA", imgUrl.toString())

            val autorUID = auth.currentUser!!.uid.toString()

            val data = hashMapOf(
                "prodavnica" to prodavnica,
                "datumIsteka" to datumIsteka,
                "slika" to imgUrl,
                "autor" to autorUID,
                "lat" to lokLat,
                "lng" to lokLng,
                "bodovi" to 0,
                "opis" to opis
            )
            db.collection("sales").document().set(data)
            return true
        } catch (e: Exception) {
            Log.w("Firestore", "Error creating sale or uploading data", e)
            return false
        }
    }

    private val storageReference: StorageReference by lazy {
        FirebaseStorage.getInstance().reference
    }

    suspend fun uploadImageToFirebase(file: File?): String? {
        return try {
            val fileUri = Uri.fromFile(file)
            val imageRef = storageReference.child("places/${file?.name}")

            // Log to verify paths
            Log.d("UploadImage", "File URI: $fileUri")
            Log.d("UploadImage", "Storage Path: ${imageRef.path}")

            // Upload the file
            val uploadTask = imageRef.putFile(fileUri).await()

            // Check if upload was successful
            if (uploadTask.task.isSuccessful) {
                val dnwldURL = imageRef.downloadUrl.await().toString()
                Log.d("UploadImage", "Upload successful. Download URL: $dnwldURL")
                return dnwldURL
            } else {
                Log.w("UploadImage", "Upload failed.")
                null
            }
        } catch (e: Exception) {
            Log.w("UploadImage", "Upload encountered an error: ${e.message}")
            e.printStackTrace()
            null
        }
    }

    suspend fun getAllAds(): QuerySnapshot? {
        val salseRef = db.collection("sales")

        return try {
            salseRef.get().await()
        } catch (e: Exception) {
            Log.w("Firestore", "Error getting sales from db: ", e)
            null
        }
    }

}