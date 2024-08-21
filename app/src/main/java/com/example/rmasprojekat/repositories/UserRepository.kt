package com.example.rmasprojekat.repositories

import com.example.rmasprojekat.ds.UserDS
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.auth.User


class UserRepository(private val userds: UserDS) {
    suspend fun createUser(email: String, password: String) {
        userds.createAcc(email, password)
    }

    fun getUser(): FirebaseUser? {
        return userds.getCurrentUser()
    }

    fun logout() {
        userds.logout()
    }

}