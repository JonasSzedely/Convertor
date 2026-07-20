package com.example.spaceconvert

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(
    darkTheme: Boolean,
    onToggle: (Boolean) -> Unit,
    onNavigateHome: () -> Unit,
    title: String
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineLarge
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onNavigateHome,
                Modifier.padding(start = 8.dp)
            ) {
                Icon(
                    Icons.Filled.Home,
                    contentDescription = "Home",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        actions = {
            Switch(
                modifier = Modifier.padding(end = 20.dp),
                checked = darkTheme,
                onCheckedChange = onToggle,
                thumbContent = {
                    if (darkTheme) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize)
                        )
                    }
                }
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.primary
        )
    )
}
