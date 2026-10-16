package com.example.spaceconvert.data.repository

import com.example.spaceconvert.domain.model.ConverterType
import com.example.spaceconvert.domain.model.VolumeConverterTypeImpl
import com.example.spaceconvert.domain.repository.CalculatorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class VolumeCalculatorRepositoryImpl : CalculatorRepository {
    private val units = MutableStateFlow<List<ConverterType>>(
        VolumeConverterTypeImpl.entries
    )

    override fun observeUnits(): Flow<List<ConverterType>> = units.asStateFlow()

    override suspend fun saveOrder(units: List<ConverterType>) {
        require(units.toSet() == VolumeConverterTypeImpl.entries.toSet()) {
            "Order must contain exactly all measurement units"
        }
        this.units.value = units
    }
}