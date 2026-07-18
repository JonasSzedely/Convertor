package com.example.learnjetpackcompose

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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.learnjetpackcompose.ui.theme.LearnJetpackComposeTheme
import com.example.spaceconvert.HomeScreen
import com.example.spaceconvert.MeasurementCalculator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var darkTheme by remember { mutableStateOf(false) }
            val navController = rememberNavController()

            LearnJetpackComposeTheme(darkTheme = darkTheme, dynamicColor = false) {
                Scaffold(
                    topBar = { Header(darkTheme = darkTheme, onToggle = { darkTheme = it }) }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") {
                            HomeScreen(
                                onNavigateToMeasurementCalculator = { navController.navigate("details") }
                            )
                        }
                        composable("details") {
                            MeasurementCalculator(
                                onBack = { navController.popBackStack() }
                            )
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
                }
            }
        }
    }
}



