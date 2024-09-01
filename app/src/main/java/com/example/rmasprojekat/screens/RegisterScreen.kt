package com.example.rmasprojekat.screens

import android.R
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rmasprojekat.repositories.UserRepository
import com.example.rmasprojekat.ui.LoginVM
import com.example.rmasprojekat.ui.ProfileVM
import com.example.rmasprojekat.ui.ProfileVMFactory
import com.example.rmasprojekat.ui.RegisterVM
import com.example.rmasprojekat.ui.RegisterVMFactory
import com.example.rmasprojekat.ui.theme.Amber
import com.example.rmasprojekat.ui.theme.AmberLight
import com.example.rmasprojekat.ui.theme.fontJockey
import compose.icons.FeatherIcons
import compose.icons.feathericons.Camera
import compose.icons.feathericons.Eye
import compose.icons.feathericons.EyeOff
import compose.icons.feathericons.Image

@Composable
fun Register(
    onNavigateToLogin: () -> Unit,
    onNavigateToLanding: () -> Unit,
    onNavigateToMain: () -> Unit,
    userRep: UserRepository
) {

    val vwModel: RegisterVM = viewModel(factory = RegisterVMFactory(userRep))
    val email by vwModel.emailRegister.collectAsState()
    val password by vwModel.passwordRegister.collectAsState()
    val ime by vwModel.imeRegister.collectAsState()
    val prezime by vwModel.prezimeRegister.collectAsState()
    val phoneNumber by vwModel.phoneNumberRegister.collectAsState()
    val showPassword by vwModel.showPasswordRegister.collectAsState()
    val context = LocalContext.current
    val cameraEvent by vwModel.cameraEvent.collectAsState()
    val okImage by vwModel.okImage.collectAsState()
    val okToRegister by vwModel.okToRegister.collectAsState()
    val firebaseOk by vwModel.firebaseOk.collectAsState()
    val regAttempted by vwModel.loginAttempted.collectAsState()

    LaunchedEffect(regAttempted) {
        if (regAttempted) {
            if (firebaseOk) {
                onNavigateToMain()
                vwModel.updateOkImage(false)
            } else {
                Toast.makeText(context, "Greska u registrovanju", Toast.LENGTH_SHORT).show()
            }
            vwModel.resetLoginAttempt()
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            val file = vwModel.bitmapToFile(context, bitmap)
            if (file != null) {
                vwModel.updateFile(file)
            }
        }
    }

    val buttonColor = if (okImage) AmberLight else Color.White
    val iconTint = if (okImage) Color.White else Amber

    if (cameraEvent) {
        launcher.launch(null)
        vwModel.onCameraEventHandled()
    }

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
                    onValueChange = { newText -> vwModel.updateImeTp(newText) },
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
                    onValueChange = { newText -> vwModel.updatePrezimeTp(newText) },
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
                onValueChange = { newText -> vwModel.updateEmailTp(newText) },
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
                onValueChange = { newText -> vwModel.updatePhoneTp(newText) },
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
                onValueChange = { newText -> vwModel.updatePasswordTp(newText) },
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
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (showPassword)
                        FeatherIcons.Eye
                    else FeatherIcons.EyeOff
                    val description = if (showPassword) "Hide password" else "Show password"
                    IconButton(onClick = { vwModel.updateShowPasswordLogin(!showPassword) }) {
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
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Button(
                        shape = RoundedCornerShape(20),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 4.dp,
                            pressedElevation = 10.dp,
                            disabledElevation = 0.dp,
                            hoveredElevation = 4.dp,
                            focusedElevation = 4.dp,
                        ),
                        onClick = { vwModel.onOpenCamera() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = buttonColor
                        )
                    ) {
                        Icon(
                            tint = iconTint,
                            imageVector = FeatherIcons.Camera,
                            contentDescription = "Fotografija",
                        )
                    }
                    Button(
                        shape = RoundedCornerShape(20),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 4.dp,
                            pressedElevation = 10.dp,
                            disabledElevation = 0.dp,
                            hoveredElevation = 4.dp,
                            focusedElevation = 4.dp,
                        ),
                        onClick = { vwModel.onOpenCamera() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = buttonColor

                        )
                    ) {
                        Icon(
                            tint = iconTint,
                            imageVector = FeatherIcons.Image,
                            contentDescription = "Fotografija",
                        )
                    }
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
                    enabled = okToRegister,
                    onClick = {
                        vwModel.createUser()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Amber
                    ),
                    shape = RoundedCornerShape(20)
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

//fun launchCamera(context: Context) {
//    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//    if (intent.resolveActivity(context.packageManager) != null) {
//        // Optionally create a file to save the image
//        // val photoFile = File(...)
//        // val photoURI = FileProvider.getUriForFile(context, "your.package.fileprovider", photoFile)
//        // intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
//        (context as? Activity)?.startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
//    }
//}