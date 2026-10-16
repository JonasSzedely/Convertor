package com.example.spaceconvert.domain.model

enum class MeasurementConverterTypeImpl(override val id: String, override val base: Double): ConverterType  {
    // metric
    NANOMETER("nanometer", 1e9),
    MICROMETER("micrometer", 1e6),
    MILLIMETER("millimeter", 1e3),
    CENTIMETER("centimeter", 1e2),
    DECIMETER("decimeter", 1e1),
    METER("meter", 1.0),
    KILOMETER("kilometer", 1e-3),

    // imperial
    INCH("inch", 1.0 / 0.0254),
    HAND("hand", 1.0 / 0.1016),
    LINK("link", 1.0 / 0.201168),
    FOOT("foot", 1.0 / 0.3048),
    YARD("yard", 1.0 / 0.9144),
    MILE("mile", 1.0 / 1609.344),

    // nautical
    FATHOM("fathom", 1.0 / 1.8288),
    CABLE("cable", 1.0 / 185.2),
    NAUTICAL_MILE("nautical_mile", 1.0 / 1852.0),

    // astronomical
    LIGHT_YEAR("light_year", 1.0 / 9_460_730_472_580_800.0)
}