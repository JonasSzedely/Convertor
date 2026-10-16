package com.example.spaceconvert.domain.repository

import com.example.spaceconvert.domain.model.ConverterType
import kotlinx.coroutines.flow.Flow

interface CalculatorRepository {
    /**
     * Observable list of all available units in the user-defined order
     */
    fun observeUnits(): Flow<List<ConverterType>>

    /**
     * Persists a new order of the units.
     */
    suspend fun saveOrder(units: List<ConverterType>)
}
