package ch.digorydoo.kutils.matrix

import ch.digorydoo.kutils.math.lerp
import ch.digorydoo.kutils.point.Point3f
import ch.digorydoo.kutils.utils.mapInplace
import ch.digorydoo.kutils.utils.newFloatBuffer
import ch.digorydoo.kutils.utils.skip
import java.nio.FloatBuffer
import kotlin.math.cos
import kotlin.math.sin

class MutableMatrix4f: Matrix4f {
    constructor(): super() {
        _buffer = newFloatBuffer(16)
        readOnlyBuffer = _buffer.asReadOnlyBuffer()
        setIdentity()
    }

    constructor(lambda: (index: Int) -> Float): super() {
        _buffer = newFloatBuffer(16)
        readOnlyBuffer = _buffer.asReadOnlyBuffer()
        mapInplace { i, _ -> lambda(i) }
    }

    constructor(arr: FloatArray): super() {
        require(arr.size == 16) { "Array is required to have a size of 16" }
        _buffer = newFloatBuffer(16)
            .apply {
                position(0)
                put(arr)
            }
        readOnlyBuffer = _buffer.asReadOnlyBuffer()
    }

    constructor(buf: FloatBuffer): super() {
        require(buf.limit() == 16) { "buf needs to have a limit of 16" }
        require(!buf.isReadOnly) { "You cannot pass a read-only buffer to MutableMatrix4f()" }
        _buffer = buf
        readOnlyBuffer = _buffer.asReadOnlyBuffer()
    }

    constructor(other: Matrix4f): super() {
        _buffer = newFloatBuffer(16)
        readOnlyBuffer = _buffer.asReadOnlyBuffer()
        set(other)
    }

    private val readOnlyBuffer: FloatBuffer

    override val buffer
        get() = readOnlyBuffer

    val writableBuffer: FloatBuffer
        get() = _buffer.also { require(!it.isReadOnly) }

    fun setIdentity() {
        setIdentity(_buffer)
    }

    fun setScaling(pt: Point3f, clear: Boolean = true) =
        setScaling(pt.x, pt.y, pt.z, clear)

    fun setScaling(sx: Double, sy: Double, sz: Double, clear: Boolean = true) =
        setScaling(sx.toFloat(), sy.toFloat(), sz.toFloat(), clear)

    fun setScaling(sx: Float, sy: Float, sz: Float, clear: Boolean = true) {
        _buffer.position(0)
        _buffer.put(sx)

        if (clear) {
            _buffer.put(0.0f)
            _buffer.put(0.0f)
            _buffer.put(0.0f)
            _buffer.put(0.0f)
        } else {
            _buffer.skip(4)
        }

        _buffer.put(sy)

        if (clear) {
            _buffer.put(0.0f)
            _buffer.put(0.0f)
            _buffer.put(0.0f)
            _buffer.put(0.0f)
        } else {
            _buffer.skip(4)
        }

        _buffer.put(sz)

        if (clear) {
            _buffer.put(0.0f)
            _buffer.put(0.0f)
            _buffer.put(0.0f)
            _buffer.put(0.0f)
            _buffer.put(1.0f)
        }
    }

    fun setTranslation(pt: Point3f, clear: Boolean = true) =
        setTranslation(pt.x, pt.y, pt.z, clear)

    fun setTranslation(tx: Double, ty: Double, tz: Double, clear: Boolean = true) =
        setTranslation(tx.toFloat(), ty.toFloat(), tz.toFloat(), clear)

    fun setTranslation(tx: Float, ty: Float, tz: Float, clear: Boolean = true) {
        if (clear) {
            _buffer.position(0)
            _buffer.put(1.0f)
            _buffer.put(0.0f)
            _buffer.put(0.0f)
            _buffer.put(0.0f)
            _buffer.put(0.0f)
            _buffer.put(1.0f)
            _buffer.put(0.0f)
            _buffer.put(0.0f)
            _buffer.put(0.0f)
            _buffer.put(0.0f)
            _buffer.put(1.0f)
            _buffer.put(0.0f)
        } else {
            _buffer.position(12)
        }

        _buffer.put(tx)
        _buffer.put(ty)
        _buffer.put(tz)

        if (clear) {
            _buffer.put(1.0f)
        }
    }

    fun setScaleTranslation(scaling: Point3f, translation: Point3f, clear: Boolean = true) {
        _buffer.position(0)
        _buffer.put(scaling.x)

        if (clear) {
            _buffer.put(0.0f)
            _buffer.put(0.0f)
            _buffer.put(0.0f)
            _buffer.put(0.0f)
        } else {
            _buffer.skip(4)
        }

        _buffer.put(scaling.y)

        if (clear) {
            _buffer.put(0.0f)
            _buffer.put(0.0f)
            _buffer.put(0.0f)
            _buffer.put(0.0f)
        } else {
            _buffer.skip(4)
        }

        _buffer.put(scaling.z)

        if (clear) {
            _buffer.put(0.0f)
        } else {
            _buffer.skip(1)
        }

        _buffer.put(translation.x)
        _buffer.put(translation.y)
        _buffer.put(translation.z)

        if (clear) {
            _buffer.put(1.0f)
        }
    }

    fun setRotationX(phi: Float, clear: Boolean = true) =
        setRotationX(phi.toDouble(), clear)

    fun setRotationX(phi: Double, clear: Boolean = true) {
        val c = cos(phi).toFloat()
        val s = sin(phi).toFloat()

        if (clear) {
            _buffer.position(0)
            _buffer.put(1.0f)
            _buffer.put(0.0f)
            _buffer.put(0.0f)
            _buffer.put(0.0f)
            _buffer.put(0.0f)
        } else {
            _buffer.position(5)
        }

        _buffer.put(c)
        _buffer.put(-s)

        if (clear) {
            _buffer.put(0.0f)
            _buffer.put(0.0f)
        } else {
            _buffer.skip(2)
        }

        _buffer.put(s)
        _buffer.put(c)

        if (clear) {
            _buffer.put(0.0f)
            _buffer.put(0.0f)
            _buffer.put(0.0f)
            _buffer.put(0.0f)
            _buffer.put(1.0f)
        }
    }

    fun setRotationY(phi: Float, clear: Boolean = true) =
        setRotationY(phi.toDouble(), clear)

    fun setRotationY(phi: Double, clear: Boolean = true) {
        val c = cos(phi).toFloat()
        val s = sin(phi).toFloat()
        _buffer.position(0)
        _buffer.put(c)

        if (clear) {
            _buffer.put(0.0f)
        } else {
            _buffer.skip(1)
        }

        _buffer.put(s)

        if (clear) {
            _buffer.put(0.0f)
            _buffer.put(0.0f)
            _buffer.put(1.0f)
            _buffer.put(0.0f)
            _buffer.put(0.0f)
        } else {
            _buffer.skip(5)
        }

        _buffer.put(-s)

        if (clear) {
            _buffer.put(0.0f)
        } else {
            _buffer.skip(1)
        }

        _buffer.put(c)

        if (clear) {
            _buffer.put(0.0f)
            _buffer.put(0.0f)
            _buffer.put(0.0f)
            _buffer.put(0.0f)
            _buffer.put(1.0f)
        }
    }

    fun setRotationZ(phi: Float, clear: Boolean = true) =
        setRotationZ(phi.toDouble(), clear)

    fun setRotationZ(phi: Double, clear: Boolean = true) {
        val c = cos(phi).toFloat()
        val s = sin(phi).toFloat()
        _buffer.position(0)
        _buffer.put(c)
        _buffer.put(s)

        if (clear) {
            _buffer.put(0.0f)
            _buffer.put(0.0f)
        } else {
            _buffer.skip(2)
        }

        _buffer.put(-s)
        _buffer.put(c)

        if (clear) {
            _buffer.put(0.0f)
            _buffer.put(0.0f)
            _buffer.put(0.0f)
            _buffer.put(0.0f)
            _buffer.put(1.0f)
            _buffer.put(0.0f)
            _buffer.put(0.0f)
            _buffer.put(0.0f)
            _buffer.put(0.0f)
            _buffer.put(1.0f)
        }
    }

    fun set(other: Matrix4f) {
        _buffer.position(0)
        other.buffer.position(0)
        _buffer.put(other.buffer)
    }

    fun setLerped(m1: Matrix4f, m2: Matrix4f, rel: Float) {
        setMerged(m1, m2) { v1, v2 ->
            lerp(v1, v2, rel)
        }
    }

    fun setMerged(m1: Matrix4f, m2: Matrix4f, merge: (v1: Float, v2: Float) -> Float) {
        val b1 = m1.buffer
        val b2 = m2.buffer

        for (i in 0 ..< 16) {
            val v1 = b1.get(i) // indexed, because b1 may equal _buffer
            val v2 = b2.get(i) // same for b2
            _buffer.put(i, merge(v1, v2))
        }
    }

    fun transpose() {
        transpose(_buffer)
    }

    operator fun timesAssign(other: Matrix4f) {
        multiply(_buffer, _buffer, other.buffer)
    }

    fun multiply(other: Matrix4f) {
        multiply(_buffer, _buffer, other.buffer)
    }

    fun mapInplace(lambda: (index: Int, value: Float) -> Float) {
        _buffer.mapInplace(lambda)
    }
}
