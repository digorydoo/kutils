package ch.digorydoo.kutils.point

import ch.digorydoo.kutils.geometry.forEachPoint3f

class Point3fSet {
    private val list = mutableListOf<Point3f>()

    val size get() = list.size
    fun isEmpty() = list.isEmpty()

    fun clear() {
        list.clear()
    }

    fun add(pt: Point3f) {
        val found = list.any { it.hasSameValues(pt) }

        if (!found) {
            list.add(Point3f(pt)) // add a clone, because it might be mutable
        }
    }

    fun addAll(floatArr: FloatArray) {
        floatArr.forEachPoint3f { add(it) }
    }

    fun findIndices(floatArr: FloatArray): MutableList<Int> {
        val result = mutableListOf<Int>()

        floatArr.forEachPoint3f { pt ->
            val idx = list.indexOfFirst { it.hasSameValues(pt) }

            if (idx < 0) {
                throw Exception("findIndices: Not found: $pt")
            }

            result.add(idx)
        }

        return result
    }

    fun forEach(lambda: (pt: Point3f) -> Unit) {
        list.forEach(lambda)
    }

    fun toMutableList() = mutableListOf<Point3f>().apply { addAll(list) }

    fun toFloatArray(): FloatArray {
        val arr = FloatArray(list.size * 3)
        var i = 0

        list.forEach { pt ->
            arr[i++] = pt.x
            arr[i++] = pt.y
            arr[i++] = pt.z
        }

        require(i == list.size * 3)
        return arr
    }

    override fun toString() =
        "{${list.joinToString(", ")}}"
}
