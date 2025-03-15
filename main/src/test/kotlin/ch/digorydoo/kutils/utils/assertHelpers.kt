package ch.digorydoo.kutils.utils

import kotlin.test.assertTrue

// Copy this into other modules as needed, because test helpers cannot be easily shared.

fun <T: Comparable<T>> assertLessThan(value: T, expectedMax: T, msg: String? = null) =
    assertTrue(
        value < expectedMax,
        (msg?.let { "$msg: " } ?: "") + "Expected $value to be less than $expectedMax"
    )

fun <T: Comparable<T>> assertLessOrEqual(value: T, expectedMax: T, msg: String? = null) =
    assertTrue(
        value <= expectedMax,
        (msg?.let { "$msg: " } ?: "") + "Expected $value to be less or equal $expectedMax"
    )

fun <T: Comparable<T>> assertGreaterThan(value: T, expectedMin: T, msg: String? = null) =
    assertTrue(
        value > expectedMin,
        (msg?.let { "$msg: " } ?: "") + "Expected $value to be greater than $expectedMin"
    )

fun <T: Comparable<T>> assertGreaterOrEqual(value: T, expectedMin: T, msg: String? = null) =
    assertTrue(
        value >= expectedMin,
        (msg?.let { "$msg: " } ?: "") + "Expected $value to be greater or equal $expectedMin"
    )
