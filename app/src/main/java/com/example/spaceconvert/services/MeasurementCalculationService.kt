package com.example.spaceconvert.services

import androidx.compose.runtime.mutableIntStateOf
import com.example.spaceconvert.viewmodel.MeasurementCalculations
import com.example.spaceconvert.viewmodel.Type

class MeasurementCalculationService {
    val calculations = calculate(Type.METER, 0)
    val meter = mutableIntStateOf(calculations.calculation.meter)
    val decimeter = mutableIntStateOf(calculations.calculation.decimeter)
    val centimeter = mutableIntStateOf(calculations.calculation.centimeter)
    val millimeter = mutableIntStateOf(calculations.calculation.millimeter)

}

class calculate(var type: Type, var value: Int){
    val calculation = MeasurementCalculations(type, value)
}