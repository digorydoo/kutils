package ch.digorydoo.kutils.cjk

fun Char.isDakuten() =
    Unicode.HIRAGANA.dakuten.contains(this) || Unicode.KATAKANA.dakuten.contains(this)

fun Char.isHandakuten() =
    Unicode.HIRAGANA.handakuten.contains(this) || Unicode.KATAKANA.handakuten.contains(this)

fun CharSequence.hasDakuten() =
    any { it.isDakuten() }

fun CharSequence.hasHandakuten() =
    any { it.isHandakuten() }

fun Char.toDakuten() = when (this) {
    'う' -> 'ゔ'

    'か' -> 'が'
    'き' -> 'ぎ'
    'く' -> 'ぐ'
    'け' -> 'げ'
    'こ' -> 'ご'

    'さ' -> 'ざ'
    'し' -> 'じ'
    'す' -> 'ず'
    'せ' -> 'ぜ'
    'そ' -> 'ぞ'

    'た' -> 'だ'
    'ち' -> 'ぢ'
    'つ' -> 'づ'
    'て' -> 'で'
    'と' -> 'ど'

    'は' -> 'ば'
    'ひ' -> 'び'
    'ふ' -> 'ぶ'
    'へ' -> 'べ'
    'ほ' -> 'ぼ'

    'ぱ' -> 'ば'
    'ぴ' -> 'び'
    'ぷ' -> 'ぶ'
    'ぺ' -> 'べ'
    'ぽ' -> 'ぼ'

    'ウ' -> 'ヴ'

    'カ' -> 'ガ'
    'キ' -> 'ギ'
    'ク' -> 'グ'
    'ケ' -> 'ゲ'
    'コ' -> 'ゴ'

    'サ' -> 'ザ'
    'シ' -> 'ジ'
    'ス' -> 'ズ'
    'セ' -> 'ゼ'
    'ソ' -> 'ゾ'

    'タ' -> 'ダ'
    'チ' -> 'ヂ'
    'ツ' -> 'ヅ'
    'テ' -> 'デ'
    'ト' -> 'ド'

    'ハ' -> 'バ'
    'ヒ' -> 'ビ'
    'フ' -> 'ブ'
    'ヘ' -> 'ベ'
    'ホ' -> 'ボ'

    'パ' -> 'バ'
    'ピ' -> 'ビ'
    'プ' -> 'ブ'
    'ペ' -> 'ベ'
    'ポ' -> 'ボ'

    else -> this
}

fun Char.toHandakuten() = when (this) {
    'は' -> 'ぱ'
    'ば' -> 'ぱ'
    'ひ' -> 'ぴ'
    'び' -> 'ぴ'
    'ふ' -> 'ぷ'
    'ぶ' -> 'ぷ'
    'へ' -> 'ぺ'
    'べ' -> 'ぺ'
    'ほ' -> 'ぽ'
    'ぼ' -> 'ぽ'

    'ハ' -> 'パ'
    'バ' -> 'パ'
    'ヒ' -> 'ピ'
    'ビ' -> 'ピ'
    'フ' -> 'プ'
    'ブ' -> 'プ'
    'ヘ' -> 'ペ'
    'ベ' -> 'ペ'
    'ホ' -> 'ポ'
    'ボ' -> 'ポ'

    else -> this
}

fun Char.removeDakutenHandakuten() = when (this) {
    'ゔ' -> 'う'

    'が' -> 'か'
    'ぎ' -> 'き'
    'ぐ' -> 'く'
    'げ' -> 'け'
    'ご' -> 'こ'

    'ざ' -> 'さ'
    'じ' -> 'し'
    'ず' -> 'す'
    'ぜ' -> 'せ'
    'ぞ' -> 'そ'

    'だ' -> 'た'
    'ぢ' -> 'ち'
    'づ' -> 'つ'
    'で' -> 'て'
    'ど' -> 'と'

    'ば' -> 'は'
    'び' -> 'ひ'
    'ぶ' -> 'ふ'
    'べ' -> 'へ'
    'ぼ' -> 'ほ'

    'ぱ' -> 'は'
    'ぴ' -> 'ひ'
    'ぷ' -> 'ふ'
    'ぺ' -> 'へ'
    'ぽ' -> 'ほ'

    'ヴ' -> 'ウ'

    'ガ' -> 'カ'
    'ギ' -> 'キ'
    'グ' -> 'ク'
    'ゲ' -> 'ケ'
    'ゴ' -> 'コ'

    'ザ' -> 'サ'
    'ジ' -> 'シ'
    'ズ' -> 'ス'
    'ゼ' -> 'セ'
    'ゾ' -> 'ソ'

    'ダ' -> 'タ'
    'ヂ' -> 'チ'
    'ヅ' -> 'ツ'
    'デ' -> 'テ'
    'ド' -> 'ト'

    'バ' -> 'ハ'
    'ビ' -> 'ヒ'
    'ブ' -> 'フ'
    'ベ' -> 'ヘ'
    'ボ' -> 'ホ'

    'パ' -> 'ハ'
    'ピ' -> 'ヒ'
    'プ' -> 'フ'
    'ペ' -> 'ヘ'
    'ポ' -> 'ホ'

    else -> this
}

/**
 * Toggle tries to apply a cycle:
 * When c cannot have dakuten nor handakuten: A -> A
 * When c cannot have handakuten: た -> だ -> た
 * When c can have both dakuten and handakuten: ふ -> ぶ -> ぷ -> ふ
 */
fun Char.toggleDakutenHandakuten() = when {
    isDakuten() -> removeDakutenHandakuten().toHandakuten()
    isHandakuten() -> removeDakutenHandakuten()
    else -> toDakuten()
}

fun Char.equalsExceptDakutenHandakuten(other: Char): Boolean {
    if (this == other) return true

    val baseA = this.removeDakutenHandakuten()
    val baseB = other.removeDakutenHandakuten()
    return baseA == baseB
}

fun String.equalsExceptDakutenHandakuten(other: String): Boolean {
    if (length != other.length) return false
    else if (this == other) return true

    var result = true

    forEachIndexed { i, ai ->
        val bi = other[i]
        result = result && ai.equalsExceptDakutenHandakuten(bi)
    }

    return result
}
