package ch.digorydoo.kutils.tty

@Suppress("unused", "MemberVisibilityCanBePrivate")
class Kokuban {
    private var result: String = ""

    override fun toString(): String =
        result.also { result = "" }

    fun print() {
        print(result)
        result = ""
    }

    fun println() {
        println(result)
        result = ""
    }

    fun printToStdErr() {
        System.err.print(result)
        result = ""
    }

    fun printlnToStdErr() {
        System.err.println(result)
        result = ""
    }

    fun rpad(t: Any?, maxLen: Int, padWith: String = " "): Kokuban {
        var s = "$t"
        while (s.length < maxLen && padWith.isNotEmpty()) {
            s += padWith
        }
        result += s
        return this
    }

    private fun ensureTrailingWhitespaceOrEmpty(s: String): String = when {
        s.isEmpty() || s.endsWith("\n") || s.endsWith(" ") -> s
        else -> "$s "
    }

    // If default is null, empty strings are not allowed. To allow empty strings, set default to "".
    fun askString(prompt: String = "", default: String? = null): String {
        val suffix = when (default) {
            null -> ""
            else -> "[$default] "
        }
        print(ensureTrailingWhitespaceOrEmpty(prompt) + suffix)

        while (true) {
            val s = readln().trim()
            if (s.isNotEmpty()) return s
            if (default != null) return default
            println("An empty answer is not acceptable.")
        }
    }

    fun askInt(prompt: String = "", default: Int? = null, min: Int? = null, max: Int? = null): Int {
        val suffix = when (default) {
            null -> ""
            else -> "[$default] "
        }
        print(ensureTrailingWhitespaceOrEmpty(prompt) + suffix)

        while (true) {
            val s = readln().trim()
            if (default != null && s.isEmpty()) return default
            val i = s.toIntOrNull()
            val valid = i != null && i >= (min ?: Int.MIN_VALUE) && i <= (max ?: Int.MAX_VALUE)
            if (valid) return i
            print("Please enter a valid integer number")

            when (min) {
                null -> when (max) {
                    null -> println(".")
                    else -> println(" that is less or equal $max")
                }
                else -> when (max) {
                    null -> println(" that is greater or equal $min")
                    else -> println(" between $min and $max")
                }
            }
        }
    }

    fun askYesOrNo(prompt: String = "", default: Boolean? = null): Boolean {
        val suffix = when (default) {
            true -> "[Y/n] "
            false -> "[y/N] "
            null -> "[y/n] "
        }
        print(ensureTrailingWhitespaceOrEmpty(prompt) + suffix)

        while (true) {
            val s = readln().trim()
            if (default != null && s.isEmpty()) return default

            when (s.lowercase()) {
                "y", "yes" -> return true
                "n", "no" -> return false
                else -> println("Please answer with yes or no!")
            }
        }
    }

    private fun addToResult(s: String): Kokuban {
        result += s
        return this
    }

    fun text(t: Any?) = addToResult("$t")
    fun bold(t: Any?) = addToResult("${Tty.BOLD}$t${Tty.BOLD_OFF}")
    fun faint(t: Any?) = addToResult("${Tty.FAINT}$t${Tty.FAINT_OFF}")
    fun italic(t: Any?) = addToResult("${Tty.ITALIC}$t${Tty.ITALIC_OFF}")
    fun underline(t: Any?) = addToResult("${Tty.UNDERLINE}$t${Tty.UNDERLINE_OFF}")
    fun blink(t: Any?) = addToResult("${Tty.BLINK}$t${Tty.BLINK_OFF}")
    fun reverse(t: Any?) = addToResult("${Tty.REVERSE}$t${Tty.REVERSE_OFF}")
    fun conceal(t: Any?) = addToResult("${Tty.CONCEAL}$t${Tty.CONCEAL_OFF}")
    fun white(t: Any?) = addToResult("${Tty.WHITE}$t${Tty.FG_DEFAULT}")
    fun red(t: Any?) = addToResult("${Tty.RED}$t${Tty.FG_DEFAULT}")
    fun green(t: Any?) = addToResult("${Tty.GREEN}$t${Tty.FG_DEFAULT}")
    fun yellow(t: Any?) = addToResult("${Tty.YELLOW}$t${Tty.FG_DEFAULT}")
    fun blue(t: Any?) = addToResult("${Tty.BLUE}$t${Tty.FG_DEFAULT}")
    fun magenta(t: Any?) = addToResult("${Tty.MAGENTA}$t${Tty.FG_DEFAULT}")
    fun cyan(t: Any?) = addToResult("${Tty.CYAN}$t${Tty.FG_DEFAULT}")
    fun brightRed(t: Any?) = addToResult("${Tty.BRIGHT_RED}$t${Tty.FG_DEFAULT}")
    fun brightGreen(t: Any?) = addToResult("${Tty.BRIGHT_GREEN}$t${Tty.FG_DEFAULT}")
    fun brightYellow(t: Any?) = addToResult("${Tty.BRIGHT_YELLOW}$t${Tty.FG_DEFAULT}")
    fun brightBlue(t: Any?) = addToResult("${Tty.BRIGHT_BLUE}$t${Tty.FG_DEFAULT}")
    fun brightMagenta(t: Any?) = addToResult("${Tty.BRIGHT_MAGENTA}$t${Tty.FG_DEFAULT}")
    fun brightCyan(t: Any?) = addToResult("${Tty.BRIGHT_CYAN}$t${Tty.FG_DEFAULT}")
    fun brightWhite(t: Any?) = addToResult("${Tty.BRIGHT_WHITE}$t${Tty.FG_DEFAULT}")
    fun ltGrey(t: Any?) = addToResult("${Tty.LIGHT_GREY}$t${Tty.FG_DEFAULT}")
    fun dkGrey(t: Any?) = addToResult("${Tty.DARK_GREY}$t${Tty.FG_DEFAULT}")
    fun orange(t: Any?) = addToResult("${Tty.ORANGE}$t${Tty.FG_DEFAULT}")
    fun pink(t: Any?) = addToResult("${Tty.PINK}$t${Tty.FG_DEFAULT}")
    fun bgWhite(t: Any?) = addToResult("${Tty.BG_WHITE}$t${Tty.BG_DEFAULT}")
    fun bgRed(t: Any?) = addToResult("${Tty.BG_RED}$t${Tty.BG_DEFAULT}")
    fun bgGreen(t: Any?) = addToResult("${Tty.BG_GREEN}$t${Tty.BG_DEFAULT}")
    fun bgYellow(t: Any?) = addToResult("${Tty.BG_YELLOW}$t${Tty.BG_DEFAULT}")
    fun bgBlue(t: Any?) = addToResult("${Tty.BG_BLUE}$t${Tty.BG_DEFAULT}")
    fun bgMagenta(t: Any?) = addToResult("${Tty.BG_MAGENTA}$t${Tty.BG_DEFAULT}")
    fun bgCyan(t: Any?) = addToResult("${Tty.BG_CYAN}$t${Tty.BG_DEFAULT}")
    fun bgBrightRed(t: Any?) = addToResult("${Tty.BG_BRIGHT_RED}$t${Tty.BG_DEFAULT}")
    fun bgBrightGreen(t: Any?) = addToResult("${Tty.BG_BRIGHT_GREEN}$t${Tty.BG_DEFAULT}")
    fun bgBrightYellow(t: Any?) = addToResult("${Tty.BG_BRIGHT_YELLOW}$t${Tty.BG_DEFAULT}")
    fun bgBrightBlue(t: Any?) = addToResult("${Tty.BG_BRIGHT_BLUE}$t${Tty.BG_DEFAULT}")
    fun bgBrightMagenta(t: Any?) = addToResult("${Tty.BG_BRIGHT_MAGENTA}$t${Tty.BG_DEFAULT}")
    fun bgBrightCyan(t: Any?) = addToResult("${Tty.BG_BRIGHT_CYAN}$t${Tty.BG_DEFAULT}")
    fun bgBrightWhite(t: Any?) = addToResult("${Tty.BG_BRIGHT_WHITE}$t${Tty.BG_DEFAULT}")
    fun bgLtGrey(t: Any?) = addToResult("${Tty.BG_LIGHT_GREY}$t${Tty.BG_DEFAULT}")
    fun bgDkGrey(t: Any?) = addToResult("${Tty.BG_DARK_GREY}$t${Tty.BG_DEFAULT}")
    fun bgOrange(t: Any?) = addToResult("${Tty.BG_ORANGE}$t${Tty.BG_DEFAULT}")
    fun bgPink(t: Any?) = addToResult("${Tty.BG_PINK}$t${Tty.BG_DEFAULT}")

    val plain get() = addToResult(Tty.PLAIN)
    val bold get() = addToResult(Tty.BOLD)
    val faint get() = addToResult(Tty.FAINT)
    val italic get() = addToResult(Tty.ITALIC)
    val underline get() = addToResult(Tty.UNDERLINE)
    val blink get() = addToResult(Tty.BLINK)
    val reverse get() = addToResult(Tty.REVERSE)
    val conceal get() = addToResult(Tty.CONCEAL)

    val boldOff get() = addToResult(Tty.BOLD_OFF)
    val faintOff get() = addToResult(Tty.FAINT_OFF)
    val italicOff get() = addToResult(Tty.ITALIC_OFF)
    val underlineOff get() = addToResult(Tty.UNDERLINE_OFF)
    val blinkOff get() = addToResult(Tty.BLINK_OFF)
    val reverseOff get() = addToResult(Tty.REVERSE_OFF)
    val concealOff get() = addToResult(Tty.CONCEAL_OFF)

    val white get() = addToResult(Tty.WHITE)
    val red get() = addToResult(Tty.RED)
    val green get() = addToResult(Tty.GREEN)
    val yellow get() = addToResult(Tty.YELLOW)
    val blue get() = addToResult(Tty.BLUE)
    val magenta get() = addToResult(Tty.MAGENTA)
    val cyan get() = addToResult(Tty.CYAN)

    val brightRed get() = addToResult(Tty.BRIGHT_RED)
    val brightGreen get() = addToResult(Tty.BRIGHT_GREEN)
    val brightYellow get() = addToResult(Tty.BRIGHT_YELLOW)
    val brightBlue get() = addToResult(Tty.BRIGHT_BLUE)
    val brightMagenta get() = addToResult(Tty.BRIGHT_MAGENTA)
    val brightCyan get() = addToResult(Tty.BRIGHT_CYAN)
    val brightWhite get() = addToResult(Tty.BRIGHT_WHITE)

    val ltGrey get() = addToResult(Tty.LIGHT_GREY)
    val dkGrey get() = addToResult(Tty.DARK_GREY)
    val orange get() = addToResult(Tty.ORANGE)
    val pink get() = addToResult(Tty.PINK)

    val fgDefault get() = addToResult(Tty.FG_DEFAULT)
    val bgDefault get() = addToResult(Tty.BG_DEFAULT)

    val bgWhite get() = addToResult(Tty.BG_WHITE)
    val bgRed get() = addToResult(Tty.BG_RED)
    val bgGreen get() = addToResult(Tty.BG_GREEN)
    val bgYellow get() = addToResult(Tty.BG_YELLOW)
    val bgBlue get() = addToResult(Tty.BG_BLUE)
    val bgMagenta get() = addToResult(Tty.BG_MAGENTA)
    val bgCyan get() = addToResult(Tty.BG_CYAN)

    val bgBrightRed get() = addToResult(Tty.BG_BRIGHT_RED)
    val bgBrightGreen get() = addToResult(Tty.BG_BRIGHT_GREEN)
    val bgBrightYellow get() = addToResult(Tty.BG_BRIGHT_YELLOW)
    val bgBrightBlue get() = addToResult(Tty.BG_BRIGHT_BLUE)
    val bgBrightMagenta get() = addToResult(Tty.BG_BRIGHT_MAGENTA)
    val bgBrightCyan get() = addToResult(Tty.BG_BRIGHT_CYAN)
    val bgBrightWhite get() = addToResult(Tty.BG_BRIGHT_WHITE)

    val bgLtGrey get() = addToResult(Tty.BG_LIGHT_GREY)
    val bgDkGrey get() = addToResult(Tty.BG_DARK_GREY)
    val bgOrange get() = addToResult(Tty.BG_ORANGE)
    val bgPink get() = addToResult(Tty.BG_PINK)

    companion object {
        fun fromException(e: Exception): Kokuban {
            return Kokuban()
                .bold.red.text("***EXCEPTION: ")
                .plain.red.text(e.message)
                .plain
        }
    }
}
