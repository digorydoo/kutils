@file:Suppress("unused")

package ch.digorydoo.kutils.math

/**
 * Linear curve with a peak at thresh.
 * @param x: must be in [0..1]
 * @param thresh: must be in [0..1]
 * @param peak: value to result in when x is at thresh
 * @return A value in [0..1] if peak is also in [0..1]
 */
fun linpeak(x: Double, thresh: Double, peak: Double) =
    if (x <= thresh) {
        peak * (x / thresh)
    } else {
        peak + (x - thresh) / (1.0 - thresh) * (1.0 - peak)
    }

/**
 * Linear curve with a peak at thresh.
 * @param x: must be in [0..1]
 * @param thresh: must be in [0..1]
 * @param peak: value to result in when x is at thresh
 * @return A value in [0..1] if peak is also in [0..1]
 */
fun linpeak(x: Float, thresh: Float, peak: Float) =
    linpeak(x.toDouble(), thresh.toDouble(), peak.toDouble()).toFloat()
