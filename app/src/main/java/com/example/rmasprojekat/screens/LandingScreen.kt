package com.example.rmasprojekat.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rmasprojekat.ui.theme.Amber
import com.example.rmasprojekat.ui.theme.fontJockey

@Composable
fun Landing(onNavigateToRegister: () -> Unit, onNavigateToLogin: () -> Unit) {

    // NAPRAVI DA AKO JE NEKO VEC ULOGOVAN DA PRESKOCI DO MAIN SCREENA
    
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("321 AKCIJA", fontFamily = fontJockey, fontSize = 60.sp, color = Amber)
        Button(
            onClick = { onNavigateToRegister() }, colors = ButtonDefaults.buttonColors(
                containerColor = Amber
            ), shape = RoundedCornerShape(20),
            modifier = Modifier.padding(30.dp)
        ) {
            Text("Register", fontFamily = fontJockey, fontSize = 30.sp)
        }
        Button(
            onClick = { onNavigateToLogin() }, colors = ButtonDefaults.buttonColors(
                containerColor = Amber
            ), shape = RoundedCornerShape(20),
            modifier = Modifier.padding(30.dp)
        ) {
            Text("Login", fontFamily = fontJockey, fontSize = 30.sp)
        }
    }
}