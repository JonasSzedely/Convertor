package com.example.spaceconvert

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.spaceconvert.navigation.Cooking
import com.example.spaceconvert.navigation.Currency
import com.example.spaceconvert.navigation.Home
import com.example.spaceconvert.navigation.Measurement
import com.example.spaceconvert.ui.components.Header
import com.example.spaceconvert.ui.screens.currency.CurrencyCalculatorScreen
import com.example.spaceconvert.ui.screens.home.HomeScreen
import com.example.spaceconvert.ui.screens.measurement.MeasurementCalculatorScreen
import com.example.spaceconvert.ui.theme.SpaceConvertTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val systemDark = isSystemInDarkTheme()
            var darkTheme by rememberSaveable { mutableStateOf(systemDark) }
            val backStack = rememberNavBackStack(Home)

            val currentTitle = stringResource(
                when (backStack.lastOrNull()) {
                    Measurement -> R.string.title_measurement
                    Currency -> R.string.title_currency
                    Cooking -> R.string.title_cooking
                    else -> R.string.title_home
                }
            )

            SpaceConvertTheme(darkTheme = darkTheme, dynamicColor = false) {
                Scaffold(
                    topBar = {
                        Header(
                            title = currentTitle,
                            darkTheme = darkTheme,
                            onToggle = { darkTheme = it },
                            onNavigateHome = {
                                backStack.clear()
                                backStack.add(Home)
                            }
                        )
                    }
                ) { innerPadding ->
                    NavDisplay(
                        backStack = backStack,
                        onBack = { backStack.removeLastOrNull() },
                        modifier = Modifier.padding(innerPadding),
                        entryProvider = entryProvider {
                            entry<Home> {
                                HomeScreen(
                                    navigateToMeasurementCalculator = { backStack.add(Measurement) },
                                    navigateToCurrencyConverter = { backStack.add(Currency) },
                                    navigateToCookingConverter = {backStack.add(Cooking)}
                                )
                            }
                            entry<Measurement> { MeasurementCalculatorScreen() }
                            entry<Currency> { CurrencyCalculatorScreen() }
                        }
                    )
                }
            }
        }
    }
}