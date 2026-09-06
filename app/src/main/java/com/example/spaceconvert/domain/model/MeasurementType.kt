package com.example.spaceconvert.domain.model

enum class MeasurementType(val base: Double = 1.0) {
    METER,
    KILOMETERS(1000.0),
    DECIMETER(0.1),
    CENTIMETER(0.01),
    MILLIMETER(0.001),
    INCH(0.0254),
    FEET(0.3048),
    YARD(0.9144),
    MILE(1609.344);

    fun toMeters(value: Double) = value * base
}