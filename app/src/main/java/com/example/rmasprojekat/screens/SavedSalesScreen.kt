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
import com.example.rmasprojekat.ui.SaveSalesVM
import com.example.rmasprojekat.ui.SaveSalesVMFactory
import com.example.rmasprojekat.ui.ViewSaleVM
import com.example.rmasprojekat.ui.theme.Amber
import com.example.rmasprojekat.ui.theme.fontJockey
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft
import compose.icons.feathericons.Trash

@Composable
fun SavedSalesScreen(
    onNavigateToProfile: () -> Unit,
    onSaleClick: () -> Unit,
    oglasRep: OglasRepository,
    viewSaleVM: ViewSaleVM
) {

    val vwModel: SaveSalesVM = viewModel(factory = SaveSalesVMFactory(oglasRep = oglasRep))
    val savedSalesList by vwModel.savedSalesList.collectAsState()

    LaunchedEffect(Unit) {
        vwModel.getSavedSales()
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
                    text = "Sacuvane akcije",
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
                savedSalesList?.forEachIndexed { index, oglas ->
                    Button(
                        onClick = {
                            viewSaleVM.updateOpisSale(oglas.opis)
                            viewSaleVM.updateProdavnSale(oglas.prod)
                            viewSaleVM.updateAutor(oglas.autor)
                            viewSaleVM.updateSlikaURL(oglas.slikaURL)
                            viewSaleVM.updateDatumISteka(oglas.datumIsteka)
                            viewSaleVM.updateDocumentID(oglas.documentID)
                            viewSaleVM.updateBodovi(oglas.bodovi)
                            viewSaleVM.updateAutorIme(oglas.imeAutora)
                            viewSaleVM.updateAutorPrezime(oglas.prezimeAutora)
                            onSaleClick()
                        },
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
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp
                            )
                            Text(text = "  -  ", fontFamily = fontJockey)
                            Text(text = oglas.opis.lines()[0], fontFamily = fontJockey)
                        }
                    }
                }
            }
        }
    }
}