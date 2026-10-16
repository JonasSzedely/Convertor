package com.example.spaceconvert.domain.model

private const val UK_GALLON_L = 4.54609
private const val US_GALLON_L = 3.785411784
private const val JP_GO_L = 2401.0 / 13310.0 // ≈ 0.18039 l

enum class VolumeConverterTypeImpl(
    override val id: String,
    override val symbol: String,
    override val base: Double
) : ConverterType {

    // ---------- metric ----------

    // international (SI)
    MILLILITER("milliliter", "ml", 1e3),
    CENTILITER("centiliter", "cl", 1e2),
    DECILITER("deciliter", "dl", 1e1),
    LITER("liter", "l", 1.0),

    // Europe / general metric
    TEASPOON_METRIC("teaspoon_metric", "tsp", 1.0 / 0.005),
    TABLESPOON_METRIC("tablespoon_metric", "tbsp", 1.0 / 0.015),

    // Australia / New Zealand
    TABLESPOON_AU("tablespoon_au", "tbsp", 1.0 / 0.020),
    CUP_AU_NZ("cup_au_nz", "", 1.0 / 0.250),

    // Canada
    CUP_CA("cup_ca", "", 1.0 / 0.250),

    // United Kingdom (today)
    CUP_UK_METRIC("cup_uk_metric", "", 1.0 / 0.250),

    // Japan
    KOSAJI_JP("kosaji_jp", "", 1.0 / 0.005),
    OSAJI_JP("osaji_jp", "", 1.0 / 0.015),
    GO_JP("go_jp", "", 1.0 / JP_GO_L),
    CUP_JP("cup_jp", "", 1.0 / 0.200),
    SHO_JP("sho_jp", "", 1.0 / (JP_GO_L * 10.0)),

    // ---------- imperial ----------

    // United Kingdom (imperial, old)
    TEASPOON_UK("teaspoon_uk", "tsp", 768.0 / UK_GALLON_L),
    TABLESPOON_UK("tablespoon_uk", "tbsp", 256.0 / UK_GALLON_L),
    FLUID_OUNCE_UK("fluid_ounce_uk", "fl oz", 160.0 / UK_GALLON_L),
    GILL_UK("gill_uk", "gi", 32.0 / UK_GALLON_L),
    CUP_UK_IMPERIAL("cup_uk_imperial", "", 16.0 / UK_GALLON_L),
    PINT_UK("pint_uk", "pt", 8.0 / UK_GALLON_L),
    QUART_UK("quart_uk", "qt", 4.0 / UK_GALLON_L),
    GALLON_UK("gallon_uk", "gal", 1.0 / UK_GALLON_L),

    // USA (US customary)
    FLUID_DRAM_US("fluid_dram_us", "fl dr", 1024.0 / US_GALLON_L),
    TEASPOON_US("teaspoon_us", "tsp", 768.0 / US_GALLON_L),
    TABLESPOON_US("tablespoon_us", "tbsp", 256.0 / US_GALLON_L),
    FLUID_OUNCE_US("fluid_ounce_us", "fl oz", 128.0 / US_GALLON_L),
    JIGGER_US("jigger_us", "", (128.0 / 1.5) / US_GALLON_L),
    GILL_US("gill_us", "gi", 32.0 / US_GALLON_L),
    CUP_US("cup_us", "", 16.0 / US_GALLON_L),
    PINT_US("pint_us", "pt", 8.0 / US_GALLON_L),
    QUART_US("quart_us", "qt", 4.0 / US_GALLON_L),
    GALLON_US("gallon_us", "gal", 1.0 / US_GALLON_L)
}