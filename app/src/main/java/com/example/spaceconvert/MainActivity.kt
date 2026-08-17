package com.example.spaceconvert

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.spaceconvert.ui.theme.SpaceConvertTheme
import com.example.spaceconvert.ui.screens.CurrencyCalculator
import com.example.spaceconvert.ui.screens.Header
import com.example.spaceconvert.ui.screens.home.HomeScreen
import com.example.spaceconvert.ui.screens.measurement.MeasurementCalculator
import kotlinx.serialization.Serializable

@Serializable
data object Home : NavKey

@Serializable
data object Measurement : NavKey

@Serializable
data object Currency : NavKey


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var darkTheme by remember { mutableStateOf(false) }
            val backStack = rememberNavBackStack(Home)
            val currentTitle = when (backStack.lastOrNull()) {
                Home -> "Home"
                Measurement -> "Measurement Calculator"
                Currency -> "Currency Converter"
                else -> "Error"
            }

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
                        onBack = {backStack.removeLastOrNull()},
                        modifier = Modifier.padding(innerPadding),
                        entryProvider = entryProvider {
                            entry<Home> {
                                HomeScreen(
                                    navigateToMeasurementCalculator = {
                                        backStack.add(Measurement)
                                    },
                                    navigateToCurrencyConverter = {
                                        backStack.add(Currency)
                                    }
                                )
                            }
                            entry<Measurement> {
                                MeasurementCalculator()
                            }
                            entry<Currency> {
                                CurrencyCalculator()
                            }
                        }
                    )
                }
            }
        }
    }
}

/*
                   var name by remember {
                       mutableStateOf("")
                   }

                   var names by remember {
                       mutableStateOf(listOf<String>())
                   }

                   Column(
                       modifier = Modifier
                           .fillMaxSize()
                           .padding(20.dp)
                   ) {

                       Row {
                           OutlinedTextField(
                               value = name, onValueChange = { text ->
                                   name = text
                               }, modifier = Modifier.weight(1f)
                           )
                           Spacer(modifier = Modifier.width(16.dp))
                           Button(onClick = {
                               if (name.isNotBlank()) {
                                   names += name
                               }
                           }) {
                               Text(text = "Add")
                           }
                       }
                       LazyColumn(modifier = Modifier.fillMaxSize()) {
                           items(names) { currentName ->
                               Log.d("COMPOSE", "This get rendered $currentName")
                               Text(
                                   text = currentName,
                                   modifier = Modifier
                                       .fillMaxWidth()
                                       .padding(16.dp)
                               )
                           }
                       }
                   }

                    */



