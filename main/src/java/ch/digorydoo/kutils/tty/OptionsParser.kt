package ch.digorydoo.kutils.tty

abstract class CmdLineArg(val name: String, val alias: String) {
    abstract fun check(value: String)
}

@Suppress("unused")
class OptionParseResult(val extraArgs: List<String>)

class InvalidDefsError(msg: String): Exception(msg)

abstract class OptionsParserError(override val message: String): Exception(message)

class UnknownOptionError(key: String): OptionsParserError("Unknown option: $key")
class ValueNotAllowedError(value: String): OptionsParserError("A value is not allowed here: $value")
class IllegalValueError(value: String): OptionsParserError("Illegal value: $value")
class MissingValueError: OptionsParserError("A value is required")
class ExtraArgumentNotAllowedError(arg: String): OptionsParserError("Argument not understood: $arg")

class ValueOutOfRangeError(value: Int, min: Int?, max: Int?):
    OptionsParserError(
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
                        throw ExtraArgumentNotAllowedError(argBeingChecked)
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

                val opt = findOption(key) ?: throw UnknownOptionError(key)
                opt.check(value)
            }

            return OptionParseResult(extraArgs = listOf())
        } catch (e: OptionsParserError) {
            val prefix = when {
                argBeingChecked.isEmpty() -> ""
                e is UnknownOptionError -> "" // message mentions arg already
                e is ExtraArgumentNotAllowedError -> ""  // message mentions arg already
                else -> "Argument $argBeingChecked: "
            }
            throw ShellCommandError("$prefix${e.message}")
        }
    }

    private fun sanitizeDefs() {
        val namesAndAliases = mutableListOf<String>()

        defs.forEach { def ->
            if (def.name.length < 3) {
                throw InvalidDefsError("Name is too short: ${def.name}")
            }
            if (def.alias.isNotEmpty() && def.alias.length > 1) {
                throw InvalidDefsError("Alias is too long: ${def.alias}")
            }
            if (namesAndAliases.contains(def.name)) {
                throw InvalidDefsError("Name already defined: ${def.name}")
            }
            if (namesAndAliases.contains(def.alias)) {
                throw InvalidDefsError("Alias already defined: ${def.alias}")
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
