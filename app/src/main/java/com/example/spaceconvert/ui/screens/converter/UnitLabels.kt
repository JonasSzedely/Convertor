package com.example.spaceconvert.ui.screens.converter

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.spaceconvert.R
import com.example.spaceconvert.domain.model.ConverterType
import com.example.spaceconvert.domain.model.MeasurementConverterTypeImpl
import com.example.spaceconvert.domain.model.MeasurementConverterTypeImpl.*
import com.example.spaceconvert.domain.model.VolumeConverterTypeImpl
import com.example.spaceconvert.domain.model.VolumeConverterTypeImpl.*

@Composable
fun ConverterType.displayName(): String = when (this) {
    is MeasurementConverterTypeImpl -> stringResource(labelRes())
    is VolumeConverterTypeImpl -> stringResource(labelRes())
    else -> id   // Fallback
}

@StringRes
private fun MeasurementConverterTypeImpl.labelRes(): Int = when (this) {
    NANOMETER -> R.string.unit_nanometer
    MICROMETER -> R.string.unit_micrometer
    MILLIMETER -> R.string.unit_millimeter
    CENTIMETER -> R.string.unit_centimeter
    DECIMETER -> R.string.unit_decimeter
    METER -> R.string.unit_meter
    KILOMETER -> R.string.unit_kilometer
    INCH -> R.string.unit_inch
    HAND -> R.string.unit_hand
    LINK -> R.string.unit_link
    FOOT -> R.string.unit_foot
    YARD -> R.string.unit_yard
    MILE -> R.string.unit_mile
    FATHOM -> R.string.unit_fathom
    CABLE -> R.string.unit_cable
    NAUTICAL_MILE -> R.string.unit_nautical_mile
    LIGHT_YEAR -> R.string.unit_light_year
}

@StringRes
private fun VolumeConverterTypeImpl.labelRes(): Int = when (this) {
    // metric
    MILLILITER -> R.string.unit_milliliter
    CENTILITER -> R.string.unit_centiliter
    DECILITER -> R.string.unit_deciliter
    LITER -> R.string.unit_liter
    TEASPOON_METRIC -> R.string.unit_teaspoon_metric
    TABLESPOON_METRIC -> R.string.unit_tablespoon_metric
    TABLESPOON_AU -> R.string.unit_tablespoon_au
    CUP_AU_NZ -> R.string.unit_cup_au_nz
    CUP_CA -> R.string.unit_cup_ca
    CUP_UK_METRIC -> R.string.unit_cup_uk_metric

    // Japan
    KOSAJI_JP -> R.string.unit_kosaji_jp
    OSAJI_JP -> R.string.unit_osaji_jp
    GO_JP -> R.string.unit_go_jp
    CUP_JP -> R.string.unit_cup_jp
    SHO_JP -> R.string.unit_sho_jp

    // UK imperial
    TEASPOON_UK -> R.string.unit_teaspoon_uk
    TABLESPOON_UK -> R.string.unit_tablespoon_uk
    FLUID_OUNCE_UK -> R.string.unit_fluid_ounce_uk
    GILL_UK -> R.string.unit_gill_uk
    CUP_UK_IMPERIAL -> R.string.unit_cup_uk_imperial
    PINT_UK -> R.string.unit_pint_uk
    QUART_UK -> R.string.unit_quart_uk
    GALLON_UK -> R.string.unit_gallon_uk

    // US customary
    FLUID_DRAM_US -> R.string.unit_fluid_dram_us
    TEASPOON_US -> R.string.unit_teaspoon_us
    TABLESPOON_US -> R.string.unit_tablespoon_us
    FLUID_OUNCE_US -> R.string.unit_fluid_ounce_us
    JIGGER_US -> R.string.unit_jigger_us
    GILL_US -> R.string.unit_gill_us
    CUP_US -> R.string.unit_cup_us
    PINT_US -> R.string.unit_pint_us
    QUART_US -> R.string.unit_quart_us
    GALLON_US -> R.string.unit_gallon_us
}