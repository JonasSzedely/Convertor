package com.example.spaceconvert.domain.model

data class Calculation(
    val baseType: ConverterType,
    val outputType: ConverterType,
    val inputValue: Double
) {
    val outputValue: Double
        get() = inputValue / baseType.base * outputType.base
}
