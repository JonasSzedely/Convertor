package com.example.spaceconvert.ui.screens.converter

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spaceconvert.di.AppContainer
import com.example.spaceconvert.domain.model.VolumeConverterTypeImpl

@Composable
fun VolumeCalculatorScreen() {
    val viewModel: ConverterViewModel = viewModel(
        key = "volume",
        factory = ConverterViewModel.factory(
            repository = AppContainer.volumeCalculatorRepository,
            defaultUnitId = VolumeConverterTypeImpl.LITER.id
        )
    )
    ConverterScreen(viewModel)
}