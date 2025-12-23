package ch.digorydoo.kutils.file

import ch.digorydoo.kutils.point.Point3f
import java.io.DataOutputStream
import java.nio.ByteBuffer
import java.nio.FloatBuffer

/**
 * Example:
 *    fun write(example: Example, file: File) {
 *        file.outputStream()
 *            .let { BufferedOutputStream(it) }
 *            .let { DataOutputStream(it) }
 *            .use { ExampleFileWriter(KDataOutputStream(it)).write(example) }
 *    }
 */
@Suppress("unused")
class KDataOutputStream<M: KDataInputStream.FileMarker>(private val output: DataOutputStream) {
    val bytesWritten get() = output.size()

    fun write(marker: M) {
        writeUInt16(marker.value)
    }

    fun write(marker: M, value: String) {
        writeUInt16(marker.value)
        writeUTF8(value)
    }

    fun writeIfNonEmpty(marker: M, value: String) {
        if (value.isNotEmpty()) {
            writeUInt16(marker.value)
            writeUTF8(value)
        }
    }

    fun writeUInt16(marker: M, value: Int) {
        writeUInt16(marker.value)
        writeUInt16(value)
    }

    fun writeInt16(marker: M, value: Int) {
        writeUInt16(marker.value)
        writeInt16(value)
    }

    fun writeInt32(marker: M, value: Int) {
        writeUInt16(marker.value)
        writeInt32(value)
    }

    fun write(marker: M, value: Float) {
        writeUInt16(marker.value)
        writeFloat(value)
    }

    fun write(marker: M, value: Boolean) {
        writeUInt16(marker.value)
        writeUInt8(if (value) 1 else 0)
    }

    fun writeIntArrayAsUInt16(marker: M, intArray: IntArray) {
        writeUInt16(marker.value)
        writeUInt16(intArray.size)
        intArray.forEach { writeUInt16(it) }
    }

    fun writeIntArrayAsInt32(marker: M, intArray: IntArray) {
        writeUInt16(marker.value)
        writeInt32(intArray.size)
        intArray.forEach { writeInt32(it) }
    }

    fun write(arr: FloatArray) {
        writeInt32(arr.size)
        arr.forEach { output.writeFloat(it) }
    }

    fun write(marker: M, arr: FloatArray) {
        writeUInt16(marker.value)
        writeInt32(arr.size)
        arr.forEach { output.writeFloat(it) }
    }

    fun write(buf: ByteBuffer) {
        val len = buf.limit()
        writeInt32(len)
        buf.position(0)

        for (i in 0 ..< len) {
            output.writeByte(buf.get().toUByte().toInt())
        }
    }

    fun write(buf: FloatBuffer) {
        val len = buf.limit()
        writeInt32(len)
        buf.position(0)

        for (i in 0 ..< len) {
            output.writeFloat(buf.get())
        }
    }

    private fun writeUInt8(i: Int) {
        require(i in 0 .. 255) { "Parameter out of range: $i" }
        output.writeByte(i)
    }

    fun writeInt16(i: Int) {
        require(i in -32768 .. 32767) { "Parameter out of range: $i" }
        output.writeShort(i)
    }

    fun writeUInt16(u: UShort) {
        require(u in 0u .. 65535u) { "Parameter out of range: $u" }
        output.writeShort(u.toInt())
    }

    fun writeUInt16(i: UInt) {
        require(i in 0u .. 65535u) { "Parameter out of range: $i" }
        output.writeShort(i.toInt())
    }

    fun writeUInt16(i: Int) {
        require(i in 0 .. 65535) { "Parameter out of range: $i" }
        output.writeShort(i)
    }

    fun writeInt32(i: Int) {
        output.writeInt(i)
    }

    fun writeFloat(f: Float) {
        output.writeFloat(f)
    }

    fun write(pt: Point3f) {
        output.writeFloat(pt.x)
        output.writeFloat(pt.y)
        output.writeFloat(pt.z)
    }

    private fun writeUTF8(s: String) {
        output.writeUTF(s)
    }
}
