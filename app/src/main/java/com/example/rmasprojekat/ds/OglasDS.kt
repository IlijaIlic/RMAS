package com.example.rmasprojekat.ds

import android.net.Uri
import android.util.Log
import com.example.rmasprojekat.classes.Sale
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.getField
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

            try {
                val usersRef = db.collection("users")
                val query = usersRef.whereEqualTo("uid", auth.currentUser?.uid.toString())
                val querySnapshot = query.get().await()
                if (!querySnapshot.isEmpty) {
                    val document = querySnapshot.documents[0]
                    val documentRef = document.reference
                    documentRef.update("bodovi", FieldValue.increment(5)).await()
                }
            } catch (e: Exception) {
                Log.w("ERROR", "Unable to find user $e")
            }

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

    suspend fun isLiked(docID: String): Boolean {
        val savedSalesRef = db.collection("savedSales")
        val query = savedSalesRef.whereEqualTo("saleID", docID)
        val query2 = query.whereEqualTo("userID", auth.currentUser!!.uid)
        val qsnapshot = query2.get().await()

        return !qsnapshot.isEmpty
    }

    suspend fun addToLiked(docID: String, autorID: String): Boolean {
        try {
            val userID = auth.currentUser!!.uid.toString()

            val data = hashMapOf(
                "saleID" to docID,
                "userID" to userID
            )

            db.collection("savedSales").add(data).await()

            val usrsRef = db.collection("users")
            val query = usrsRef.whereEqualTo("uid", autorID)
            val querySnap = query.get().await()
            if (!querySnap.isEmpty) {
                val document = querySnap.documents[0]
                val documentRef = document.reference
                documentRef.update("bodovi", FieldValue.increment(1)).await()
            }

            val salesRef = db.collection("sales").document(docID)
            salesRef.update("bodovi", FieldValue.increment(1)).await()

            return true
        } catch (e: Exception) {
            return false
        }
    }

    suspend fun removeFromLiked(docID: String, autorID: String): Boolean {
        try {
            val savedSalesRef = db.collection("savedSales")
            val query = savedSalesRef
                .whereEqualTo("saleID", docID)
                .whereEqualTo("userID", auth.currentUser!!.uid)
            val querySnapshot = query.get().await()
            for (document in querySnapshot.documents) {
                savedSalesRef.document(document.id).delete().await()
            }

            val usrsRef = db.collection("users")
            val query2 = usrsRef.whereEqualTo("uid", autorID)
            val querySnap = query2.get().await()
            if (!querySnap.isEmpty) {
                val document = querySnap.documents[0]
                val documentRef = document.reference
                documentRef.update("bodovi", FieldValue.increment(-1)).await()
            }

            val salesRef = db.collection("sales").document(docID)
            salesRef.update("bodovi", FieldValue.increment(-1)).await()

            return true
        } catch (e: Exception) {
            Log.e("Firestore", "Error removing document", e)
            return false
        }
    }

    suspend fun deleteSale(docID: String): Boolean {
        try {
            val savedSalesRef = db.collection("savedSales")
            val query = savedSalesRef.whereEqualTo("saleID", docID)
            val querySnap = query.get().await()
            if (!querySnap.isEmpty) {
                querySnap.forEach { doc ->
                    doc.reference.delete().await()
                }
            }
        } catch (e: Exception) {
            Log.w("DELETE", "Error deleting savedSales")
            return false
        }
        try {
            val salesRef = db.collection("sales").document(docID)
            val snap = salesRef.get().await()
            if (snap.exists()) {
                snap.reference.delete().await()
            }
        } catch (e: Exception) {
            Log.w("DELETE", "Error deleting sale")
            return false
        }
        return true
    }

    suspend fun getHistorySales(): QuerySnapshot? {
        try {
            val salesRef = db.collection("sales")
            val query = salesRef.whereEqualTo("autor", auth.currentUser!!.uid)
            val querSnapshot = query.get().await()
            if (!querSnapshot.isEmpty) {
                return querSnapshot
            }
            return null
        } catch (e: Exception) {
            return null
        }
    }

    suspend fun getSavedSales(): List<Sale>? {
        try {
            val savedSalesRef = db.collection("savedSales")
            val query = savedSalesRef.whereEqualTo("userID", auth.currentUser!!.uid)
            val querySnap = query.get().await()
            if (!querySnap.isEmpty) {
                val savedSalesList = querySnap.documents.mapNotNull { doc ->
                    val saleID = doc.getString("saleID")
                    saleID?.let { getSaleInfo(it) }
                }
                return savedSalesList
            } else {
                return emptyList()
            }
        } catch (e: Exception) {
            return null
        }
    }

    suspend fun getSaleInfo(saleId: String): Sale? {
        try {
            val saleRef = db.collection("sales").document(saleId)
            val saleSnap = saleRef.get().await()

            if (saleSnap.exists()) {
                val lat = saleSnap.getField<Double>("lat") ?: 0.0
                val lng = saleSnap.getField<Double>("lng") ?: 0.0
                val datum = saleSnap.getString("datumIsteka") ?: ""
                val opis = saleSnap.getString("opis") ?: ""
                val prod = saleSnap.getString("prodavnica") ?: ""
                val autor = saleSnap.getString("autor") ?: ""
                val bodovi = saleSnap.getField<Int>("bodovi") ?: 0
                val slikaUrl = saleSnap.getString("slika") ?: ""
                val docID = saleSnap.id

                val autorInfo = getUserByUID(autor)
                val user = autorInfo?.documents?.firstOrNull()
                var autorIme = ""
                var autorPrezime = ""
                if (user != null) {
                    autorIme = user.getString("ime").toString()
                    autorPrezime = user.getString("prezime").toString() ?: ""
                }


                val lokConv = LatLng(lat, lng)
                Log.w("BODOVI", bodovi.toString())
                return Sale(
                    opis = opis,
                    prod = prod,
                    datumIsteka = datum,
                    lokacija = lokConv,
                    slikaURL = slikaUrl,
                    autor = autor,
                    bodovi = bodovi,
                    documentID = docID,
                    imeAutora = autorIme,
                    prezimeAutora = autorPrezime
                )
            } else {
                return null
            }
        } catch (e: Exception) {
            Log.e("Firestore", "Error retrieving sale", e)
            return null
        }
    }

    suspend fun getUserByUID(uid: String): QuerySnapshot? {
        val usersRef = db.collection("users")
        val query = usersRef.whereEqualTo("uid", uid)

        return try {
            query.get().await()
        } catch (e: Exception) {
            Log.w("Firestore", "Error getting documents: ", e)
            null
        }

    }
}