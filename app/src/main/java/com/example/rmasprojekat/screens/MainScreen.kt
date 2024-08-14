package com.example.rmasprojekat.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.rmasprojekat.ui.theme.Amber
import com.example.rmasprojekat.ui.theme.AmberLight
import com.example.rmasprojekat.ui.theme.fontJockey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigateToLanding: () -> Unit,
    onNavigateToAddPlace: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToUserList: () -> Unit,
    onNavigateToViewSale: () -> Unit,
) {

    var sliderPosition by remember { mutableFloatStateOf(0f) }
    var openDialog by remember { mutableStateOf(false) }
    var showDate by remember { mutableStateOf(false) }
    var isExpanded by remember { mutableStateOf(false) }
    var selText by remember { mutableStateOf(TextFieldValue("Izaberi Lokaciju")) }
    var isExpandedProd by remember { mutableStateOf(false) }
    var selTextProd by remember { mutableStateOf(TextFieldValue("Izaberi Prodavnicu")) }
    val dateState = rememberDatePickerState()

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
                Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "Profile")
            }
            FloatingActionButton(
                onClick = { onNavigateToUserList() },
                containerColor = Amber,
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Profile")
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
                onClick = { openDialog = true },
            ) {
                Icon(imageVector = Icons.Filled.List, contentDescription = "List")
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
                    onClick = { onNavigateToAddPlace() },
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Add place")
                }
                FloatingActionButton(
                    containerColor = Amber,
                    contentColor = Color.White,
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(10.dp),
                    onClick = { onNavigateToViewSale() },
                ) {
                    Icon(imageVector = Icons.Filled.LocationOn, contentDescription = "ViewPlace")
                }
                when {
                    openDialog -> {
                        Dialog(onDismissRequest = { /*TODO*/ }) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(600.dp)
                                    .padding(16.dp),
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
                                        onClick = { openDialog = false },
                                        containerColor = Color.White,
                                        modifier = Modifier
                                            .padding(10.dp)
                                            .shadow(4.dp, CircleShape)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.ArrowBack,
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
                                                expanded = isExpanded,
                                                onExpandedChange = {
                                                    isExpanded = !isExpanded
                                                }) {
                                                TextField(
                                                    textStyle = TextStyle.Default.copy(
                                                        fontFamily = fontJockey
                                                    ),
                                                    value = selText,
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
                                                            expanded = isExpanded
                                                        )
                                                    },
                                                    readOnly = true
                                                )
                                                ExposedDropdownMenu(
                                                    expanded = isExpanded,
                                                    onDismissRequest = { isExpanded = false }) {
                                                    DropdownMenuItem(
                                                        text = {
                                                            Text(
                                                                text = "Nis",
                                                                fontFamily = fontJockey
                                                            )
                                                        },
                                                        onClick = {
                                                            selText = TextFieldValue("Nis")
                                                            isExpanded = false
                                                        })
                                                    DropdownMenuItem(
                                                        text = {
                                                            Text(
                                                                text = "Beograd",
                                                                fontFamily = fontJockey
                                                            )
                                                        },
                                                        onClick = {
                                                            selText = TextFieldValue("Beograd")
                                                            isExpanded = false
                                                        })
                                                    DropdownMenuItem(
                                                        text = {
                                                            Text(
                                                                text = "Novi Sad",
                                                                fontFamily = fontJockey
                                                            )
                                                        },
                                                        onClick = {
                                                            selText = TextFieldValue("Novi Sad")
                                                            isExpanded = false
                                                        })
                                                }

                                            }
                                            ExposedDropdownMenuBox(
                                                expanded = isExpandedProd,
                                                onExpandedChange = {
                                                    isExpandedProd = !isExpandedProd
                                                }) {
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
                                                    onDismissRequest = { isExpandedProd = false }) {
                                                    DropdownMenuItem(
                                                        text = {
                                                            Text(
                                                                text = "Roda",
                                                                fontFamily = fontJockey
                                                            )
                                                        },
                                                        onClick = {
                                                            selTextProd = TextFieldValue("Roda")
                                                            isExpandedProd = false
                                                        })
                                                    DropdownMenuItem(
                                                        text = {
                                                            Text(
                                                                text = "Idea",
                                                                fontFamily = fontJockey
                                                            )
                                                        },
                                                        onClick = {
                                                            selTextProd = TextFieldValue("Idea")
                                                            isExpandedProd = false
                                                        })
                                                    DropdownMenuItem(
                                                        text = {
                                                            Text(
                                                                text = "Lidl",
                                                                fontFamily = fontJockey
                                                            )
                                                        },
                                                        onClick = {
                                                            selTextProd = TextFieldValue("Lidl")
                                                            isExpandedProd = false
                                                        })
                                                }

                                            }
                                            FloatingActionButton(
                                                onClick = { showDate = true },
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
                                                    text = "Izaberite datum isteka akcije",
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
                                                value = sliderPosition,
                                                onValueChange = { sliderPosition = it },
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
                                                onDismissRequest = { showDate = true },
                                                confirmButton = {
                                                    Button(
                                                        colors = ButtonDefaults.buttonColors(
                                                            contentColor = Color.White,
                                                            containerColor = Amber
                                                        ),
                                                        onClick = { showDate = false }
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
                                                        onClick = { showDate = false }
                                                    ) {
                                                        Text(text = "Odustani")
                                                    }
                                                },
                                            ) {
                                                DatePicker(
                                                    state = dateState, showModeToggle = true,
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
                                            onClick = { openDialog = false },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Amber
                                            ),
                                            modifier = Modifier.padding(10.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.Close,
                                                contentDescription = "Close"
                                            )
                                        }
                                        Button(
                                            onClick = { openDialog = false },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Amber
                                            ),
                                            modifier = Modifier.padding(10.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.Done,
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