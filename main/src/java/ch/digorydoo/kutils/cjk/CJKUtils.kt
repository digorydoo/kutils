package ch.digorydoo.kutils.cjk

// @returns false for hiragana and katakana!
fun Char.isCJK() =
    this in Unicode.CJK_RADICALS ||
        this in Unicode.CJK_SYMBOLS ||
        this in Unicode.CJK_STROKES ||
        this in Unicode.CJK_ENCLOSED ||
        this in Unicode.CJK_IDEOGRAPHS ||
        this in Unicode.CJK_COMPAT ||
        this in Unicode.CJK_COMPAT_IDEOGRAPHS ||
        this in Unicode.CJK_COMPAT_FORMS ||
        this in Unicode.CJK_IDEOGRAPHS_EXT_A

fun Char.isHiragana() =
    this in Unicode.HIRAGANA.range

fun Char.isKatakana() =
    this in Unicode.KATAKANA.range || this in Unicode.KATAKANA.phoneticsRange

fun Char.isOneStrokeKanji() =
    this in Unicode.ONE_STROKE_KANJI

fun Char.isTwoStrokeKanji() =
    this in Unicode.TWO_STROKE_KANJI

fun Char.isPunctuation() =
    this in Unicode.PUNCTUATION

fun Char.isBracket() =
    this in Unicode.BRACKETS

fun Char.isDigitOfAnyForm() =
    this in Unicode.WESTERN_DIGITS || this in Unicode.WIDE_DIGITS || this in Unicode.CHINESE_DIGITS

fun Char.toHiragana() =
    when (this in Unicode.KATAKANA.range) {
        true -> (this.code - Unicode.KATAKANA.range.first.code + Unicode.HIRAGANA.range.first.code).toChar()
        false -> this
    }

fun Char.toKatakana() =
    when (this in Unicode.HIRAGANA.range) {
        true -> (this.code - Unicode.HIRAGANA.range.first.code + Unicode.KATAKANA.range.first.code).toChar()
        else -> this
    }

fun CharSequence.isCJK() =
    all { it.isCJK() }

fun CharSequence.isHiragana() =
    all { it.isHiragana() }

fun CharSequence.isKatakana() =
    all { it.isKatakana() }

fun CharSequence.isPunctuation() =
    all { it.isPunctuation() }

fun CharSequence.isBracket() =
    all { it.isBracket() }

fun CharSequence.hasCJK() =
    any { it.isCJK() }

fun CharSequence.hasHiragana() =
    any { it.isHiragana() }

fun CharSequence.hasKatakana() =
    any { it.isKatakana() }

fun CharSequence.hasPunctuation() =
    any { it.isPunctuation() }

fun CharSequence.hasBracket() =
    any { it.isBracket() }

fun CharSequence.hasDigitOfAnyForm() =
    any { it.isDigitOfAnyForm() }

fun CharSequence.toHiragana() =
    fold("") { result, c ->
        result + c.toHiragana()
    }

fun CharSequence.toKatakana() =
    fold("") { result, c ->
        result + c.toKatakana()
    }
