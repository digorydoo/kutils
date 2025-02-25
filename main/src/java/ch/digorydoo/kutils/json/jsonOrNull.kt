@file:Suppress("unused")

package ch.digorydoo.kutils.json

fun jsonOrNull(key: String, value: String) = when {
    key.isEmpty() -> throw IllegalArgumentException("JSON key must not be empty")
    value.isEmpty() -> null
    else -> jsonify(key, value)
}

fun jsonOrNull(key: String, value: Boolean) = when {
    key.isEmpty() -> throw IllegalArgumentException("JSON key must not be empty")
    !value -> null
    else -> jsonify(key, true)
}

fun jsonOrNull(key: String, value: Collection<String>, singleAsString: Boolean = false) = when {
    key.isEmpty() -> throw IllegalArgumentException("JSON key must not be empty")
    value.isEmpty() -> null
    else -> jsonify(key, value, singleAsString)
}
