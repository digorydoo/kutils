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

    fun text(t: Any?): Kokuban {
        result += "$t"; return this
    }

    fun bold(t: Any?): Kokuban {
        result += "${Tty.BOLD}${t}${Tty.BOLD_OFF}"; return this
    }

    fun faint(t: Any?): Kokuban {
        result += "${Tty.FAINT}${t}${Tty.FAINT_OFF}"; return this
    }

    fun italic(t: Any?): Kokuban {
        result += "${Tty.ITALIC}${t}${Tty.ITALIC_OFF}"; return this
    }

    fun underline(t: Any?): Kokuban {
        result += "${Tty.UNDERLINE}${t}${Tty.UNDERLINE_OFF}"; return this
    }

    fun blink(t: Any?): Kokuban {
        result += "${Tty.BLINK}${t}${Tty.BLINK_OFF}"; return this
    }

    fun reverse(t: Any?): Kokuban {
        result += "${Tty.REVERSE}${t}${Tty.REVERSE_OFF}"; return this
    }

    fun conceal(t: Any?): Kokuban {
        result += "${Tty.CONCEAL}${t}${Tty.CONCEAL_OFF}"; return this
    }

    fun white(t: Any?): Kokuban {
        result += "${Tty.WHITE}${t}${Tty.FG_DEFAULT}"; return this
    }

    fun red(t: Any?): Kokuban {
        result += "${Tty.RED}${t}${Tty.FG_DEFAULT}"; return this
    }

    fun green(t: Any?): Kokuban {
        result += "${Tty.GREEN}${t}${Tty.FG_DEFAULT}"; return this
    }

    fun yellow(t: Any?): Kokuban {
        result += "${Tty.YELLOW}${t}${Tty.FG_DEFAULT}"; return this
    }

    fun blue(t: Any?): Kokuban {
        result += "${Tty.BLUE}${t}${Tty.FG_DEFAULT}"; return this
    }

    fun magenta(t: Any?): Kokuban {
        result += "${Tty.MAGENTA}${t}${Tty.FG_DEFAULT}"; return this
    }

    fun cyan(t: Any?): Kokuban {
        result += "${Tty.CYAN}${t}${Tty.FG_DEFAULT}"; return this
    }

    fun brightRed(t: Any?): Kokuban {
        result += "${Tty.BRIGHT_RED}${t}${Tty.FG_DEFAULT}"; return this
    }

    fun brightGreen(t: Any?): Kokuban {
        result += "${Tty.BRIGHT_GREEN}${t}${Tty.FG_DEFAULT}"; return this
    }

    fun brightYellow(t: Any?): Kokuban {
        result += "${Tty.BRIGHT_YELLOW}${t}${Tty.FG_DEFAULT}"; return this
    }

    fun brightBlue(t: Any?): Kokuban {
        result += "${Tty.BRIGHT_BLUE}${t}${Tty.FG_DEFAULT}"; return this
    }

    fun brightMagenta(t: Any?): Kokuban {
        result += "${Tty.BRIGHT_MAGENTA}${t}${Tty.FG_DEFAULT}"; return this
    }

    fun brightCyan(t: Any?): Kokuban {
        result += "${Tty.BRIGHT_CYAN}${t}${Tty.FG_DEFAULT}"; return this
    }

    fun brightWhite(t: Any?): Kokuban {
        result += "${Tty.BRIGHT_WHITE}${t}${Tty.FG_DEFAULT}"; return this
    }

    fun ltGrey(t: Any?): Kokuban {
        result += "${Tty.LIGHT_GREY}${t}${Tty.FG_DEFAULT}"; return this
    }

    fun dkGrey(t: Any?): Kokuban {
        result += "${Tty.DARK_GREY}${t}${Tty.FG_DEFAULT}"; return this
    }

    fun orange(t: Any?): Kokuban {
        result += "${Tty.ORANGE}${t}${Tty.FG_DEFAULT}"; return this
    }

    fun pink(t: Any?): Kokuban {
        result += "${Tty.PINK}${t}${Tty.FG_DEFAULT}"; return this
    }

    fun bgWhite(t: Any?): Kokuban {
        result += "${Tty.BG_WHITE}${t}${Tty.BG_DEFAULT}"; return this
    }

    fun bgRed(t: Any?): Kokuban {
        result += "${Tty.BG_RED}${t}${Tty.BG_DEFAULT}"; return this
    }

    fun bgGreen(t: Any?): Kokuban {
        result += "${Tty.BG_GREEN}${t}${Tty.BG_DEFAULT}"; return this
    }

    fun bgYellow(t: Any?): Kokuban {
        result += "${Tty.BG_YELLOW}${t}${Tty.BG_DEFAULT}"; return this
    }

    fun bgBlue(t: Any?): Kokuban {
        result += "${Tty.BG_BLUE}${t}${Tty.BG_DEFAULT}"; return this
    }

    fun bgMagenta(t: Any?): Kokuban {
        result += "${Tty.BG_MAGENTA}${t}${Tty.BG_DEFAULT}"; return this
    }

    fun bgCyan(t: Any?): Kokuban {
        result += "${Tty.BG_CYAN}${t}${Tty.BG_DEFAULT}"; return this
    }

    fun bgBrightRed(t: Any?): Kokuban {
        result += "${Tty.BG_BRIGHT_RED}${t}${Tty.BG_DEFAULT}"; return this
    }

    fun bgBrightGreen(t: Any?): Kokuban {
        result += "${Tty.BG_BRIGHT_GREEN}${t}${Tty.BG_DEFAULT}"; return this
    }

    fun bgBrightYellow(t: Any?): Kokuban {
        result += "${Tty.BG_BRIGHT_YELLOW}${t}${Tty.BG_DEFAULT}"; return this
    }

    fun bgBrightBlue(t: Any?): Kokuban {
        result += "${Tty.BG_BRIGHT_BLUE}${t}${Tty.BG_DEFAULT}"; return this
    }

    fun bgBrightMagenta(t: Any?): Kokuban {
        result += "${Tty.BG_BRIGHT_MAGENTA}${t}${Tty.BG_DEFAULT}"; return this
    }

    fun bgBrightCyan(t: Any?): Kokuban {
        result += "${Tty.BG_BRIGHT_CYAN}${t}${Tty.BG_DEFAULT}"; return this
    }

    fun bgBrightWhite(t: Any?): Kokuban {
        result += "${Tty.BG_BRIGHT_WHITE}${t}${Tty.BG_DEFAULT}"; return this
    }

    fun bgLtGrey(t: Any?): Kokuban {
        result += "${Tty.BG_LIGHT_GREY}${t}${Tty.BG_DEFAULT}"; return this
    }

    fun bgDkGrey(t: Any?): Kokuban {
        result += "${Tty.BG_DARK_GREY}${t}${Tty.BG_DEFAULT}"; return this
    }

    fun bgOrange(t: Any?): Kokuban {
        result += "${Tty.BG_ORANGE}${t}${Tty.BG_DEFAULT}"; return this
    }

    fun bgPink(t: Any?): Kokuban {
        result += "${Tty.BG_PINK}${t}${Tty.BG_DEFAULT}"; return this
    }

    val plain: Kokuban
        get() {
            result += Tty.PLAIN; return this
        }
    val bold: Kokuban
        get() {
            result += Tty.BOLD; return this
        }
    val faint: Kokuban
        get() {
            result += Tty.FAINT; return this
        }
    val italic: Kokuban
        get() {
            result += Tty.ITALIC; return this
        }
    val underline: Kokuban
        get() {
            result += Tty.UNDERLINE; return this
        }
    val blink: Kokuban
        get() {
            result += Tty.BLINK; return this
        }
    val reverse: Kokuban
        get() {
            result += Tty.REVERSE; return this
        }
    val conceal: Kokuban
        get() {
            result += Tty.CONCEAL; return this
        }

    val boldOff: Kokuban
        get() {
            result += Tty.BOLD_OFF; return this
        }
    val faintOff: Kokuban
        get() {
            result += Tty.FAINT_OFF; return this
        }
    val italicOff: Kokuban
        get() {
            result += Tty.ITALIC_OFF; return this
        }
    val underlineOff: Kokuban
        get() {
            result += Tty.UNDERLINE_OFF; return this
        }
    val blinkOff: Kokuban
        get() {
            result += Tty.BLINK_OFF; return this
        }
    val reverseOff: Kokuban
        get() {
            result += Tty.REVERSE_OFF; return this
        }
    val concealOff: Kokuban
        get() {
            result += Tty.CONCEAL_OFF; return this
        }

    val white: Kokuban
        get() {
            result += Tty.WHITE; return this
        }
    val red: Kokuban
        get() {
            result += Tty.RED; return this
        }
    val green: Kokuban
        get() {
            result += Tty.GREEN; return this
        }
    val yellow: Kokuban
        get() {
            result += Tty.YELLOW; return this
        }
    val blue: Kokuban
        get() {
            result += Tty.BLUE; return this
        }
    val magenta: Kokuban
        get() {
            result += Tty.MAGENTA; return this
        }
    val cyan: Kokuban
        get() {
            result += Tty.CYAN; return this
        }

    val brightRed: Kokuban
        get() {
            result += Tty.BRIGHT_RED; return this
        }
    val brightGreen: Kokuban
        get() {
            result += Tty.BRIGHT_GREEN; return this
        }
    val brightYellow: Kokuban
        get() {
            result += Tty.BRIGHT_YELLOW; return this
        }
    val brightBlue: Kokuban
        get() {
            result += Tty.BRIGHT_BLUE; return this
        }
    val brightMagenta: Kokuban
        get() {
            result += Tty.BRIGHT_MAGENTA; return this
        }
    val brightCyan: Kokuban
        get() {
            result += Tty.BRIGHT_CYAN; return this
        }
    val brightWhite: Kokuban
        get() {
            result += Tty.BRIGHT_WHITE; return this
        }

    val ltGrey: Kokuban
        get() {
            result += Tty.LIGHT_GREY; return this
        }
    val dkGrey: Kokuban
        get() {
            result += Tty.DARK_GREY; return this
        }
    val orange: Kokuban
        get() {
            result += Tty.ORANGE; return this
        }
    val pink: Kokuban
        get() {
            result += Tty.PINK; return this
        }

    val fgDefault: Kokuban
        get() {
            result += Tty.FG_DEFAULT; return this
        }
    val bgDefault: Kokuban
        get() {
            result += Tty.BG_DEFAULT; return this
        }

    val bgWhite: Kokuban
        get() {
            result += Tty.BG_WHITE; return this
        }
    val bgRed: Kokuban
        get() {
            result += Tty.BG_RED; return this
        }
    val bgGreen: Kokuban
        get() {
            result += Tty.BG_GREEN; return this
        }
    val bgYellow: Kokuban
        get() {
            result += Tty.BG_YELLOW; return this
        }
    val bgBlue: Kokuban
        get() {
            result += Tty.BG_BLUE; return this
        }
    val bgMagenta: Kokuban
        get() {
            result += Tty.BG_MAGENTA; return this
        }
    val bgCyan: Kokuban
        get() {
            result += Tty.BG_CYAN; return this
        }

    val bgBrightRed: Kokuban
        get() {
            result += Tty.BG_BRIGHT_RED; return this
        }
    val bgBrightGreen: Kokuban
        get() {
            result += Tty.BG_BRIGHT_GREEN; return this
        }
    val bgBrightYellow: Kokuban
        get() {
            result += Tty.BG_BRIGHT_YELLOW; return this
        }
    val bgBrightBlue: Kokuban
        get() {
            result += Tty.BG_BRIGHT_BLUE; return this
        }
    val bgBrightMagenta: Kokuban
        get() {
            result += Tty.BG_BRIGHT_MAGENTA; return this
        }
    val bgBrightCyan: Kokuban
        get() {
            result += Tty.BG_BRIGHT_CYAN; return this
        }
    val bgBrightWhite: Kokuban
        get() {
            result += Tty.BG_BRIGHT_WHITE; return this
        }

    val bgLtGrey: Kokuban
        get() {
            result += Tty.BG_LIGHT_GREY; return this
        }
    val bgDkGrey: Kokuban
        get() {
            result += Tty.BG_DARK_GREY; return this
        }
    val bgOrange: Kokuban
        get() {
            result += Tty.BG_ORANGE; return this
        }
    val bgPink: Kokuban
        get() {
            result += Tty.BG_PINK; return this
        }

    companion object {
        fun fromException(e: Exception): Kokuban {
            return Kokuban()
                .bold.red.text("***EXCEPTION: ")
                .plain.red.text(e.message)
                .plain
        }
    }
}
