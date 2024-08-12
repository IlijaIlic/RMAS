package com.example.rmasprojekat.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.rmasprojekat.ui.theme.Amber

@Composable
fun MainScreen(
    onNavigateToLanding: () -> Unit,
    onNavigateToAddPlace: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToUserList: () -> Unit,
    onNavigateToViewSale: () -> Unit,
) {
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
        }
    }
}