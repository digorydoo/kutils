package ch.digorydoo.kutils.tty

abstract class CmdLineArg(val name: String, val alias: String) {
    abstract fun check(value: String)
}

@Suppress("unused")
class OptionParseResult(val extraArgs: List<String>)

class InvalidDefsException(msg: String): Exception(msg)

abstract class OptionsParserException(override val message: String): Exception(message)

class UnknownOptionException(key: String): OptionsParserException("Unknown option: $key")
class ValueNotAllowedException(value: String): OptionsParserException("A value is not allowed here: $value")
class IllegalValueException(value: String): OptionsParserException("Illegal value: $value")
class MissingValueException: OptionsParserException("A value is required")
class ExtraArgumentNotAllowedException(arg: String): OptionsParserException("Argument not understood: $arg")

class IntValueOutOfRangeException(value: Int, min: Int?, max: Int?):
    OptionsParserException(
        "Value $value out of range! Expected a value " + when {
            min != null && max != null -> "between $min and $max"
            min != null && max == null -> ">= $min"
            min == null && max != null -> "<= $max"
            else -> "<bad range>"
        }
    )

class FloatValueOutOfRangeException(value: Float, min: Float?, max: Float?):
    OptionsParserException(
        "Value $value out of range! Expected a value " + when {
            min != null && max != null -> "between $min and $max"
            min != null && max == null -> ">= $min"
            min == null && max != null -> "<= $max"
            else -> "<bad range>"
        }
    )

@Suppress("unused", "MemberVisibilityCanBePrivate")
class OptionsParser(private val defs: Iterable<CmdLineArg>) {
    fun parse(args: Array<String>, startIdx: Int = 0, allowExtraArgs: Boolean = false): OptionParseResult {
        sanitizeDefs()
        var argBeingChecked = ""

        try {
            for (i in startIdx ..< args.size) {
                argBeingChecked = args[i]

                if (!argBeingChecked.startsWith("-")) {
                    if (allowExtraArgs) {
                        return OptionParseResult(extraArgs = args.slice(i ..< args.size))
                    } else {
                        throw ExtraArgumentNotAllowedException(argBeingChecked)
                    }
                }

                val isSingle = !argBeingChecked.startsWith("--")
                argBeingChecked = argBeingChecked.substring(if (isSingle) 1 else 2)

                val key: String
                val value: String
                val eqAt = argBeingChecked.indexOf("=")

                if (eqAt < 0) {
                    key = argBeingChecked
                    value = ""
                } else {
                    key = argBeingChecked.substring(0, eqAt)
                    value = argBeingChecked.substring(eqAt + 1)
                }

                val opt = findOption(key) ?: throw UnknownOptionException(key)
                opt.check(value)
            }

            return OptionParseResult(extraArgs = listOf())
        } catch (e: OptionsParserException) {
            val prefix = when {
                argBeingChecked.isEmpty() -> ""
                e is UnknownOptionException -> "" // message mentions arg already
                e is ExtraArgumentNotAllowedException -> ""  // message mentions arg already
                else -> "Argument $argBeingChecked: "
            }
            throw ShellCommandError("$prefix${e.message}")
        }
    }

    private fun sanitizeDefs() {
        val namesAndAliases = mutableListOf<String>()

        defs.forEach { def ->
            if (def.name.length < 3) {
                throw InvalidDefsException("Name is too short: ${def.name}")
            }
            if (def.alias.isNotEmpty() && def.alias.length > 1) {
                throw InvalidDefsException("Alias is too long: ${def.alias}")
            }
            if (namesAndAliases.contains(def.name)) {
                throw InvalidDefsException("Name already defined: ${def.name}")
            }
            if (namesAndAliases.contains(def.alias)) {
                throw InvalidDefsException("Alias already defined: ${def.alias}")
            }
            namesAndAliases.add(def.name)
            namesAndAliases.add(def.alias)
        }
    }

    private fun findOption(key: String): CmdLineArg? {
        if (key.isEmpty()) {
            return null
        }
        for (d in defs) {
            if (d.alias == key || d.name == key) {
                return d
            }
        }
        return null
    }
}
