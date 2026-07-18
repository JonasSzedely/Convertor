package com.example.learnjetpackcompose

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(darkTheme: Boolean, onToggle: (Boolean) -> Unit) {

    TopAppBar(
        title = { Text("Header", fontSize = 20.sp) },
        navigationIcon = {
            IconButton(onClick = { /* z.B. zurück */ }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Zurück")
            }
        },
        actions = {
            Switch(
                modifier = Modifier.padding(0.dp, 0.dp, 20.dp, 0.dp),
                checked = darkTheme,
                onCheckedChange = onToggle,
                thumbContent = {
                    if (darkTheme) {
                        // Icon isn't focusable, no need for content description
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                        )
                    }
                },

                )
            /*
            IconButton(onClick = { /* z.B. Menü */ }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Mehr")
            }
             */
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}
