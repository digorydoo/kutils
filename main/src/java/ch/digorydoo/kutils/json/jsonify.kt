@file:Suppress("unused")

package ch.digorydoo.kutils.json

fun jsonify(key: String, value: String) =
    if (key.isEmpty()) throw IllegalArgumentException("JSON key must not be empty")
    else "${key.toJSON()}:${value.toJSON()}"

fun jsonify(key: String, value: Boolean) =
    if (key.isEmpty()) throw IllegalArgumentException("JSON key must not be empty")
    else "${key.toJSON()}:${value.toJSON()}"

fun jsonify(key: String, value: Int) =
    if (key.isEmpty()) throw IllegalArgumentException("JSON key must not be empty")
    else "${key.toJSON()}:${value.toJSON()}"

fun jsonify(key: String, value: Float) =
    if (key.isEmpty()) throw IllegalArgumentException("JSON key must not be empty")
    else "${key.toJSON()}:${value.toJSON()}"

fun jsonify(key: String, value: Double) =
    if (key.isEmpty()) throw IllegalArgumentException("JSON key must not be empty")
    else "${key.toJSON()}:${value.toJSON()}"

fun jsonify(key: String, values: Array<String>, singleAsString: Boolean = false) = when {
    key.isEmpty() -> throw IllegalArgumentException("JSON key must not be empty")
    singleAsString && values.size == 1 -> "${key.toJSON()}:${values.first().toJSON()}"
    else -> "${key.toJSON()}:${values.toJSON()}"
}

fun jsonify(key: String, values: Collection<String>, singleAsString: Boolean = false) = when {
    key.isEmpty() -> throw IllegalArgumentException("JSON key must not be empty")
    singleAsString && values.size == 1 -> "${key.toJSON()}:${values.first().toJSON()}"
    else -> "${key.toJSON()}:${values.toJSON()}"
}
