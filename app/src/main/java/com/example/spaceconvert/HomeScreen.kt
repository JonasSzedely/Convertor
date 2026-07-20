package com.example.spaceconvert

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SquareFoot
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ButtonItem(val label: ImageVector, val onClick: () -> Unit)
@Composable
fun HomeScreen(
    onNavigateToMeasurementCalculator: () -> Unit,
    //onNavigateToSettings: () -> Unit
) {
    val buttons = listOf(
        ButtonItem(Icons.Filled.SquareFoot,onNavigateToMeasurementCalculator)
    )

    Column (
        Modifier.padding(30.dp,20.dp,20.dp,0.dp)) {
        Text(
            text = "Hello Traveler!",
            fontSize = 25.sp,
        )
        Text(
            text = "And welcome to SpaceConverter."
        )
        Spacer(Modifier.padding(20.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(buttons) { item ->
                Button(
                    onClick = item.onClick,
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                ) {
                    Icon(
                        imageVector = item.label,
                        contentDescription = "Measurement Converter",
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}