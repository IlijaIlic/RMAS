package com.example.rmasprojekat.screens

import android.R
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.rounded.Face
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rmasprojekat.ui.theme.Amber
import com.example.rmasprojekat.ui.theme.fontJockey

@Composable
fun Register(
    onNavigateToLogin: () -> Unit,
    onNavigateToLanding: () -> Unit,
    onNavigateToMain: () -> Unit
) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var passwordVisible by remember { mutableStateOf(false) }
    var ime by remember { mutableStateOf(TextFieldValue("")) }
    var prezime by remember { mutableStateOf(TextFieldValue("")) }
    var phoneNumber by remember { mutableStateOf(TextFieldValue("")) }

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        item {
            Text(
                text = "321 AKCIJA!",
                fontSize = 50.sp,
                modifier = Modifier.padding(10.dp),
                color = Amber,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = fontJockey
            )
        }
        item {
            Text(
                text = "Napravi svoj nalog!",
                fontSize = 30.sp,
                fontFamily = fontJockey
            )
        }
        item {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 20.dp)
            ) {
                TextField(
                    placeholder = { Text("Ime", color = Amber, fontFamily = fontJockey) },
                    value = ime,
                    onValueChange = { newText -> ime = newText },
                    modifier = Modifier
                        .padding(5.dp)
                        .widthIn(0.dp, 125.dp)
                        .shadow(3.dp, RoundedCornerShape(12.dp)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
                TextField(
                    placeholder = { Text("Prezime", color = Amber, fontFamily = fontJockey) },
                    value = prezime,
                    onValueChange = { newText -> prezime = newText },
                    modifier = Modifier
                        .padding(5.dp)
                        .widthIn(0.dp, 125.dp)
                        .shadow(3.dp, RoundedCornerShape(12.dp)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }
        }
        item {
            TextField(
                placeholder = { Text("Email", color = Amber, fontFamily = fontJockey) },
                value = email,
                onValueChange = { newText -> email = newText },
                modifier = Modifier
                    .padding(10.dp)
                    .widthIn(0.dp, 250.dp)
                    .shadow(3.dp, RoundedCornerShape(12.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
        item {
            TextField(
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                placeholder = { Text("Broj telefona", color = Amber, fontFamily = fontJockey) },
                value = phoneNumber,
                onValueChange = { newText -> phoneNumber = newText },
                modifier = Modifier
                    .padding(10.dp)
                    .widthIn(0.dp, 250.dp)
                    .shadow(3.dp, RoundedCornerShape(12.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
        item {
            TextField(
                placeholder = { Text("Lozinka", color = Amber, fontFamily = fontJockey) },
                value = password,
                onValueChange = { newText -> password = newText },
                modifier = Modifier
                    .padding(10.dp)
                    .widthIn(0.dp, 250.dp)
                    .shadow(3.dp, RoundedCornerShape(12.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Rounded.Lock
                    else Icons.Filled.Lock
                    val description = if (passwordVisible) "Hide password" else "Show password"
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = description)
                    }
                }
            )
        }
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(10.dp)
            )
            {
                Text(
                    fontSize = 15.sp,
                    text = "Vasa fotografija",
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 10.dp),
                    fontWeight = FontWeight.Normal,
                    fontFamily = fontJockey
                )
                Button(
                    modifier = Modifier
                        .shadow(
                            3.dp, RoundedCornerShape(12.dp)
                        ),
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White

                    ), shape = RoundedCornerShape(20)
                ) {
                    Icon(
                        tint = Amber,
                        imageVector = Icons.Rounded.Face,
                        contentDescription = "Fotografija",
                    )
                }
            }
        }
        item {
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, end = 60.dp)
            ) {
                Button(
                    onClick = { onNavigateToMain() }, colors = ButtonDefaults.buttonColors(
                        containerColor = Amber
                    ), shape = RoundedCornerShape(20)
                ) {
                    Text(
                        "Registruj se",
                        fontFamily = fontJockey,
                        style = TextStyle(fontSize = 20.sp)
                    )
                }
                Row {
                    Text(
                        text = "Vec imate nalog?",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 5.dp, top = 5.dp),
                        fontFamily = fontJockey
                    )
                    ClickableText(
                        text = AnnotatedString("Uloguj se"),
                        onClick = { onNavigateToLogin() },
                        style = TextStyle(
                            color = Amber,
                            fontSize = 18.sp,
                            fontFamily = fontJockey
                        ),
                        modifier = Modifier.padding(bottom = 5.dp, top = 5.dp, start = 5.dp)
                    )
                }
            }
        }
        item {
            Button(
                onClick = { onNavigateToLanding() },
                colors = ButtonDefaults.buttonColors(containerColor = Amber),
                shape = RoundedCornerShape(20),
                modifier = Modifier

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
}
