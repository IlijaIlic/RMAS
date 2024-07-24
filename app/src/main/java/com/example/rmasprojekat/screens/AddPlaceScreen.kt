package com.example.rmasprojekat.screens

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rmasprojekat.ui.theme.Amber
import com.example.rmasprojekat.ui.theme.AmberLight
import com.example.rmasprojekat.ui.theme.fontJockey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlace(onNavigateToMain: () -> Unit) {

    var opis by remember { mutableStateOf(TextFieldValue("")) }
    var selText by remember { mutableStateOf(TextFieldValue("Izaberi radnju")) }
    var isExpanded by remember { mutableStateOf(false) }

    Surface(
        color = Amber,
        modifier = Modifier
            .fillMaxSize()
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
                fontSize = 40.sp
            )
            ExposedDropdownMenuBox(
                expanded = isExpanded,
                onExpandedChange = { isExpanded = !isExpanded }) {
                TextField(
                    textStyle = TextStyle.Default.copy(fontFamily = fontJockey),
                    value = selText,
                    onValueChange = {},
                    modifier = Modifier
                        .menuAnchor()
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
                    onDismissRequest = { isExpanded = false }) {
                    DropdownMenuItem(
                        text = { Text(text = "Idea", fontFamily = fontJockey) },
                        onClick = {
                            selText = TextFieldValue("Idea")
                            isExpanded = false
                        })
                    DropdownMenuItem(
                        text = { Text(text = "Roda", fontFamily = fontJockey) },
                        onClick = {
                            selText = TextFieldValue("Roda")
                            isExpanded = false
                        })
                    DropdownMenuItem(
                        text = { Text(text = "Lidl", fontFamily = fontJockey) },
                        onClick = {
                            selText = TextFieldValue("Lidl")
                            isExpanded = false
                        })
                }
            }
            TextField(
                value = opis,
                onValueChange = { newText -> opis = newText },
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
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxSize()
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
                FloatingActionButton(
                    containerColor = Color.White,
                    contentColor = Amber,
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.Bottom),
                    onClick = { onNavigateToMain() },
                ) {
                    Icon(imageVector = Icons.Filled.Done, contentDescription = "Cancel")
                }
            }
        }
    }
}