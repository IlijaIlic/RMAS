package com.example.rmasprojekat.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rmasprojekat.ui.theme.Amber
import com.example.rmasprojekat.ui.theme.fontJockey

@Composable
fun UserListScreen(onNavigateToMain: () -> Unit) {
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

            }
        }
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Surface(

                    color = Amber,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .heightIn(50.dp, 50.dp),
                    shape = RoundedCornerShape(20),

                    ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "1.",
                            color = Color.White,
                            fontFamily = fontJockey,
                            fontSize = 20.sp
                        )
                        Text(
                            text = "Pera Peric",
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
                            text = "156",
                            color = Color.White,
                            fontFamily = fontJockey,
                            fontSize = 20.sp
                        )
                    }
                }
                for (i in 2..100) {
                    Surface(
                        border = BorderStroke(2.dp, color = Amber),
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .heightIn(50.dp, 50.dp),
                        shape = RoundedCornerShape(20),

                        ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "${i}.",
                                color = Amber,
                                fontFamily = fontJockey,
                                fontSize = 20.sp
                            )
                            Text(
                                text = "Steva Stevic",
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
                                text = "13",
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