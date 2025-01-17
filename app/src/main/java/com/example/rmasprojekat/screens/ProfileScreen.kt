package com.example.rmasprojekat.screens

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rmasprojekat.R
import com.example.rmasprojekat.components.LoadImage
import com.example.rmasprojekat.repositories.UserRepository
import com.example.rmasprojekat.services.NearbyService
import com.example.rmasprojekat.ui.LoginVM
import com.example.rmasprojekat.ui.LoginVMFactory
import com.example.rmasprojekat.ui.ProfileVM
import com.example.rmasprojekat.ui.ProfileVMFactory
import com.example.rmasprojekat.ui.theme.Amber
import com.example.rmasprojekat.ui.theme.AmberLight
import com.example.rmasprojekat.ui.theme.fontJockey
import compose.icons.FeatherIcons
import compose.icons.feathericons.Archive
import compose.icons.feathericons.ArrowLeft
import compose.icons.feathericons.Bookmark
import compose.icons.feathericons.LogOut

@Composable
fun ProfileScreen(
    onNavigateToMain: () -> Unit,
    onNavigateWhenLogout: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToSaved: () -> Unit,
    userRep: UserRepository
) {

    val vwModel: ProfileVM = viewModel(factory = ProfileVMFactory(userRep))
    val ime by vwModel.imeProf.collectAsState()
    val prezime by vwModel.prezimeProf.collectAsState()
    val email by vwModel.emailProf.collectAsState()
    val brTelefona by vwModel.brojTelefonaProf.collectAsState()
    val brojBodova by vwModel.brojBodovaProf.collectAsState()
    val checked by vwModel.serviceCheckedProf.collectAsState()
    val imageUrl by vwModel.imageURL.collectAsState()
    val context = LocalContext.current


    LaunchedEffect(Unit) {
        vwModel.getUserInfo()

        val isServiceRunning = vwModel.isServiceRunning(context, NearbyService::class.java)

        //suspend funckije da bi checked moglo u if
        val checkedFromSuspend = vwModel.getServiceAllowed() ?: false

        // Ako je korisinika izabrao da ukljuci servis ranije i servis trenutno nije ukljucen
        if (checkedFromSuspend && !isServiceRunning) {
            Toast.makeText(context, "Servis je automatski pokrenut!", Toast.LENGTH_SHORT).show()
            vwModel.startNearbyService(context)
        } else {
            Toast.makeText(context, "Servis vec radi ili je zabranjen!", Toast.LENGTH_SHORT).show()
        }
    }

    //
    // Screen za sacuvane akcije, istoriju postavljenih
    // ODRADjENO
    //
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        LazyColumn {
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    FloatingActionButton(
                        onClick = { onNavigateToMain() },
                        containerColor = Color.White,
                        contentColor = Amber,
                        shape = CircleShape,
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Icon(
                            imageVector = FeatherIcons.ArrowLeft,
                            contentDescription = "goBack"
                        )
                    }
                    Text(
                        text = "Profil",
                        fontFamily = fontJockey,
                        fontSize = 30.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,

                    ) {

                    LoadImage(
                        url = imageUrl,

                        )
                    Column {
                        Text(
                            text = ime,
                            fontFamily = fontJockey,
                            fontSize = 65.sp,
                            color = Amber,
                            fontWeight = FontWeight.Bold,

                            )
                        Text(
                            text = prezime,
                            fontFamily = fontJockey,
                            fontSize = 65.sp,
                            color = Amber,
                            fontWeight = FontWeight.Bold
                        )
                    }

                }
            }
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "podaci korisnika",
                        color = Amber,
                        fontFamily = fontJockey,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(20.dp),
                        fontWeight = FontWeight.Thin
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(end = 20.dp),
                        thickness = 2.dp, color = Amber
                    )
                }

            }
            item {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Email:",
                            color = Color.Black,
                            fontFamily = fontJockey,
                            fontSize = 20.sp,
                        )
                        Text(
                            text = email,
                            color = Color.Black,
                            fontFamily = fontJockey,
                            fontSize = 20.sp
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Broj telefona:",
                            color = Color.Black,
                            fontFamily = fontJockey,
                            fontSize = 20.sp
                        )
                        Text(
                            text = brTelefona,
                            color = Color.Black,
                            fontFamily = fontJockey,
                            fontSize = 20.sp
                        )
                    }

                }
            }
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "akcije",
                        color = Amber,
                        fontFamily = fontJockey,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(20.dp)
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(end = 20.dp),
                        thickness = 2.dp, color = Amber
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Broj bodova:",
                            color = Color.Black,
                            fontFamily = fontJockey,
                            fontSize = 20.sp,
                        )
                        Text(
                            text = brojBodova.toString(),
                            color = Color.Black,
                            fontFamily = fontJockey,
                            fontSize = 20.sp
                        )
                    }
                    Button(
                        onClick = { onNavigateToSaved() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Amber
                        ), shape = RoundedCornerShape(20)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = FeatherIcons.Bookmark,
                                contentDescription = "Saved",
                                tint = Color.White,
                                modifier = Modifier.padding(end = 10.dp)
                            )
                            Text(
                                text = "Sacuvane akcije",
                                fontFamily = fontJockey,
                                fontSize = 20.sp,
                                color = Color.White
                            )

                        }
                    }
                    Button(
                        onClick = { onNavigateToHistory() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Amber
                        ), shape = RoundedCornerShape(20)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = FeatherIcons.Archive,
                                contentDescription = "Saved",
                                tint = Color.White,
                                modifier = Modifier.padding(end = 10.dp)
                            )
                            Text(
                                text = "Istorija postavljenih akcija",
                                fontFamily = fontJockey,
                                fontSize = 20.sp,
                                color = Color.White
                            )

                        }
                    }
                }
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Aktiviraj servis",
                            fontFamily = fontJockey,
                            modifier = Modifier.padding(horizontal = 10.dp),
                            color = Color.Black,
                            fontSize = 20.sp
                        )
                        Switch(
                            checked = checked,
                            onCheckedChange = {
                                vwModel.serviceFunction(context = context)
                                vwModel.updateServiceCheckedProf(it)

                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Amber,
                                uncheckedThumbColor = Color.White,
                                uncheckedTrackColor = AmberLight,
                                uncheckedBorderColor = AmberLight
                            )
                        )
                    }
                    Button(
                        onClick = {
                            userRep.logout()
                            onNavigateWhenLogout()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Amber
                        ),
                        shape = RoundedCornerShape(20),
                        modifier = Modifier.padding(top = 20.dp)

                    ) {
                        Text(
                            text = "Odjavite se",
                            fontFamily = fontJockey,
                            fontSize = 20.sp,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 5.dp)
                        )
                        Icon(
                            imageVector = FeatherIcons.LogOut,
                            contentDescription = "Logout",
                            modifier = Modifier.padding(horizontal = 5.dp)
                        )
                    }
                }
            }
        }
    }
}