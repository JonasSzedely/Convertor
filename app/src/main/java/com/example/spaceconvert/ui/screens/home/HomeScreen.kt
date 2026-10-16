package com.example.spaceconvert.ui.screens.home

import android.content.res.Configuration
import androidx.annotation.StringRes
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.Restaurant
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.spaceconvert.R



private data class ButtonItem(
    val icon: ImageVector,
    @StringRes val contentDescription: Int,
    val onClick: () -> Unit
)

@Composable
fun HomeScreen(
    navigateToMeasurementCalculator: () -> Unit,
    navigateToCurrencyConverter: () -> Unit,
    navigateToCookingConverter: () -> Unit
) {
    val buttons = listOf(
        ButtonItem(Icons.Filled.SquareFoot, R.string.cd_measurement, navigateToMeasurementCalculator),
        ButtonItem(Icons.Filled.CurrencyExchange, R.string.cd_currency, navigateToCurrencyConverter),
        ButtonItem(Icons.Filled.Restaurant, R.string.cd_cooking, navigateToCookingConverter)
    )

    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    Column(
        Modifier.padding(start = 30.dp, top = 20.dp, end = 20.dp)
    ) {
        if(!isLandscape) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column() {
                    Text(
                        text = stringResource(R.string.home_greeting),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(text = stringResource(R.string.home_intro))
                }
                Spacer(Modifier.width(10.dp))
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null, //decorative item, irrelevant for visible impaired navigation
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(max = 130.dp)
                )
            }

            Spacer(Modifier.height(40.dp))
        }

        LazyVerticalGrid(
            columns = GridCells.FixedSize(100.dp),
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
                        imageVector = item.icon,
                        contentDescription = stringResource(item.contentDescription),
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}