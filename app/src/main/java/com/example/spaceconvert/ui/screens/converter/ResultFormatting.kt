package com.example.spaceconvert.ui.screens.converter

import java.text.DecimalFormat
import java.util.Locale
import kotlin.math.abs

/**
 * Formatiert ein Ergebnis für die Anzeige.
 * Sehr kleine und sehr grosse Werte wissenschaftlich (z.B. 1.0570e-16),
 * alle anderen mit Tausendertrennzeichen und max. 6 Nachkommastellen.
 */
fun Double.formatResult(): String =
    if (this != 0.0 && (abs(this) < 1e-4 || abs(this) >= 1e9)) {
        String.format(Locale.getDefault(), "%.4e", this)
    } else {
        DecimalFormat("#,##0.####").format(this)
    }