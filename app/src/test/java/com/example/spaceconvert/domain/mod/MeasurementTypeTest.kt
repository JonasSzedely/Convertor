package com.example.spaceconvert.domain.mod

import com.example.spaceconvert.domain.model.MeasurementType
import org.junit.Assert
import org.junit.Test

/**
 * Tests for [com.example.spaceconvert.domain.model.MeasurementType].
 *
 * Contract: `base` is the factor a value of this unit is multiplied by
 * to obtain meters (1 KILOMETERS * 1000.0 = 1000 m).
 */
class MeasurementTypeTest {

    private companion object {
        /** Tolerance for double comparisons — floating point is never exact. */
        const val DELTA = 1e-6
    }

    //Metric units

    @Test
    fun `meter leaves the value unchanged`() {
        Assert.assertEquals(5.0, MeasurementType.METER.toMeters(5.0), DELTA)
    }

    @Test
    fun `kilometers convert to meters`() {
        Assert.assertEquals(2500.0, MeasurementType.KILOMETERS.toMeters(2.5), DELTA)
    }

    @Test
    fun `decimeters convert to meters`() {
        Assert.assertEquals(3.0, MeasurementType.DECIMETER.toMeters(30.0), DELTA)
    }

    @Test
    fun `centimeters convert to meters`() {
        Assert.assertEquals(2.5, MeasurementType.CENTIMETER.toMeters(250.0), DELTA)
    }

    @Test
    fun `millimeters convert to meters`() {
        Assert.assertEquals(1.5, MeasurementType.MILLIMETER.toMeters(1500.0), DELTA)
    }

    //Imperial units

    @Test
    fun `inches convert to meters`() {
        Assert.assertEquals(0.0254, MeasurementType.INCH.toMeters(1.0), DELTA)
    }

    @Test
    fun `twelve inches equal one foot`() {
        Assert.assertEquals(
            MeasurementType.FEET.toMeters(1.0),
            MeasurementType.INCH.toMeters(12.0),
            DELTA
        )
    }

    @Test
    fun `feet convert to meters`() {
        Assert.assertEquals(0.3048, MeasurementType.FEET.toMeters(1.0), DELTA)
    }

    @Test
    fun `three feet equal one yard`() {
        Assert.assertEquals(
            MeasurementType.YARD.toMeters(1.0),
            MeasurementType.FEET.toMeters(3.0),
            DELTA
        )
    }

    @Test
    fun `yards convert to meters`() {
        Assert.assertEquals(0.9144, MeasurementType.YARD.toMeters(1.0), DELTA)
    }

    @Test
    fun `miles convert to meters`() {
        Assert.assertEquals(1609.344, MeasurementType.MILE.toMeters(1.0), DELTA)
    }

    @Test
    fun `1760 yards equal one mile`() {
        Assert.assertEquals(
            MeasurementType.MILE.toMeters(1.0),
            MeasurementType.YARD.toMeters(1760.0),
            1e-3 // larger magnitude, larger tolerance
        )
    }

    //Cross-unit tests

    @Test
    fun `100 centimeters equal one meter`() {
        Assert.assertEquals(
            MeasurementType.METER.toMeters(1.0),
            MeasurementType.CENTIMETER.toMeters(100.0),
            DELTA
        )
    }

    @Test
    fun `1000 millimeters equal one meter`() {
        Assert.assertEquals(
            MeasurementType.METER.toMeters(1.0),
            MeasurementType.MILLIMETER.toMeters(1000.0),
            DELTA
        )
    }

    @Test
    fun `ten decimeters equal one meter`() {
        Assert.assertEquals(
            MeasurementType.METER.toMeters(1.0),
            MeasurementType.DECIMETER.toMeters(10.0),
            DELTA
        )
    }

    //Edge cases

    @Test
    fun `zero stays zero for every unit`() {
        MeasurementType.entries.forEach { type ->
            Assert.assertEquals("failed for $type", 0.0, type.toMeters(0.0), DELTA)
        }
    }

    @Test
    fun `negative values keep their sign`() {
        Assert.assertEquals(-2.5, MeasurementType.CENTIMETER.toMeters(-250.0), DELTA)
        Assert.assertEquals(-1609.344, MeasurementType.MILE.toMeters(-1.0), DELTA)
    }

    @Test
    fun `very large values do not overflow`() {
        val result = MeasurementType.MILE.toMeters(1_000_000.0)
        Assert.assertEquals(1_609_344_000.0, result, 1.0)
        Assert.assertTrue("result is not finite", result.isFinite())
    }

    @Test
    fun `very small values are not rounded to zero`() {
        val result = MeasurementType.MILLIMETER.toMeters(0.001)
        Assert.assertNotEquals(0.0, result, 0.0)
        Assert.assertEquals(1e-6, result, 1e-12)
    }
}