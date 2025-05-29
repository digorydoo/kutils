package ch.digorydoo.kutils.collections

import ch.digorydoo.kutils.utils.Log

/**
 * This class implements a MutableList<GraphicElement> with an array of fixed capacity for improved performance. The
 * main improvement is that we avoid creating and destroying internal structures to reduce GC pressure.
 *
 * NOTE: Native MutableList is probably optimised, so it is unclear if this implementation is actually better for
 * performance! FIXME remove this class?
 */
open class MutableFixedCapacityList<T>(private val capacity: Int): MutableCollection<T> {
    @Suppress("UNCHECKED_CAST")
    // The c'tor Array<T?>(capacity) needs a reified T and cannot be used here, so this hack is necessary:
    private val array: Array<T?> = arrayOfNulls<Any?>(capacity) as Array<T?>

    private var numSlotsUsed = 0
    protected var mutatingFnCalled = 0L // to detect modification while iterating

    override val size: Int
        get() = numSlotsUsed

    override fun clear() {
        mutatingFnCalled++
        (0 ..< numSlotsUsed).forEach { array[it] = null }
        numSlotsUsed = 0
    }

    override fun add(element: T): Boolean {
        mutatingFnCalled++

        if (numSlotsUsed >= capacity) {
            Log.error(TAG, "Dropping $element since the capacity of $capacity is exceeded")
            return false
        }

        array[numSlotsUsed++] = element
        return true
    }

    override fun addAll(elements: Collection<T>): Boolean {
        mutatingFnCalled++
        var modified = false
        elements.forEach { element ->
            if (add(element)) modified = true
        }
        return modified
    }

    override fun remove(element: T): Boolean {
        mutatingFnCalled++
        val index = (0 ..< numSlotsUsed).firstOrNull { array[it] == element } ?: return false

        // Shift elements to fill the gap
        for (i in index until numSlotsUsed - 1) {
            array[i] = array[i + 1]
        }

        array[numSlotsUsed - 1] = null
        numSlotsUsed--
        return true
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        mutatingFnCalled++
        var modified = false
        elements.forEach { element ->
            if (remove(element)) modified = true
        }
        return modified
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        mutatingFnCalled++
        var modified = false
        val iterator = iterator()

        while (iterator.hasNext()) {
            if (!elements.contains(iterator.next())) {
                iterator.remove()
                modified = true
            }
        }

        return modified
    }

    override fun contains(element: T) =
        (0 ..< numSlotsUsed).any { array[it] == element }

    override fun containsAll(elements: Collection<T>) =
        elements.all { contains(it) }

    override fun isEmpty() =
        numSlotsUsed == 0

    override fun iterator(): MutableIterator<T> {
        return object: MutableIterator<T> {
            private var index = 0
            private var lastReturned = -1
            private val prevMutatingFnCalled = mutatingFnCalled

            override fun hasNext(): Boolean {
                if (mutatingFnCalled != prevMutatingFnCalled) throw ConcurrentModificationException()
                return index < numSlotsUsed
            }

            override fun next(): T {
                if (mutatingFnCalled != prevMutatingFnCalled) throw ConcurrentModificationException()
                if (!hasNext()) throw NoSuchElementException()
                lastReturned = index
                return array[index++]!!
            }

            override fun remove() {
                if (lastReturned < 0) throw IllegalStateException()

                // Shift elements to fill the gap
                for (i in lastReturned until numSlotsUsed - 1) {
                    array[i] = array[i + 1]
                }

                array[numSlotsUsed - 1] = null
                numSlotsUsed--
                index = lastReturned
                lastReturned = -1
            }
        }
    }

    companion object {
        private val TAG = Log.Tag("MutableFixedCapacityList")
    }
}
