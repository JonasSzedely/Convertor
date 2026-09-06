package com.example.spaceconvert.data.repository

import com.example.spaceconvert.domain.model.MeasurementType
import org.junit.Assert.assertEquals
import org.junit.Test

class MeasurementCalculatorRepositoryImplTest {

    private companion object {
        const val DELTA = 1e-6
    }

    private val repository = MeasurementCalculatorRepositoryImpl()

    @Test
    fun `converting to the same unit returns the input unchanged`() {
        val result = repository.convert(
            MeasurementType.METER,
            MeasurementType.METER,
            42.0
        )
        assertEquals(42.0, result, DELTA)
    }

    @Test
    fun `converts from a larger to a smaller unit`() {
        val result = repository.convert(
            MeasurementType.METER,
            MeasurementType.CENTIMETER,
            1.0
        )
        assertEquals(100.0, result, DELTA)
    }

    @Test
    fun `converts from a smaller to a larger unit`() {
        val result = repository.convert(
            MeasurementType.MILLIMETER,
            MeasurementType.METER,
            2500.0
        )
        assertEquals(2.5, result, DELTA)
    }

    @Test
    fun `converts across measurement systems`() {
        val result = repository.convert(
            MeasurementType.INCH,
            MeasurementType.CENTIMETER,
            1.0
        )
        assertEquals(2.54, result, DELTA)
    }

    @Test
    fun `zero converts to zero`() {
        val result = repository.convert(
            MeasurementType.MILE,
            MeasurementType.MILLIMETER,
            0.0
        )
        assertEquals(0.0, result, DELTA)
    }
}