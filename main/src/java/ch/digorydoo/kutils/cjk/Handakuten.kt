package ch.digorydoo.kutils.cjk

fun Char.isHandakuten() =
    Unicode.HIRAGANA.handakuten.contains(this) || Unicode.KATAKANA.handakuten.contains(this)

@Suppress("unused")
fun CharSequence.hasHandakuten() =
    any { it.isHandakuten() }

fun Char.toggleHandakuten() = when (this) {
    'は' -> 'ぱ'
    'ぱ' -> 'は'
    'ば' -> 'ぱ'
    'ひ' -> 'ぴ'
    'ぴ' -> 'ひ'
    'び' -> 'ぴ'
    'ふ' -> 'ぷ'
    'ぷ' -> 'ふ'
    'ぶ' -> 'ぷ'
    'へ' -> 'ぺ'
    'ぺ' -> 'へ'
    'べ' -> 'ぺ'
    'ほ' -> 'ぽ'
    'ぽ' -> 'ほ'
    'ぼ' -> 'ぽ'

    'ハ' -> 'パ'
    'パ' -> 'ハ'
    'バ' -> 'パ'
    'ヒ' -> 'ピ'
    'ピ' -> 'ヒ'
    'ビ' -> 'ピ'
    'フ' -> 'プ'
    'プ' -> 'フ'
    'ブ' -> 'プ'
    'ヘ' -> 'ペ'
    'ペ' -> 'ヘ'
    'ベ' -> 'ペ'
    'ホ' -> 'ポ'
    'ポ' -> 'ホ'
    'ボ' -> 'ポ'

    else -> this
}
