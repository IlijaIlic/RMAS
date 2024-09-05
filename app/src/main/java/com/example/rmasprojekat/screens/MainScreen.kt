package com.example.rmasprojekat.screens

import com.example.rmasprojekat.R
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import android.Manifest
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.Lifecycle
import com.example.rmasprojekat.repositories.OglasRepository
import com.example.rmasprojekat.repositories.UserRepository
import com.example.rmasprojekat.ui.AddPlaceVM
import com.example.rmasprojekat.ui.AddPlaceVMFactory
import com.example.rmasprojekat.ui.MainVM
import com.example.rmasprojekat.ui.MainVMFactory
import com.example.rmasprojekat.ui.ViewSaleVM
import com.example.rmasprojekat.ui.theme.Amber
import com.example.rmasprojekat.ui.theme.AmberLight
import com.example.rmasprojekat.ui.theme.fontJockey
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import compose.icons.FeatherIcons
import compose.icons.TablerIcons
import compose.icons.feathericons.ArrowLeft
import compose.icons.feathericons.Check
import compose.icons.feathericons.Crosshair
import compose.icons.feathericons.Disc
import compose.icons.feathericons.Flag
import compose.icons.feathericons.MapPin
import compose.icons.feathericons.Plus
import compose.icons.feathericons.RefreshCw
import compose.icons.feathericons.Search
import compose.icons.feathericons.User
import compose.icons.feathericons.X
import compose.icons.tablericons.MapPin
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigateToLanding: () -> Unit,
    onNavigateToAddPlace: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToUserList: () -> Unit,
    onNavigateToViewSale: () -> Unit,
    oglasRepository: OglasRepository,
    userRep: UserRepository,
    viewSaleVM: ViewSaleVM,
    painterUsr: Painter
) {

    val vwModel: MainVM = viewModel(factory = MainVMFactory(oglasRepository, userRep))

    val openDialog by vwModel.openDialogMain.collectAsState()
    val showDate by vwModel.showDateMain.collectAsState()
    val isExpandedProd by vwModel.isExpandedProdMain.collectAsState()
    val selTextProd by vwModel.selTextProdMain.collectAsState()
    val dateState = vwModel.dateState
    val context = LocalContext.current
    val usrLocation by vwModel.userLocation.collectAsState()
    val cameraPositionState by vwModel.cameraPositionState.collectAsState()
    val sales by vwModel.sales.collectAsState()
    val sliderPos by vwModel.slidePosMain.collectAsState()
    val markerState by vwModel.markerState.collectAsState()


    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {
            Lifecycle.State.DESTROYED -> {}
            Lifecycle.State.INITIALIZED -> {}
            Lifecycle.State.CREATED -> {
                vwModel.getAllSales()
            }

            Lifecycle.State.STARTED -> {}
            Lifecycle.State.RESUMED -> {
                vwModel.getAllSales()
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            vwModel.stopLocationUpdates(context)
        }
    }


    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            vwModel.getCurrentLocation(context)
        } else {
        }
    }

    LaunchedEffect(Unit) {
        when {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                vwModel.getCurrentLocation(context)
            }

            else -> {
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    val nis = LatLng(43.321445, 21.896104)

    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                zoomControlsEnabled = false,
                compassEnabled = false,
            )
        )
    }

    val properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = properties,
            uiSettings = uiSettings
        ) {
            usrLocation?.let { location ->
                val userLatLng = LatLng(location.latitude, location.longitude)
//                MarkerComposable(
//                    state = MarkerState(position = location),
//                    title = "Vasa lokacija",
//                ) {
//
//                    Box(
//                        modifier = Modifier.size(60.dp),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Image(
//                            painter = painterUsr,
//                            contentDescription = "Korisnik",
//                            modifier = Modifier.size(40.dp)
//                        )
//                    }
//                }
                vwModel.onUpdatePosition(location)
                Marker(
                    state = markerState,
                    title = "Vasa lokacija",
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
                )
                Circle(
                    center = userLatLng,
                    radius = (sliderPos * 50.0),
                    strokeColor = Amber,
                    fillColor = Color(0x2CFF0008),
                    strokeWidth = 2f
                )
            }
            sales?.forEach { sale ->
                Marker(
                    state = MarkerState(position = sale.lokacija ?: nis),
                    title = sale.opis,
                    onInfoWindowClick = {
                        viewSaleVM.updateOpisSale(sale.opis)
                        viewSaleVM.updateProdavnSale(sale.prod)
                        viewSaleVM.updateAutor(sale.autor)
                        viewSaleVM.updateSlikaURL(sale.slikaURL)
                        viewSaleVM.updateDatumISteka(sale.datumIsteka)
                        viewSaleVM.updateDocumentID(sale.documentID)
                        viewSaleVM.updateBodovi(sale.bodovi)
                        viewSaleVM.updateAutorIme(sale.imeAutora)
                        viewSaleVM.updateAutorPrezime(sale.prezimeAutora)
                        onNavigateToViewSale()
                    }
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                FloatingActionButton(
                    onClick = { onNavigateToProfile() },
                    containerColor = Amber,
                    contentColor = Color.White,
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(10.dp)
                ) {
                    Icon(
                        imageVector = FeatherIcons.User,
                        contentDescription = "Profile"
                    )
                }
                Column {
                    FloatingActionButton(
                        onClick = { onNavigateToUserList() },
                        containerColor = Amber,
                        contentColor = Color.White,
                        shape = CircleShape,
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        Icon(imageVector = FeatherIcons.Search, contentDescription = "Profile")
                    }
                    FloatingActionButton(
                        onClick = { vwModel.getAllSales() },
                        containerColor = Amber,
                        contentColor = Color.White,
                        shape = CircleShape,
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        Icon(imageVector = FeatherIcons.RefreshCw, contentDescription = "Profile")
                    }
                }

            }
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                FloatingActionButton(
                    containerColor = Amber,
                    contentColor = Color.White,
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(10.dp),
                    onClick = { vwModel.updateOpenDialog(true) },
                ) {
                    Icon(imageVector = FeatherIcons.Disc, contentDescription = "List")
                }
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier.fillMaxSize()
                ) {
                    FloatingActionButton(
                        containerColor = Amber,
                        contentColor = Color.White,
                        shape = CircleShape,
                        modifier = Modifier
                            .padding(10.dp),
                        onClick = {
                            try {
                                vwModel.updateCameraPosition(
                                    CameraPositionState(
                                        CameraPosition(
                                            usrLocation!!,
                                            18f,
                                            0f,
                                            0f
                                        )
                                    )
                                )
                            } catch (e: Exception) {
                                Log.w("ERROR", "Cant recenter!")
                            }
                        },
                    ) {
                        Icon(
                            imageVector = FeatherIcons.Crosshair,
                            contentDescription = "ViewPlace"
                        )
                    }
                    FloatingActionButton(
                        containerColor = Amber,
                        contentColor = Color.White,
                        shape = CircleShape,
                        modifier = Modifier
                            .padding(10.dp),
                        onClick = {
                            onNavigateToAddPlace()
                        },
                    ) {
                        Icon(imageVector = FeatherIcons.Plus, contentDescription = "Add place")
                    }

                    when {
                        openDialog -> {
                            Dialog(onDismissRequest = { /*TODO*/ }) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(600.dp)
                                        .padding(16.dp)
                                        .verticalScroll(rememberScrollState()),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        FloatingActionButton(
                                            shape = CircleShape,
                                            onClick = { vwModel.updateOpenDialog(false) },
                                            containerColor = Color.White,
                                            modifier = Modifier
                                                .padding(10.dp)
                                                .shadow(4.dp, CircleShape)
                                        ) {
                                            Icon(
                                                imageVector = FeatherIcons.ArrowLeft,
                                                contentDescription = "Back",
                                                tint = Amber
                                            )
                                        }
                                        Surface(
                                            modifier = Modifier
                                                .height(400.dp)
                                                .fillMaxWidth(),
                                            color = Color.White
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(10.dp)
                                            ) {
                                                ExposedDropdownMenuBox(
                                                    expanded = isExpandedProd,
                                                    onExpandedChange = {
                                                        vwModel.updateIsExpandedProd(!isExpandedProd)
                                                    }) {
                                                    Column {
                                                        Text(
                                                            text = "Izaberite prodavnicu",
                                                            fontFamily = fontJockey
                                                        )
                                                        TextField(
                                                            textStyle = TextStyle.Default.copy(
                                                                fontFamily = fontJockey
                                                            ),
                                                            value = selTextProd,
                                                            onValueChange = {},
                                                            modifier = Modifier
                                                                .menuAnchor()
                                                                .padding(10.dp)
                                                                .widthIn(0.dp, 250.dp)
                                                                .shadow(
                                                                    3.dp,
                                                                    RoundedCornerShape(12.dp)
                                                                ),

                                                            colors = TextFieldDefaults.colors(
                                                                focusedContainerColor = Color.White,
                                                                unfocusedContainerColor = Color.White,
                                                                focusedIndicatorColor = Color.Transparent,
                                                                unfocusedIndicatorColor = Color.Transparent,
                                                            ),
                                                            trailingIcon = {
                                                                ExposedDropdownMenuDefaults.TrailingIcon(
                                                                    expanded = isExpandedProd
                                                                )
                                                            },
                                                            readOnly = true
                                                        )
                                                        ExposedDropdownMenu(
                                                            expanded = isExpandedProd,
                                                            onDismissRequest = {
                                                                vwModel.updateIsExpandedProd(false)
                                                            }) {
                                                            DropdownMenuItem(
                                                                text = {
                                                                    Text(
                                                                        text = "Roda",
                                                                        fontFamily = fontJockey
                                                                    )
                                                                },
                                                                onClick = {
                                                                    vwModel.updateSelTextProdMain("Roda")
                                                                    vwModel.updateIsExpandedProd(
                                                                        false
                                                                    )
                                                                })
                                                            DropdownMenuItem(
                                                                text = {
                                                                    Text(
                                                                        text = "Idea",
                                                                        fontFamily = fontJockey
                                                                    )
                                                                },
                                                                onClick = {
                                                                    vwModel.updateSelTextProdMain("Idea")
                                                                    vwModel.updateIsExpandedProd(
                                                                        false
                                                                    )
                                                                })
                                                            DropdownMenuItem(
                                                                text = {
                                                                    Text(
                                                                        text = "Lidl",
                                                                        fontFamily = fontJockey
                                                                    )
                                                                },
                                                                onClick = {
                                                                    vwModel.updateSelTextProdMain("Lidl")
                                                                    vwModel.updateIsExpandedProd(
                                                                        false
                                                                    )
                                                                })
                                                        }
                                                    }

                                                }
                                                Text(
                                                    text = "Trajanje akcije nakon",
                                                    fontFamily = fontJockey
                                                )
                                                FloatingActionButton(
                                                    onClick = { vwModel.updateShowDate(true) },
                                                    shape = RoundedCornerShape(12.dp),
                                                    modifier = Modifier
                                                        .padding(10.dp)
                                                        .widthIn(250.dp, 250.dp)
                                                        .shadow(
                                                            3.dp,
                                                            RoundedCornerShape(12.dp)
                                                        ),
                                                    containerColor = Color.White
                                                ) {
                                                    Text(
                                                        text = vwModel.getFormattedDate(),
                                                        textAlign = TextAlign.Start,
                                                        fontFamily = fontJockey,
                                                        color = Color.Black
                                                    )
                                                }
                                                Text(
                                                    text = "Obim pretrage",
                                                    fontFamily = fontJockey
                                                )
                                                Slider(
                                                    value = sliderPos,
                                                    onValueChange = { vwModel.updateSlidePos(it) },
                                                    colors = SliderDefaults.colors(
                                                        thumbColor = Amber,
                                                        activeTrackColor = Amber,
                                                        inactiveTrackColor = AmberLight,
                                                    ),
                                                    steps = 3,
                                                    valueRange = 0f..50f
                                                )

                                            }
                                            if (showDate) {
                                                DatePickerDialog(
                                                    colors = DatePickerDefaults.colors(
                                                        containerColor = Color.White,
                                                    ),
                                                    modifier = Modifier.verticalScroll(
                                                        rememberScrollState()
                                                    ),
                                                    onDismissRequest = { vwModel.updateShowDate(true) },
                                                    confirmButton = {
                                                        Button(
                                                            colors = ButtonDefaults.buttonColors(
                                                                contentColor = Color.White,
                                                                containerColor = Amber
                                                            ),
                                                            onClick = { vwModel.updateShowDate(false) }
                                                        ) {
                                                            Text(text = "Prihvati")
                                                        }
                                                    },
                                                    dismissButton = {
                                                        Button(
                                                            colors = ButtonDefaults.buttonColors(
                                                                contentColor = Color.White,
                                                                containerColor = Amber
                                                            ),
                                                            onClick = { vwModel.updateShowDate(false) }
                                                        ) {
                                                            Text(text = "Odustani")
                                                        }
                                                    },
                                                ) {
                                                    DatePicker(
                                                        state = dateState.value,
                                                        showModeToggle = true,
                                                        colors = DatePickerDefaults.colors(
                                                            containerColor = Color.White,
                                                            titleContentColor = Amber,
                                                            headlineContentColor = Amber,
                                                            weekdayContentColor = Color.Black,
                                                            subheadContentColor = Color.Black,
                                                            dayContentColor = Color.Black,
                                                            selectedDayContainerColor = Amber,
                                                            selectedDayContentColor = AmberLight,
                                                            todayContentColor = AmberLight,
                                                            todayDateBorderColor = AmberLight,
                                                            dayInSelectionRangeContentColor = Color.White,
                                                            dayInSelectionRangeContainerColor = AmberLight,
                                                            disabledDayContentColor = Color.Gray,
                                                        )
                                                    )

                                                }
                                            }
                                        }

                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(100.dp),
                                            verticalAlignment = Alignment.Bottom,
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                        ) {
                                            Button(
                                                onClick = {
                                                    vwModel.resetFilters()
                                                    vwModel.updateOpenDialog(false)
                                                },
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = Amber
                                                ),
                                                modifier = Modifier.padding(10.dp)
                                            ) {
                                                Icon(
                                                    imageVector = FeatherIcons.X,
                                                    contentDescription = "Close"
                                                )
                                            }
                                            Button(
                                                onClick = {
                                                    vwModel.filterSales()
                                                    vwModel.updateOpenDialog(false)
                                                },
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = Amber
                                                ),
                                                modifier = Modifier.padding(10.dp)
                                            ) {
                                                Icon(
                                                    imageVector = FeatherIcons.Check,
                                                    contentDescription = "Done"
                                                )
                                            }
                                        }
                                    }


                                }
                            }

                        }
                    }
                }
            }

        }
    }
}