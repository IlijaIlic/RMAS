package com.example.rmasprojekat.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rmasprojekat.repositories.OglasRepository
import com.example.rmasprojekat.repositories.UserRepository
import com.example.rmasprojekat.ui.RegisterVM
import com.example.rmasprojekat.ui.RegisterVMFactory
import com.example.rmasprojekat.ui.SalesVM
import com.example.rmasprojekat.ui.SalesVMFactory
import com.example.rmasprojekat.ui.ViewSaleVM
import com.example.rmasprojekat.ui.theme.Amber
import com.example.rmasprojekat.ui.theme.AmberLight
import com.example.rmasprojekat.ui.theme.fontJockey
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft
import compose.icons.feathericons.Check
import compose.icons.feathericons.Search
import compose.icons.feathericons.Tool
import compose.icons.feathericons.X

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalesListScreen(
    onNavigateToMain: () -> Unit, onNavigateToUsers: () -> Unit,
    oglasRepository: OglasRepository,
    userRepository: UserRepository,
    onSaleClick: () -> Unit,
    viewSaleVM: ViewSaleVM
) {

    val vwModel: SalesVM =
        viewModel(factory = SalesVMFactory(oglasRep = oglasRepository, userRep = userRepository))
    val search by vwModel.searchSales.collectAsState()
    val openDialog by vwModel.openDialogMain.collectAsState()
    val showDate by vwModel.showDateMain.collectAsState()
    val isExpandedProd by vwModel.isExpandedProdMain.collectAsState()
    val selTextProd by vwModel.selTextProdMain.collectAsState()
    val dateState = vwModel.dateState
    val sales by vwModel.sales.collectAsState()

    LaunchedEffect(Unit) {
        vwModel.getAllSales()
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
                    Icon(imageVector = FeatherIcons.ArrowLeft, contentDescription = "goBack")
                }
                ClickableText(
                    onClick = { onNavigateToUsers() },
                    text = AnnotatedString("Lista korisnika"),
                    style = TextStyle(
                        fontFamily = fontJockey,
                        fontSize = 20.sp,
                        color = Amber,
                    ),
                    modifier = Modifier
                        .padding(horizontal = 5.dp),
                )
                Text(
                    text = "/",
                    fontSize = 40.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
                Text(
                    text = "Lista akcija",
                    fontFamily = fontJockey,
                    fontSize = 30.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
        }
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    shape = RoundedCornerShape(30.dp),
                    placeholder = {
                        Text(
                            "Pretrazite akcije",
                            color = Color.Gray,
                            fontFamily = fontJockey
                        )
                    },
                    value = search,
                    onValueChange = { newText -> vwModel.updateSearchSales(newText) },
                    modifier = Modifier
                        .padding(5.dp)
                        .widthIn(300.dp, 300.dp)
                        .shadow(3.dp, RoundedCornerShape(30.dp)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    trailingIcon = {
                        Icon(
                            imageVector = FeatherIcons.Search,
                            contentDescription = "Search"
                        )
                    },
                    singleLine = true
                )
                FloatingActionButton(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .widthIn(150.dp, 150.dp)
                        .padding(vertical = 10.dp)
                        .shadow(5.dp, shape = RoundedCornerShape(30.dp)),
                    onClick = { vwModel.updateOpenDialog(true) },
                    containerColor = Color.White
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            fontFamily = fontJockey,
                            color = Color.Gray,
                            text = "Filtriraj",
                            modifier = Modifier.padding(horizontal = 10.dp),
                            fontSize = 15.sp
                        )
                        Icon(
                            tint = Amber,
                            imageVector = FeatherIcons.Tool,
                            contentDescription = "Filter"
                        )
                    }

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
                                                                vwModel.updateIsExpandedProd(false)
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
                                                                vwModel.updateIsExpandedProd(false)
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
                                                                vwModel.updateIsExpandedProd(false)
                                                            })
                                                    }
                                                }

                                            }
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
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                sales?.forEachIndexed { index, oglas ->
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
                                fontWeight = FontWeight.Bold
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