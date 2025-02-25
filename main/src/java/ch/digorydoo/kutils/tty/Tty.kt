package ch.digorydoo.kutils.tty

@Suppress("unused")
object Tty {
    const val PLAIN = "\u001b[0m"

    const val BOLD = "\u001b[1m"
    const val BOLD_OFF = "\u001b[22m" // ambiguous, see FAINT_OFF

    const val FAINT = "\u001b[2m"
    const val FAINT_OFF = "\u001b[22m" // ambiguous, see BOLD_OFF

    const val ITALIC = "\u001b[3m"
    const val ITALIC_OFF = "\u001b[23m"

    const val UNDERLINE = "\u001b[4m"

    // const val DBL_UNDERLINE = "\u001b[21m" // not widely supported
    const val UNDERLINE_OFF = "\u001b[24m"

    const val BLINK = "\u001b[5m"

    // const val RAPID_BLINK = "\u001b[6m" // not widely supported
    const val BLINK_OFF = "\u001b[25m"

    const val REVERSE = "\u001b[7m"
    const val REVERSE_OFF = "\u001b[27m"

    const val CONCEAL = "\u001b[8m"
    const val CONCEAL_OFF = "\u001b[28m"

    // val STRIKEOUT = "\u001b[9m" // not widely supported
    // val STRIKEOUT_OFF = "\u001b[29m" // not widely supported

    // val FRAKTUR = "\u001b[20m" // not widely supported
    // val FRAKTUR_OFF = "\u001b[23m" // not widely supported

    // val PROPORTIONAL = "\u001b[26m" // not widely supported
    // val PROPORTIONAL_OFF = "\u001b[50m" // not widely supported

    // val SUPERSCRIPT = "\u001b[73m" // not widely supported
    // val SUBSCRIPT = "\u001b[74m" // not widely supported

    // val FRAMED = "\u001b[51m" // not widely supported
    // val FRAMED_OFF = "\u001b[54m" // // not widely supported; ambiguous, see ENCIRCLED_OFF

    // val ENCIRCLED = "\u001b[52m" // not widely supported
    // val ENCIRCLED_OFF = "\u001b[54m" // // not widely supported; ambiguous, see FRAMED_OFF

    // val OVERLINED = "\u001b[53m" // not widely supported
    // val OVERLINED_OFF = "\u001b[55m" // not widely supported

    // val FONT0 = "\u001b[10m" // not widely supported
    // val FONT1 = "\u001b[11m" // not widely supported
    // val FONT2 = "\u001b[12m" // not widely supported
    // val FONT3 = "\u001b[13m" // not widely supported
    // val FONT4 = "\u001b[14m" // not widely supported
    // val FONT5 = "\u001b[15m" // not widely supported
    // val FONT6 = "\u001b[16m" // not widely supported
    // val FONT7 = "\u001b[17m" // not widely supported
    // val FONT8 = "\u001b[18m" // not widely supported
    // val FONT9 = "\u001b[19m" // not widely supported

    const val WHITE = "\u001b[30m" // will be black on terminals with a bright background
    const val RED = "\u001b[31m"
    const val GREEN = "\u001b[32m"
    const val YELLOW = "\u001b[33m"
    const val BLUE = "\u001b[34m"
    const val MAGENTA = "\u001b[35m"
    const val CYAN = "\u001b[36m"
    const val LIGHT_GREY = "\u001b[37m" // will be white on terminals with a bright background

    const val DARK_GREY = "\u001b[90m" // i.e. the fg brightened up on terminals with a bright background
    const val BRIGHT_RED = "\u001b[91m" // aka. ORANGE
    const val BRIGHT_GREEN = "\u001b[92m"
    const val BRIGHT_YELLOW = "\u001b[93m"
    const val BRIGHT_BLUE = "\u001b[94m"
    const val BRIGHT_MAGENTA = "\u001b[95m" // aka. PINK
    const val BRIGHT_CYAN = "\u001b[96m"
    const val BRIGHT_WHITE = "\u001b[97m"

    const val ORANGE = BRIGHT_RED
    const val PINK = BRIGHT_MAGENTA

    const val FG_DEFAULT = "\u001b[39m"
    const val BG_DEFAULT = "\u001b[49m"

    const val BG_WHITE = "\u001b[40m" // will be black on terminals with a bright background
    const val BG_RED = "\u001b[41m"
    const val BG_GREEN = "\u001b[42m"
    const val BG_YELLOW = "\u001b[43m"
    const val BG_BLUE = "\u001b[44m"
    const val BG_MAGENTA = "\u001b[45m"
    const val BG_CYAN = "\u001b[46m"
    const val BG_LIGHT_GREY = "\u001b[47m" // will be white on terminals with a bright background

    const val BG_DARK_GREY = "\u001b[100m" // i.e. the fg brightened up on terminals with a bright background
    const val BG_BRIGHT_RED = "\u001b[101m" // aka. BG_ORANGE
    const val BG_BRIGHT_GREEN = "\u001b[102m"
    const val BG_BRIGHT_YELLOW = "\u001b[103m"
    const val BG_BRIGHT_BLUE = "\u001b[104m"
    const val BG_BRIGHT_MAGENTA = "\u001b[105m" // aka. BG_PINK
    const val BG_BRIGHT_CYAN = "\u001b[106m"
    const val BG_BRIGHT_WHITE = "\u001b[107m"

    const val BG_ORANGE = BG_BRIGHT_RED
    const val BG_PINK = BG_BRIGHT_MAGENTA
}
