@file:Suppress("unused")

package ch.digorydoo.kutils.utils

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.IntBuffer

fun newByteBuffer(size: Int): ByteBuffer =
    ByteBuffer.allocateDirect(size * Byte.SIZE_BYTES).apply {
        order(ByteOrder.nativeOrder())
    }

fun newFloatBuffer(size: Int): FloatBuffer {
    val s = if (size <= 0) 1 else size // necessary?
    val bb = ByteBuffer.allocateDirect(s * Float.SIZE_BYTES)
    bb.order(ByteOrder.nativeOrder())
    return bb.asFloatBuffer()
}

fun FloatArray.toFloatBuffer(): FloatBuffer {
    val bb = ByteBuffer.allocateDirect(this.size * Float.SIZE_BYTES)
    bb.order(ByteOrder.nativeOrder())
    val fb = bb.asFloatBuffer()
    fb.put(this)
    fb.position(0)
    return fb
}

@Suppress("unused")
fun List<Float>.toFloatBuffer(): FloatBuffer {
    val bb = ByteBuffer.allocateDirect(this.size * Float.SIZE_BYTES)
    bb.order(ByteOrder.nativeOrder())
    val fb = bb.asFloatBuffer()

    for (i in indices) {
        fb.put(i, this[i])
    }

    fb.position(0)
    return fb
}

fun FloatBuffer.toList(): List<Float> {
    // limit <= capacity
    return List(this.limit()) { i ->
        this.get(i)
    }
}

fun FloatBuffer.forEachElement(lambda: (index: Int, value: Float) -> Unit) {
    val lim = limit()
    position(0)

    for (i in 0 ..< lim) {
        lambda(i, get())
    }
}

fun FloatBuffer.mapInplace(lambda: (index: Int, value: Float) -> Float) {
    val lim = limit()

    for (i in 0 ..< lim) {
        val value = get(i)
        put(i, lambda(i, value))
    }
}

fun FloatBuffer.swap(i: Int, j: Int) {
    val v = get(i)
    put(i, get(j))
    put(j, v)
}

@Suppress("unused")
fun ByteBuffer.toList(): List<Byte> {
    // limit <= capacity
    return List(this.limit()) { i ->
        this.get(i)
    }
}

fun IntArray.toIntBuffer(): IntBuffer {
    val bb = ByteBuffer.allocateDirect(this.size * Int.SIZE_BYTES)
    bb.order(ByteOrder.nativeOrder())
    val ib = bb.asIntBuffer()
    ib.put(this)
    ib.position(0)
    return ib
}

fun ByteArray.toByteBuffer(): ByteBuffer {
    val bb = ByteBuffer.allocateDirect(this.size)
    bb.order(ByteOrder.nativeOrder())
    bb.put(this)
    bb.position(0)
    return bb
}

fun FloatBuffer.skip(n: Int) {
    position(position() + n)
}
