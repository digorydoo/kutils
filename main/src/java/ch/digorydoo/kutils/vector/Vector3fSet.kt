package ch.digorydoo.kutils.vector

import ch.digorydoo.kutils.utils.forEachVector3f

class Vector3fSet {
    private val list = mutableListOf<Vector3f>()

    val size get() = list.size
    fun isEmpty() = list.isEmpty()

    fun clear() {
        list.clear()
    }

    fun add(vec: Vector3f) {
        val found = list.any { it.hasSameValues(vec) }

        if (!found) {
            list.add(Vector3f(vec)) // add a clone, because it might be mutable
        }
    }

    fun addAll(floatArr: FloatArray) {
        floatArr.forEachVector3f { add(it) }
    }

    fun findIndices(floatArr: FloatArray): MutableList<Int> {
        val result = mutableListOf<Int>()

        floatArr.forEachVector3f { vec ->
            val idx = list.indexOfFirst { it.hasSameValues(vec) }

            if (idx < 0) {
                throw Exception("findIndices: Not found: $vec")
            }

            result.add(idx)
        }

        return result
    }

    fun forEach(lambda: (Vector3f) -> Unit) {
        list.forEach(lambda)
    }

    fun toMutableList() = mutableListOf<Vector3f>().apply { addAll(list) }

    fun toFloatArray(): FloatArray {
        val arr = FloatArray(list.size * 3)
        var i = 0

        list.forEach { vec ->
            arr[i++] = vec.x
            arr[i++] = vec.y
            arr[i++] = vec.z
        }

        require(i == list.size * 3)
        return arr
    }

    override fun toString() =
        "{${list.joinToString(", ")}}"
}
