package ch.digorydoo.kutils.tty

@Suppress("unused", "MemberVisibilityCanBePrivate")
class OptionsBuilder private constructor() {
    private val options = mutableListOf<CmdLineArg>()

    fun addValueless(name: String, alias: String, lambda: () -> Unit) {
        options.add(
            object: CmdLineArg(name, alias) {
                override fun check(value: String) {
                    if (value.isNotEmpty()) {
                        throw ValueNotAllowedException(value)
                    } else {
                        lambda()
                    }
                }
            }
        )
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
                            else -> throw IllegalValueException(value)
                        }
                    }
                }
            }
        )
    }

    fun addString(name: String, alias: String, lambda: (value: String) -> Unit) {
        options.add(
            object: CmdLineArg(name, alias) {
                override fun check(value: String) {
                    when {
                        value.isEmpty() -> throw MissingValueException()
                        else -> lambda(value)
                    }
                }
            }
        )
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
                        throw MissingValueException()
                    }

                    val i: Int

                    try {
                        i = value.toInt()
                    } catch (e: NumberFormatException) {
                        throw IllegalValueException(value)
                    }

                    val tooLow = minValue != null && i < minValue
                    val tooHigh = maxValue != null && i > maxValue

                    if (tooLow || tooHigh) {
                        throw IntValueOutOfRangeException(value = i, min = minValue, max = maxValue)
                    }

                    lambda(i)
                }
            }
        )
    }

    fun addFloat(
        name: String,
        alias: String,
        minValue: Float?,
        maxValue: Float?,
        lambda: (value: Float) -> Unit,
    ) {
        options.add(
            object: CmdLineArg(name, alias) {
                override fun check(value: String) {
                    if (value.isEmpty()) {
                        throw MissingValueException()
                    }

                    val f: Float

                    try {
                        f = value.toFloat()
                    } catch (e: NumberFormatException) {
                        throw IllegalValueException(value)
                    }

                    val tooLow = minValue != null && f < minValue
                    val tooHigh = maxValue != null && f > maxValue

                    if (tooLow || tooHigh) {
                        throw FloatValueOutOfRangeException(value = f, min = minValue, max = maxValue)
                    }

                    lambda(f)
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
