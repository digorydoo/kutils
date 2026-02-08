package ch.digorydoo.kutils.vector

import ch.digorydoo.kutils.utils.forEachVector2f

class Vector2fSet {
    private val list = mutableListOf<Vector2f>()

    val size get() = list.size
    fun isEmpty() = list.isEmpty()

    fun clear() {
        list.clear()
    }

    fun add(vec: Vector2f) {
        val found = list.any { it.hasSameValues(vec) }

        if (!found) {
            list.add(Vector2f(vec)) // add a clone, because it might be mutable
        }
    }

    fun addAll(floatArr: FloatArray) {
        floatArr.forEachVector2f { add(it) }
    }

    fun findIndices(floatArr: FloatArray): MutableList<Int> {
        val result = mutableListOf<Int>()

        floatArr.forEachVector2f { vec ->
            val idx = list.indexOfFirst { it.hasSameValues(vec) }

            if (idx < 0) {
                throw Exception("findIndices: Not found: $vec")
            }

            result.add(idx)
        }

        return result
    }

    fun forEach(lambda: (Vector2f) -> Unit) {
        list.forEach(lambda)
    }

    fun toMutableList() = mutableListOf<Vector2f>().apply { addAll(list) }

    fun toFloatArray(): FloatArray {
        val arr = FloatArray(list.size * 2)
        var i = 0

        list.forEach { vec ->
            arr[i++] = vec.x
            arr[i++] = vec.y
        }

        require(i == list.size * 2)
        return arr
    }

    override fun toString() =
        "{${list.joinToString(", ")}}"
}
