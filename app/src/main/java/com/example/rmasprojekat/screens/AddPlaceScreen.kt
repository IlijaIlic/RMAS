package com.example.rmasprojekat.screens

import com.example.rmasprojekat.R
import androidx.compose.ui.res.painterResource
import android.graphics.drawable.Icon
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.rmasprojekat.repositories.OglasRepository
import com.example.rmasprojekat.ui.AddPlaceVM
import com.example.rmasprojekat.ui.AddPlaceVMFactory
import com.example.rmasprojekat.ui.ProfileVM
import com.example.rmasprojekat.ui.ProfileVMFactory
import com.example.rmasprojekat.ui.theme.Amber
import com.example.rmasprojekat.ui.theme.AmberLight
import com.example.rmasprojekat.ui.theme.fontJockey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlace(
    onNavigateToMain: () -> Unit,
    oglasiRep: OglasRepository
) {
    val vwModel: AddPlaceVM = viewModel(factory = AddPlaceVMFactory(oglasiRep))
    val opis by vwModel.opisAdd.collectAsState()
    val selText by vwModel.selTextAdd.collectAsState()
    val isExpanded by vwModel.isExpandedAdd.collectAsState()
    val showDate by vwModel.showDateAdd.collectAsState()
    val dateState = vwModel.dateState
    val cameraEvent by vwModel.cameraEvent.collectAsState()
    val okToAdd by vwModel.okToAdd.collectAsState()
    val okImage by vwModel.okImage.collectAsState()
    val image by vwModel.imageTaken.collectAsState()
    val uploadSuc by vwModel.uploadSuc.collectAsState()

    val context = LocalContext.current

    val imageForDisplay = @androidx.compose.runtime.Composable {
        if (!okImage) {
            painterResource(R.drawable.noimageborder)
        } else {
            rememberAsyncImagePainter(image)
        }
    }


    LaunchedEffect(Unit) {
        vwModel.getCurrentLocation(context)
    }

    LaunchedEffect(uploadSuc) {
        if (uploadSuc) {
            onNavigateToMain()
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

    if (cameraEvent) {
        launcher.launch(null)
        vwModel.onCameraEventHandled()
    }

    Surface(
        color = Amber,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Dodaj novu akciju!",
                color = Color.White,
                fontFamily = fontJockey,
                fontSize = 40.sp,
                modifier = Modifier.padding(20.dp)
            )


            ExposedDropdownMenuBox(
                expanded = isExpanded,
                onExpandedChange = { vwModel.updateIsExpanded(!isExpanded) }) {
                TextField(
                    textStyle = TextStyle.Default.copy(fontFamily = fontJockey),
                    value = selText,
                    onValueChange = {},
                    modifier = Modifier
                        .menuAnchor()
                        .padding(10.dp)
                        .widthIn(0.dp, 250.dp)
                        .shadow(3.dp, RoundedCornerShape(12.dp)),

                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                    readOnly = true
                )
                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { vwModel.updateIsExpanded(false) }) {
                    DropdownMenuItem(
                        text = { Text(text = "Idea", fontFamily = fontJockey) },
                        onClick = {
                            vwModel.updateSelText("Idea")
                            vwModel.updateIsExpanded(false)
                        })
                    DropdownMenuItem(
                        text = { Text(text = "Roda", fontFamily = fontJockey) },
                        onClick = {
                            vwModel.updateSelText("Roda")
                            vwModel.updateIsExpanded(false)
                        })
                    DropdownMenuItem(
                        text = { Text(text = "Lidl", fontFamily = fontJockey) },
                        onClick = {
                            vwModel.updateSelText("Lidl")
                            vwModel.updateIsExpanded(false)
                        })
                }

            }
            TextField(
                value = opis,
                onValueChange = { newText -> vwModel.updateOpisTp(newText) },
                singleLine = false,
                placeholder = {
                    Text(
                        "dodaj opis",
                        color = Color.LightGray,
                        fontFamily = fontJockey
                    )
                },
                modifier = Modifier
                    .padding(10.dp)
                    .shadow(3.dp, RoundedCornerShape(12.dp))
                    .height(150.dp)
                    .widthIn(0.dp, 250.dp)
                    .heightIn(0.dp, 150.dp),

                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
            )

            if (showDate) {
                DatePickerDialog(
                    colors = DatePickerDefaults.colors(
                        containerColor = Color.White,
                    ),
                    modifier = Modifier
                        .verticalScroll(rememberScrollState()),
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
                        state = dateState.value, showModeToggle = true,
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

            Image(
                painter = imageForDisplay(),
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(250.dp)
                    .clip(RoundedCornerShape(12.dp))

            )
            FloatingActionButton(
                modifier = Modifier
                    .padding(10.dp)
                    .shadow(
                        3.dp, RoundedCornerShape(12.dp)
                    ),
                onClick = { vwModel.onOpenCamera() },
                containerColor = Color.White,
                shape = RoundedCornerShape(20),
                contentColor = Amber
            ) {
                Icon(
                    tint = Amber,
                    imageVector = Icons.Outlined.AddCircle,
                    contentDescription = "Fotografija",
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(
                    onClick = { vwModel.updateShowDate(true) },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Amber,
                        containerColor = Color.White
                    )
                ) {
                    Text(
                        text = "Odaberi datum isteka akcije",
                        fontFamily = fontJockey,
                        color = Amber
                    )
                }
                Text(
                    text = vwModel.getFormattedDate(),
                    color = Color.White,
                    fontFamily = fontJockey,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.Bottom
            ) {

                FloatingActionButton(
                    containerColor = Color.White,
                    contentColor = Amber,
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.Bottom),
                    onClick = { onNavigateToMain() },
                ) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Add place")
                }
                Button(
                    enabled = okToAdd,
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Amber,
                        disabledContainerColor = Color.Gray
                    ),
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.Bottom)
                        .clip(CircleShape)
                        .size(56.dp),
                    onClick = {
                        vwModel.onAddPlace(context = context)
                    },
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 3.dp
                    )
                ) {
                    Icon(imageVector = Icons.Filled.Done, contentDescription = "Cancel")
                }

            }
        }
    }
}