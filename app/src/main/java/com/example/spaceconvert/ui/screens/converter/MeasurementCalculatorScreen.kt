package com.example.spaceconvert.ui.screens.converter

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spaceconvert.di.AppContainer
import com.example.spaceconvert.domain.model.MeasurementConverterTypeImpl

@Composable
fun MeasurementCalculatorScreen() {
    val viewModel: ConverterViewModel = viewModel(
        key = "measurement",
        factory = ConverterViewModel.factory(
            repository = AppContainer.measurementCalculatorRepository,
            defaultUnitId = MeasurementConverterTypeImpl.METER.id
        )
    )
    ConverterScreen(viewModel)
}