package com.example.spaceconvert.data.repository

import com.example.spaceconvert.domain.model.MeasurementType
import com.example.spaceconvert.domain.repository.MeasurementCalculatorRepository

class MeasurementCalculatorRepositoryImpl: MeasurementCalculatorRepository {
    override fun convert(
        fromType: MeasurementType,
        toType: MeasurementType,
        fromValue: Double
    ): Double {
        val fromValueInMeters = fromType.toMeters(fromValue)
        return fromValueInMeters / toType.toMeters(1.0)
    }
}