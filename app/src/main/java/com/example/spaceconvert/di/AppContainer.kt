package com.example.spaceconvert.di

import com.example.spaceconvert.data.repository.MeasurementCalculatorRepositoryImpl
import com.example.spaceconvert.data.repository.VolumeCalculatorRepositoryImpl
import com.example.spaceconvert.domain.repository.CalculatorRepository

object AppContainer {
    val measurementCalculatorRepository: CalculatorRepository =
        MeasurementCalculatorRepositoryImpl()

    val volumeCalculatorRepository: CalculatorRepository =
        VolumeCalculatorRepositoryImpl()

    //val currencyCalculatorRepository: CalculatorRepository =
    //    CurrencyCalculatorRepositoryImpl()
}