package com.example.rmasprojekat.ds

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.tasks.await

class UserDS(private var auth: FirebaseAuth = Firebase.auth) {

    suspend fun createAcc(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener() { task ->
            if (task.isSuccessful) {

            } else {

            }
        }
    }

    suspend fun login(email: String, password: String): FirebaseUser? {
        val authResult = auth.signInWithEmailAndPassword(email, password).await()
        return authResult.user
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun logout() {
        auth.signOut()
    }

}