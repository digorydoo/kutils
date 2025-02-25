package ch.digorydoo.kutils.tty

@Suppress("unused", "MemberVisibilityCanBePrivate")
class OptionsBuilder private constructor() {
    private val options = mutableListOf<CmdLineArg>()

    fun addValueless(name: String, lambda: () -> Unit) {
        addValueless(name, "", lambda)
    }

    fun addValueless(name: String, alias: String, lambda: () -> Unit) {
        options.add(
            object: CmdLineArg(name, alias) {
                override fun check(value: String) {
                    if (value.isNotEmpty()) {
                        throw ValueNotAllowedError(value)
                    } else {
                        lambda()
                    }
                }
            }
        )
    }

    fun addBoolean(name: String, lambda: (value: Boolean) -> Unit) {
        addBoolean(name, "", lambda)
    }

    fun addBoolean(name: String, alias: String, lambda: (value: Boolean) -> Unit) {
        options.add(
            object: CmdLineArg(name, alias) {
                override fun check(value: String) {
                    value.lowercase().let {
                        when {
                            it.isEmpty() -> lambda(true)
                            it == "true" -> lambda(true)
                            it == "yes" -> lambda(true)
                            it == "false" -> lambda(false)
                            it == "no" -> lambda(false)
                            else -> throw IllegalValueError(value)
                        }
                    }
                }
            }
        )
    }

    fun addString(name: String, lambda: (value: String) -> Unit) {
        addString(name, "", lambda)
    }

    fun addString(name: String, alias: String, lambda: (value: String) -> Unit) {
        options.add(
            object: CmdLineArg(name, alias) {
                override fun check(value: String) {
                    when {
                        value.isEmpty() -> throw MissingValueError()
                        else -> lambda(value)
                    }
                }
            }
        )
    }

    fun addInt(name: String, minValue: Int?, maxValue: Int?, lambda: (value: Int) -> Unit) {
        addInt(name, "", minValue, maxValue, lambda)
    }

    fun addInt(
        name: String,
        alias: String,
        minValue: Int?,
        maxValue: Int?,
        lambda: (value: Int) -> Unit,
    ) {
        options.add(
            object: CmdLineArg(name, alias) {
                override fun check(value: String) {
                    if (value.isEmpty()) {
                        throw MissingValueError()
                    }

                    val i: Int

                    try {
                        i = value.toInt()
                    } catch (e: NumberFormatException) {
                        throw IllegalValueError(value)
                    }

                    val tooLow = minValue != null && i < minValue
                    val tooHigh = maxValue != null && i > maxValue

                    return if (tooLow || tooHigh) {
                        throw ValueOutOfRangeError(value = i, min = minValue, max = maxValue)
                    } else {
                        lambda(i)
                    }
                }
            }
        )
    }

    companion object {
        fun build(lambda: OptionsBuilder.() -> Unit): List<CmdLineArg> {
            val builder = OptionsBuilder()
            builder.lambda()
            return builder.options
        }
    }
}
