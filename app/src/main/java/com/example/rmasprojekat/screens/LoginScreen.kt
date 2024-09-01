package com.example.rmasprojekat.screens

import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rmasprojekat.ds.UserDS
import com.example.rmasprojekat.repositories.UserRepository
import com.example.rmasprojekat.ui.LoginVM
import com.example.rmasprojekat.ui.LoginVMFactory
import com.example.rmasprojekat.ui.theme.Amber
import com.example.rmasprojekat.ui.theme.fontJockey
import compose.icons.FeatherIcons
import compose.icons.feathericons.Eye
import compose.icons.feathericons.EyeOff
import kotlinx.coroutines.launch

@Composable
fun Login(
    onNavigateToRegister: () -> Unit,
    onNavigateToLanding: () -> Unit,
    onNavigateToMain: () -> Unit,
    userRep: UserRepository
) {

    val context = LocalContext.current
    val vwModel: LoginVM = viewModel(factory = LoginVMFactory(userRep))
    val email by vwModel.emailLogin.collectAsState()
    val password by vwModel.passwordLogin.collectAsState()
    val showPassword by vwModel.showPasswordLogin.collectAsState()
    val okToLogin by vwModel.okToLogin.collectAsState()
    val firebaseOk by vwModel.firebaseOk.collectAsState()
    val loginAttempted by vwModel.loginAttempted.collectAsState()

    LaunchedEffect(loginAttempted) {
        if (loginAttempted) {
            if (firebaseOk) {
                onNavigateToMain()
            } else {
                Toast.makeText(context, "Greska u prijavljivanju", Toast.LENGTH_SHORT).show()
            }
            vwModel.resetLoginAttempt() 
        }
    }

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
                    vwModel.updateEmailTp(newText)
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
                    vwModel.updatePasswordTp(newText)
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
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (showPassword)
                        FeatherIcons.Eye
                    else FeatherIcons.EyeOff

                    val description = if (showPassword) "Hide password" else "Show password"

                    IconButton(onClick = { vwModel.updateShowPasswordLogin(!showPassword) }) {
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
                enabled = okToLogin,
                onClick = {
                    vwModel.login()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Amber
                ),
                shape = RoundedCornerShape(20)
            ) {
                Text("Prijavi se", fontFamily = fontJockey, style = TextStyle(fontSize = 20.sp))
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