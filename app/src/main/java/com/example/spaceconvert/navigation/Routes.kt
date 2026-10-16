package com.example.spaceconvert.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object Home : NavKey

@Serializable
data object Measurement : NavKey

@Serializable
data object Currency : NavKey

@Serializable
data object Volume : NavKey