package ch.digorydoo.kutils.point

import ch.digorydoo.kutils.geometry.forEachPoint2f

class Point2fSet {
    private val list = mutableListOf<Point2f>()

    val size get() = list.size
    fun isEmpty() = list.isEmpty()

    fun clear() {
        list.clear()
    }

    fun add(pt: Point2f) {
        val found = list.any { it.hasSameValues(pt) }

        if (!found) {
            list.add(Point2f(pt)) // add a clone, because it might be mutable
        }
    }

    fun addAll(floatArr: FloatArray) {
        floatArr.forEachPoint2f { add(it) }
    }

    fun findIndices(floatArr: FloatArray): MutableList<Int> {
        val result = mutableListOf<Int>()

        floatArr.forEachPoint2f { pt ->
            val idx = list.indexOfFirst { it.hasSameValues(pt) }

            if (idx < 0) {
                throw Exception("findIndices: Not found: $pt")
            }

            result.add(idx)
        }

        return result
    }

    fun forEach(lambda: (pt: Point2f) -> Unit) {
        list.forEach(lambda)
    }

    fun toMutableList() = mutableListOf<Point2f>().apply { addAll(list) }

    fun toFloatArray(): FloatArray {
        val arr = FloatArray(list.size * 2)
        var i = 0

        list.forEach { pt ->
            arr[i++] = pt.x
            arr[i++] = pt.y
        }

        require(i == list.size * 2)
        return arr
    }

    override fun toString() =
        "{${list.joinToString(", ")}}"
}
