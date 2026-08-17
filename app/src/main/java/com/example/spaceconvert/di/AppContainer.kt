package com.example.spaceconvert.di

import com.example.spaceconvert.data.repository.MeasurementCalculatorRepositoryImpl
import com.example.spaceconvert.domain.repository.MeasurementCalculatorRepository

object AppContainer {
    val measurementCalculatorRepository: MeasurementCalculatorRepository =
        MeasurementCalculatorRepositoryImpl()
}