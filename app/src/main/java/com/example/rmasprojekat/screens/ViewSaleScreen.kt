package com.example.rmasprojekat.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rmasprojekat.R
import com.example.rmasprojekat.components.LoadImageSale
import com.example.rmasprojekat.ui.ViewSaleVM
import com.example.rmasprojekat.ui.theme.Amber
import com.example.rmasprojekat.ui.theme.fontJockey
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft
import compose.icons.feathericons.Circle
import compose.icons.feathericons.Eye
import compose.icons.feathericons.EyeOff
import compose.icons.feathericons.Heart
import compose.icons.feathericons.XCircle

@Composable
fun ViewSaleScreen(
    onNavigateToMain: () -> Unit,
    vwModel: ViewSaleVM
) {

    val prodavnica by vwModel.prodavnicaSale.collectAsState()
    val opis by vwModel.opisSale.collectAsState()
    val liked by vwModel.likedSale.collectAsState()
    val autor by vwModel.autorSale.collectAsState()
    val imageURL by vwModel.slikaURL.collectAsState()
    val bodovi by vwModel.bodovi.collectAsState()
    val autorIme by vwModel.autorIme.collectAsState()
    val autorPrezime by vwModel.autorPrezime.collectAsState()
    val datumIsteka by vwModel.datumIsteka.collectAsState()

    LaunchedEffect(Unit) {
        vwModel.isLiked()
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        color = Amber
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),

            ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                FloatingActionButton(
                    onClick = { onNavigateToMain() },
                    containerColor = Color.White,
                    contentColor = Amber,
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(10.dp),

                    ) {
                    Icon(imageVector = FeatherIcons.ArrowLeft, contentDescription = "Go Back")
                }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    textAlign = TextAlign.Center,
                    text = prodavnica,
                    fontFamily = fontJockey,
                    fontSize = 70.sp,
                    color = Color.White
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                LoadImageSale(
                    url = imageURL
                )
                Text(
                    text = opis,
                    fontFamily = fontJockey,
                    color = Color.White,
                    fontSize = 30.sp,
                    modifier = Modifier.padding(vertical = 10.dp)
                )

                IconButton(
                    onClick = {
                        vwModel.onLikeClick()
                        vwModel.updateLikedSale(!liked)
                    },
                ) {
                    Icon(
                        imageVector =
                        if (liked)
                            FeatherIcons.XCircle
                        else FeatherIcons.Heart,
                        contentDescription = "Like",
                        tint = Color.White
                    )
                }

                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = "bodovi: $bodovi",
                        fontFamily = fontJockey,
                        color = Color.White,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                    Text(
                        text = "autor: $autorIme $autorPrezime",
                        fontFamily = fontJockey,
                        color = Color.White,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )

                }
                Text(
                    text = "datum isteka akcije: $datumIsteka",
                    fontFamily = fontJockey,
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )

            }
        }
    }
}
