package com.example.rmasprojekat.ds

import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.io.File

class UserDS(
    private var auth: FirebaseAuth = Firebase.auth,
    private var db: FirebaseFirestore = Firebase.firestore
) {


    suspend fun createAcc(
        email: String,
        password: String,
        ime: String,
        prezime: String,
        brTel: String,
        slika: File?
    ): FirebaseUser? {
        try {

            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val imgUrl = uploadImageToFirebase(slika)
            val data = hashMapOf(
                "uid" to auth.currentUser?.uid.toString(),
                "email" to email,
                "password" to password,
                "ime" to ime,
                "prezime" to prezime,
                "brTel" to brTel,
                "servis" to false,
                "slika" to imgUrl,
                "bodovi" to 0
            )
            db.collection("users").document().set(data)

            return authResult.user
        } catch (e: Exception) {
            Log.w("Firestore", "Error creating account or uploading data", e)
        }
        return null
    }

    private val storageReference: StorageReference by lazy {
        FirebaseStorage.getInstance().reference
    }

    suspend fun uploadImageToFirebase(file: File?): String? {
        return try {
            val fileUri = Uri.fromFile(file)
            val imageRef = storageReference.child("images/${file?.name}")

            // Log to verify paths
            Log.d("UploadImage", "File URI: $fileUri")
            Log.d("UploadImage", "Storage Path: ${imageRef.path}")

            // Upload the file
            val uploadTask = imageRef.putFile(fileUri).await()

            // Check if upload was successful
            if (uploadTask.task.isSuccessful) {
                val downloadUrl = imageRef.downloadUrl.await().toString()
                Log.d("UploadImage", "Upload successful. Download URL: $downloadUrl")
                return downloadUrl
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


    suspend fun login(email: String, password: String): FirebaseUser? {
        try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            return authResult.user
        } catch (e: Exception) {
            Log.w("ERROR", e)
        }
        return null
    }

    suspend fun getCurrentUser(): QuerySnapshot? {
        val usersRef = db.collection("users")
        val query = usersRef.whereEqualTo("uid", auth.currentUser?.uid.toString())

        return try {
            query.get().await()
        } catch (e: Exception) {
            Log.w("Firestore", "Error getting documents: ", e)
            null
        }

    }

    fun logout() {
        auth.signOut()
    }

    fun getUserTF(): FirebaseUser? {
        return auth.currentUser
    }

    suspend fun getAllUsers(): QuerySnapshot? {
        val usersRef = db.collection("users")

        return try {
            usersRef.get().await()
        } catch (e: Exception) {
            Log.w("Firestore", "Error getting users from db: ", e)
            null
        }
    }


}