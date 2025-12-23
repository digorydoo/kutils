package ch.digorydoo.kutils.utils

import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty

/**
 * Use this class as a Kotlin delegate when one class should fully delegate one member to an instance of another class.
 * Using this is shorter and more readable than explicitly writing the getter and setter for the member.
 * Note that RefClass must be the class containing the delegate, not the class whose property is delegated!
 * Also note that this only works if the property being delegated is a var. Use a simple getter instead if it's a val!
 * See unit test for an example how to use it.
 */
class PropertyDelegate<RefClass, MemberType>(private val ref: KMutableProperty0<MemberType>) {
    operator fun getValue(thisRef: RefClass, property: KProperty<*>): MemberType = ref.get()
    operator fun setValue(thisRef: RefClass, property: KProperty<*>, value: MemberType) = ref.set(value)
}
