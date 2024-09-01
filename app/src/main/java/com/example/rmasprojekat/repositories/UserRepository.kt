package com.example.rmasprojekat.repositories

import com.example.rmasprojekat.ds.UserDS
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.auth.User
import java.io.File


class UserRepository(private val userds: UserDS) {
    suspend fun createUser(
        email: String,
        password: String,
        ime: String,
        prezime: String,
        brTel: String,
        slika: File?
    ): FirebaseUser? {
        return userds.createAcc(email, password, ime, prezime, brTel, slika)
    }

    suspend fun login(email: String, password: String): FirebaseUser? {
        return userds.login(email, password)
    }


    suspend fun getUser(): QuerySnapshot? {
        return userds.getCurrentUser()
    }

    fun logout() {
        userds.logout()
    }

    fun getUserTF(): FirebaseUser? {
        return userds.getUserTF()
    }

    suspend fun getAllUsers(): QuerySnapshot? {
        return userds.getAllUsers()
    }


}