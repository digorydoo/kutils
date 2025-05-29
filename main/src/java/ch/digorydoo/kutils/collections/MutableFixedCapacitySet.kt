package ch.digorydoo.kutils.collections

/**
 * NOTE: Native MutableSet is probably optimised, so it is unclear if this implementation is actually better for
 * performance! FIXME remove this class?
 */
open class MutableFixedCapacitySet<T>(capacity: Int): MutableFixedCapacityList<T>(capacity) {
    override fun add(element: T): Boolean {
        return if (contains(element)) false else super.add(element)
    }
}
