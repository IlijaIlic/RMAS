package com.example.rmasprojekat.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rmasprojekat.classes.UserClass
import com.example.rmasprojekat.repositories.UserRepository
import com.example.rmasprojekat.ui.UsersVM
import com.example.rmasprojekat.ui.UsersVMFactory
import com.example.rmasprojekat.ui.theme.Amber
import com.example.rmasprojekat.ui.theme.fontJockey

@Composable
fun UserListScreen(
    onNavigateToMain: () -> Unit,
    onNavigateToSales: () -> Unit,
    userRep: UserRepository
) {


    val vwModel: UsersVM = viewModel(factory = UsersVMFactory(userRep))
    val users by vwModel.users.collectAsState()
    val currUID by vwModel.usrUID.collectAsState()


    LaunchedEffect(Unit) {
        vwModel.getAllUsers()
        vwModel.getCurrentUID()
        Log.w("TEST", "RADI")
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                FloatingActionButton(
                    containerColor = Color.White,
                    contentColor = Amber,
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(20.dp),
                    onClick = { onNavigateToMain() }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "goBack")
                }
                Text(
                    text = "Lista korisnika",
                    fontFamily = fontJockey,
                    fontSize = 30.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
                Text(
                    text = "/",
                    fontSize = 40.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
                ClickableText(
                    onClick = {
                        onNavigateToSales()
                    },
                    text = AnnotatedString("Lista akcija"),
                    style = TextStyle(
                        fontFamily = fontJockey,
                        fontSize = 20.sp,
                        color = Amber,
                    ),
                    modifier = Modifier
                        .padding(horizontal = 5.dp),
                )
            }
        }
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                users?.forEachIndexed { index, user ->
                    if (user.uid == currUID) {
                        Surface(
                            color = Amber,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .heightIn(50.dp, 50.dp),
                            shape = RoundedCornerShape(20),

                            ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "${index + 1}.",
                                    color = Color.White,
                                    fontFamily = fontJockey,
                                    fontSize = 20.sp
                                )
                                Text(
                                    text = user.ime + " " + user.prezime,
                                    color = Color.White,
                                    fontFamily = fontJockey,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "broj bodova:",
                                    color = Color.White,
                                    fontFamily = fontJockey,
                                    fontSize = 20.sp
                                )
                                Text(
                                    text = user.bod.toString(),
                                    color = Color.White,
                                    fontFamily = fontJockey,
                                    fontSize = 20.sp
                                )
                            }
                        }
                    } else {
                        Surface(
                            border = BorderStroke(1.dp, color = Amber),
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .heightIn(50.dp, 50.dp),
                            shape = RoundedCornerShape(20),

                            ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "${index + 1}.",
                                    color = Amber,
                                    fontFamily = fontJockey,
                                    fontSize = 20.sp
                                )
                                Text(
                                    text = user.ime + " " + user.prezime,
                                    color = Amber,
                                    fontFamily = fontJockey,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "broj bodova:",
                                    color = Amber,
                                    fontFamily = fontJockey,
                                    fontSize = 20.sp
                                )
                                Text(
                                    text = user.bod.toString(),
                                    color = Amber,
                                    fontFamily = fontJockey,
                                    fontSize = 20.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
