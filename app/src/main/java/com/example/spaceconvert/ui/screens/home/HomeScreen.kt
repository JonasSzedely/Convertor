package com.example.spaceconvert.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.SquareFoot
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.spaceconvert.R


data class ButtonItem(val label: ImageVector, val onClick: () -> Unit)

@Composable
fun HomeScreen(
    navigateToMeasurementCalculator: () -> Unit,
    navigateToCurrencyConverter: () -> Unit
) {
    val buttons = listOf(
        ButtonItem(Icons.Filled.SquareFoot, navigateToMeasurementCalculator),
        ButtonItem(Icons.Filled.CurrencyExchange, navigateToCurrencyConverter)
    )

    Column(
        Modifier.padding(30.dp, 20.dp, 20.dp, 0.dp)
    ) {
        Row(
            modifier = Modifier
                .height(150.dp)
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column{
                Text(
                    text = "Hello Traveler!",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = "I am Convertron,\nhow may I help you?"
                )

            }
            Spacer(Modifier.padding(5.dp))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
        }

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