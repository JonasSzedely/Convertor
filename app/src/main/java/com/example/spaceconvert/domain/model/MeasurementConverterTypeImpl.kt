package com.example.spaceconvert.domain.model

enum class MeasurementConverterTypeImpl(
    override val id: String,
    override val symbol: String,
    override val base: Double
) : ConverterType {
    // metric
    NANOMETER("nanometer", "nm", 1e9),
    MICROMETER("micrometer", "µm", 1e6),
    MILLIMETER("millimeter", "mm", 1e3),
    CENTIMETER("centimeter", "cm", 1e2),
    DECIMETER("decimeter", "dm", 1e1),
    METER("meter", "m", 1.0),
    KILOMETER("kilometer", "km", 1e-3),

    // imperial
    INCH("inch", "in", 1.0 / 0.0254),
    HAND("hand", "hh", 1.0 / 0.1016),
    LINK("link", "li", 1.0 / 0.201168),
    FOOT("foot", "ft", 1.0 / 0.3048),
    YARD("yard", "yd", 1.0 / 0.9144),
    MILE("mile", "mi", 1.0 / 1609.344),

    // nautical
    FATHOM("fathom", "ftm", 1.0 / 1.8288),
    CABLE("cable", "cb", 1.0 / 185.2),
    NAUTICAL_MILE("nautical_mile", "NM", 1.0 / 1852.0),

    // astronomical
    LIGHT_YEAR("light_year", "ly", 1.0 / 9_460_730_472_580_800.0)
}