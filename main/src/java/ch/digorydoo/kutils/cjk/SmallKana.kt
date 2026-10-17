package ch.digorydoo.kutils.cjk

fun Char.isSmallKana() =
    Unicode.HIRAGANA.small.contains(this) || Unicode.KATAKANA.small.contains(this)

fun CharSequence.isSmallKana() =
    isNotEmpty() && all { it.isSmallKana() }

fun CharSequence.hasSmallKana() =
    any { it.isSmallKana() }

fun CharSequence.toSmallKana() =
    fold("") { result, c ->
        result + c.toSmallKana()
    }

fun CharSequence.toNormalSizedKana() =
    fold("") { result, c ->
        result + c.toNormalSizedKana()
    }

fun Char.toSmallKana() = when (this) {
    'あ' -> 'ぁ'
    'い' -> 'ぃ'
    'う' -> 'ぅ'
    'え' -> 'ぇ'
    'お' -> 'ぉ'
    'か' -> 'ゕ'
    'け' -> 'ゖ'
    'つ' -> 'っ'
    'や' -> 'ゃ'
    'ゆ' -> 'ゅ'
    'よ' -> 'ょ'
    'わ' -> 'ゎ'

    'ア' -> 'ァ'
    'イ' -> 'ィ'
    'ウ' -> 'ゥ'
    'エ' -> 'ェ'
    'オ' -> 'ォ'
    'カ' -> 'ヵ'
    'ケ' -> 'ヶ'
    'ツ' -> 'ッ'
    'ヤ' -> 'ャ'
    'ユ' -> 'ュ'
    'ヨ' -> 'ョ'
    'ワ' -> 'ヮ'

    else -> this
}

fun Char.toNormalSizedKana() = when (this) {
    'ぁ' -> 'あ'
    'ぃ' -> 'い'
    'ぅ' -> 'う'
    'ぇ' -> 'え'
    'ぉ' -> 'お'
    'ゕ' -> 'か'
    'ゖ' -> 'け'
    'っ' -> 'つ'
    'ゃ' -> 'や'
    'ゅ' -> 'ゆ'
    'ょ' -> 'よ'
    'ゎ' -> 'わ'

    'ァ' -> 'ア'
    'ィ' -> 'イ'
    'ゥ' -> 'ウ'
    'ェ' -> 'エ'
    'ォ' -> 'オ'
    'ヵ' -> 'カ'
    'ヶ' -> 'ケ'
    'ッ' -> 'ツ'
    'ャ' -> 'ヤ'
    'ュ' -> 'ユ'
    'ョ' -> 'ヨ'
    'ヮ' -> 'ワ'

    else -> this
}
