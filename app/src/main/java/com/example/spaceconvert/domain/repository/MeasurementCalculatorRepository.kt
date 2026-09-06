package com.example.spaceconvert.domain.repository

import com.example.spaceconvert.domain.model.MeasurementType

interface MeasurementCalculatorRepository {
    fun convert(fromType: MeasurementType, toType: MeasurementType, fromValue: Double): Double
}