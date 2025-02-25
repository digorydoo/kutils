package ch.digorydoo.kutils.matrix

import ch.digorydoo.kutils.point.MutablePoint4f
import ch.digorydoo.kutils.point.Point4f
import ch.digorydoo.kutils.string.lpad
import ch.digorydoo.kutils.string.toPrecision
import ch.digorydoo.kutils.utils.forEachElement
import ch.digorydoo.kutils.utils.mapInplace
import ch.digorydoo.kutils.utils.mapInplaceIndexed
import ch.digorydoo.kutils.utils.newFloatBuffer
import ch.digorydoo.kutils.utils.swap
import java.nio.FloatBuffer
import kotlin.math.max

open class Matrix4f protected constructor() {
    constructor(lambda: (index: Int) -> Float): this() {
        _buffer = newFloatBuffer(16)
            .apply { mapInplace { i, _ -> lambda(i) } }
            .asReadOnlyBuffer()
    }

    constructor(readOnlyBuffer: FloatBuffer): this() {
        require(readOnlyBuffer.isReadOnly) { "readOnlyBuffer is not read-only!" }
        require(readOnlyBuffer.limit() == 16) { "readOnlyBuffer needs to have a limit of 16" }
        _buffer = readOnlyBuffer
    }

    constructor(arr: FloatArray): this() {
        require(arr.size == 16) { "Array is required to have a size of 16" }
        _buffer = newFloatBuffer(16)
            .apply {
                position(0)
                put(arr)
            }
            .asReadOnlyBuffer()
    }

    constructor(other: Matrix4f): this() {
        _buffer = newFloatBuffer(16)
            .apply {
                position(0)
                other._buffer.position(0)
                put(other._buffer)
            }
            .asReadOnlyBuffer()
    }

    protected lateinit var _buffer: FloatBuffer // not initialized by the protected constructor()

    open val buffer: FloatBuffer
        get() {
            require(_buffer.isReadOnly)
            return _buffer
        }

    fun isIdentity() =
        isIdentity(_buffer)

    operator fun times(other: Matrix4f): MutableMatrix4f =
        newMultiplied(other)

    operator fun times(pt: Point4f): MutablePoint4f =
        MutablePoint4f().also { multiply(it, _buffer, pt) }

    // Callers should prefer MutablePoint4f::setMultiplied
    fun multiplyTo(src: Point4f, dst: MutablePoint4f) {
        require(src !== dst) { "src and dst must not be the same instance" }
        multiply(dst, _buffer, src)
    }

    fun newMultiplied(other: Matrix4f) = MutableMatrix4f(
        newFloatBuffer(16).also { multiply(it, _buffer, other._buffer) }
    )

    fun toNewFloatBuffer(): FloatBuffer {
        _buffer.position(0)
        return newFloatBuffer(16).apply {
            position(0)
            put(_buffer)
        }
    }

    fun forEachElement(lambda: (index: Int, value: Float) -> Unit) {
        _buffer.forEachElement(lambda)
    }

    final override fun toString(): String {
        val arr = Array(16) { "" }
        val maxLenPerColumn = Array(4) { 0 }

        forEachElement { i, value ->
            val s = value.toPrecision(2)
            val column = i / 4
            maxLenPerColumn[column] = max(s.length, maxLenPerColumn[column])
            arr[i] = s
        }

        arr.mapInplaceIndexed { i, value ->
            val column = i / 4
            lpad(value, maxLenPerColumn[column], ' ')
        }

        return """
            | ${arr[0]} ${arr[4]} ${arr[8]} ${arr[12]} |
            | ${arr[1]} ${arr[5]} ${arr[9]} ${arr[13]} |
            | ${arr[2]} ${arr[6]} ${arr[10]} ${arr[14]} |
            | ${arr[3]} ${arr[7]} ${arr[11]} ${arr[15]} |
            """.trimIndent()
    }

    companion object {
        val identity = Matrix4f(
            newFloatBuffer(16).let {
                setIdentity(it)
                it.asReadOnlyBuffer()
            }
        )

        fun setIdentity(buf: FloatBuffer) {
            require(buf.limit() == 16) { "buffer limit needs to be 16" }
            buf.position(0)
            buf.put(1.0f)
            buf.put(0.0f)
            buf.put(0.0f)
            buf.put(0.0f)
            buf.put(0.0f)
            buf.put(1.0f)
            buf.put(0.0f)
            buf.put(0.0f)
            buf.put(0.0f)
            buf.put(0.0f)
            buf.put(1.0f)
            buf.put(0.0f)
            buf.put(0.0f)
            buf.put(0.0f)
            buf.put(0.0f)
            buf.put(1.0f)
        }

        fun isIdentity(buf: FloatBuffer): Boolean {
            if (buf.limit() != 16) return false
            var ok = true
            buf.position(0)
            ok = ok && buf.get() == 1.0f
            ok = ok && buf.get() == 0.0f
            ok = ok && buf.get() == 0.0f
            ok = ok && buf.get() == 0.0f
            ok = ok && buf.get() == 0.0f
            ok = ok && buf.get() == 1.0f
            ok = ok && buf.get() == 0.0f
            ok = ok && buf.get() == 0.0f
            ok = ok && buf.get() == 0.0f
            ok = ok && buf.get() == 0.0f
            ok = ok && buf.get() == 1.0f
            ok = ok && buf.get() == 0.0f
            ok = ok && buf.get() == 0.0f
            ok = ok && buf.get() == 0.0f
            ok = ok && buf.get() == 0.0f
            return ok && buf.get() == 1.0f
        }

        fun multiply(dst: FloatBuffer, srcA: FloatBuffer, srcB: FloatBuffer) {
            srcA.position(0)
            val a11 = srcA.get()
            val a21 = srcA.get()
            val a31 = srcA.get()
            val a41 = srcA.get()
            val a12 = srcA.get()
            val a22 = srcA.get()
            val a32 = srcA.get()
            val a42 = srcA.get()
            val a13 = srcA.get()
            val a23 = srcA.get()
            val a33 = srcA.get()
            val a43 = srcA.get()
            val a14 = srcA.get()
            val a24 = srcA.get()
            val a34 = srcA.get()
            val a44 = srcA.get()

            srcB.position(0)
            val b11 = srcB.get()
            val b21 = srcB.get()
            val b31 = srcB.get()
            val b41 = srcB.get()
            val b12 = srcB.get()
            val b22 = srcB.get()
            val b32 = srcB.get()
            val b42 = srcB.get()
            val b13 = srcB.get()
            val b23 = srcB.get()
            val b33 = srcB.get()
            val b43 = srcB.get()
            val b14 = srcB.get()
            val b24 = srcB.get()
            val b34 = srcB.get()
            val b44 = srcB.get()

            dst.position(0)
            dst.put((a11 * b11) + (a12 * b21) + (a13 * b31) + (a14 * b41))
            dst.put((a21 * b11) + (a22 * b21) + (a23 * b31) + (a24 * b41))
            dst.put((a31 * b11) + (a32 * b21) + (a33 * b31) + (a34 * b41))
            dst.put((a41 * b11) + (a42 * b21) + (a43 * b31) + (a44 * b41))
            dst.put((a11 * b12) + (a12 * b22) + (a13 * b32) + (a14 * b42))
            dst.put((a21 * b12) + (a22 * b22) + (a23 * b32) + (a24 * b42))
            dst.put((a31 * b12) + (a32 * b22) + (a33 * b32) + (a34 * b42))
            dst.put((a41 * b12) + (a42 * b22) + (a43 * b32) + (a44 * b42))
            dst.put((a11 * b13) + (a12 * b23) + (a13 * b33) + (a14 * b43))
            dst.put((a21 * b13) + (a22 * b23) + (a23 * b33) + (a24 * b43))
            dst.put((a31 * b13) + (a32 * b23) + (a33 * b33) + (a34 * b43))
            dst.put((a41 * b13) + (a42 * b23) + (a43 * b33) + (a44 * b43))
            dst.put((a11 * b14) + (a12 * b24) + (a13 * b34) + (a14 * b44))
            dst.put((a21 * b14) + (a22 * b24) + (a23 * b34) + (a24 * b44))
            dst.put((a31 * b14) + (a32 * b24) + (a33 * b34) + (a34 * b44))
            dst.put((a41 * b14) + (a42 * b24) + (a43 * b34) + (a44 * b44))
        }

        fun multiply(dst: MutablePoint4f, mat: FloatBuffer, pt: Point4f) {
            mat.position(0)
            val m11 = mat.get()
            val m21 = mat.get()
            val m31 = mat.get()
            val m41 = mat.get()
            val m12 = mat.get()
            val m22 = mat.get()
            val m32 = mat.get()
            val m42 = mat.get()
            val m13 = mat.get()
            val m23 = mat.get()
            val m33 = mat.get()
            val m43 = mat.get()
            val m14 = mat.get()
            val m24 = mat.get()
            val m34 = mat.get()
            val m44 = mat.get()

            // Because pt and dst may be the same instance
            val px = pt.x
            val py = pt.y
            val pz = pt.z
            val pw = pt.w

            dst.x = (m11 * px) + (m12 * py) + (m13 * pz) + (m14 * pw)
            dst.y = (m21 * px) + (m22 * py) + (m23 * pz) + (m24 * pw)
            dst.z = (m31 * px) + (m32 * py) + (m33 * pz) + (m34 * pw)
            dst.w = (m41 * px) + (m42 * py) + (m43 * pz) + (m44 * pw)
        }

        fun transpose(buf: FloatBuffer) {
            buf.apply {
                swap(1, 4)
                swap(2, 8)
                swap(3, 12)
                swap(6, 9)
                swap(7, 13)
                swap(11, 14)
            }
        }
    }
}
