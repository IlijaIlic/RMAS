package com.example.rmasprojekat.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rmasprojekat.ui.theme.Amber
import com.example.rmasprojekat.ui.theme.fontJockey

@Composable
fun Login(
    onNavigateToRegister: () -> Unit,
    onNavigateToLanding: () -> Unit,
    onNavigateToMain: () -> Unit
) {

    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "321 AKCIJA!",
            fontSize = 50.sp,
            modifier = Modifier.padding(10.dp),
            color = Amber,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = fontJockey
        )
        Text(
            text = "Uloguj se na svoj nalog",
            fontSize = 30.sp,
            fontFamily = fontJockey,

            )
        Column(modifier = Modifier.padding(top = 20.dp)) {
            TextField(
                placeholder = { Text("Unesi email", color = Amber, fontFamily = fontJockey) },
                value = email,
                onValueChange = { newText ->
                    email = newText
                },
                singleLine = true,
                modifier = Modifier
                    .padding(10.dp)
                    .widthIn(0.dp, 250.dp)
                    .shadow(3.dp, RoundedCornerShape(12.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),


                )
            TextField(
                placeholder = { Text("Unesi sifru", color = Amber, fontFamily = fontJockey) },
                value = password,
                onValueChange = { newText ->
                    password = newText
                },
                modifier = Modifier
                    .padding(10.dp)
                    .widthIn(0.dp, 250.dp)
                    .shadow(3.dp, RoundedCornerShape(12.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Rounded.Lock
                    else Icons.Filled.Lock

                    // Localized description for accessibility services
                    val description = if (passwordVisible) "Hide password" else "Show password"

                    // Toggle button to hide or display password
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description)
                    }
                }
            )
        }
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 60.dp)
        ) {
            Button(
                onClick = { onNavigateToMain() }, colors = ButtonDefaults.buttonColors(
                    containerColor = Amber
                ), shape = RoundedCornerShape(20)
            ) {
                Text("Uloguj se", fontFamily = fontJockey, style = TextStyle(fontSize = 20.sp))
            }
            Row {
                Text(
                    text = "Nemate nalog?",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 5.dp, top = 5.dp),
                    fontFamily = fontJockey
                )
                ClickableText(
                    text = AnnotatedString("Registruj se"),
                    onClick = { onNavigateToRegister() },
                    style = TextStyle(
                        color = Amber,
                        fontSize = 18.sp,
                        fontFamily = fontJockey
                    ),
                    modifier = Modifier.padding(bottom = 5.dp, top = 5.dp, start = 5.dp),

                    )
            }

        }
        Button(
            onClick = { onNavigateToLanding() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Amber
            ), shape = RoundedCornerShape(20),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 30.dp)
        ) {
            Text(
                "Nazad na pocetni ekran",
                fontFamily = fontJockey,
                style = TextStyle(fontSize = 20.sp)
            )
        }
    }
}