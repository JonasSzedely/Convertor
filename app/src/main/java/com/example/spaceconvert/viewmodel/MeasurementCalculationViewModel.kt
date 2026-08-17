package com.example.spaceconvert.viewmodel

class MeasurementCalculations (var type: Type, var value: Int) {
    val millimeter: Int
        get() = when (type) {
            Type.METER -> value * 1000
            Type.DECIMETER -> value * 100
            Type.CENTIMETER -> value * 10
            Type.MILLIMETER -> value
        }
    val meter: Int = millimeter*1000
    val decimeter: Int = millimeter*100
    val centimeter: Int  = millimeter * 10
}


enum class Type(){
    METER,
    DECIMETER,
    CENTIMETER,
    MILLIMETER
}