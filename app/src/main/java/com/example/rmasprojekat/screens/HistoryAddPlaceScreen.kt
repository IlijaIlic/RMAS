package com.example.rmasprojekat.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rmasprojekat.repositories.OglasRepository
import com.example.rmasprojekat.ui.HistoryVM
import com.example.rmasprojekat.ui.HistoryVMFactory
import com.example.rmasprojekat.ui.UsersVM
import com.example.rmasprojekat.ui.UsersVMFactory
import com.example.rmasprojekat.ui.theme.Amber
import com.example.rmasprojekat.ui.theme.fontJockey
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft
import compose.icons.feathericons.Trash

@Composable
fun HistoryAddPlaceScreen(
    onNavigateToProfile: () -> Unit,
    oglasRep: OglasRepository
) {

    val vwModel: HistoryVM = viewModel(factory = HistoryVMFactory(oglasRep))
    val historyList by vwModel.historyList.collectAsState()

    LaunchedEffect(Unit) {
        vwModel.getHistoryOfAddedPlaces()
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
                    onClick = { onNavigateToProfile() }) {
                    Icon(imageVector = FeatherIcons.ArrowLeft, contentDescription = "goBack")
                }
                Text(
                    text = "Istorija postavljenih akcija",
                    fontFamily = fontJockey,
                    fontSize = 30.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
        }
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                historyList?.forEachIndexed { index, oglas ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Amber
                            ),
                            modifier = Modifier
                                .widthIn(300.dp, 300.dp)
                                .heightIn(70.dp, 70.dp)
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            elevation = ButtonDefaults.elevatedButtonElevation(
                                defaultElevation = 3.dp
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceAround,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = oglas.prod,
                                    fontFamily = fontJockey,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = "  -  ", fontFamily = fontJockey)
                                Text(text = oglas.opis.lines()[0], fontFamily = fontJockey)
                            }
                        }
                        Button(
                            onClick = {
                                vwModel.deleteSale(oglas.documentID)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Amber
                            ),
                            modifier = Modifier
                                .heightIn(70.dp, 70.dp)
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            elevation = ButtonDefaults.elevatedButtonElevation(
                                defaultElevation = 3.dp
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(
                                imageVector = FeatherIcons.Trash,
                                contentDescription = "Delete",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}