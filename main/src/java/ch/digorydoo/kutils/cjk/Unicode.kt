@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package ch.digorydoo.kutils.cjk

object Unicode {
    const val NULL_VALUE = '\u0000'
    const val ESCAPE = '\u001b'
    const val MIDDLE_DOT = '\u00b7'           // ·
    const val EN_SPACE = '\u2002'
    const val EM_SPACE = '\u2003'
    const val HAIR_SPACE = '\u200A'
    const val ZERO_WIDTH_SPACE = '\u200B'
    const val BULLET = '\u2022'               // •
    const val TRIANG_RIGHT = '\u2023'         // ‣
    const val HYPHENATION_POINT = '\u2027'
    const val LOW_ASTERISK = '\u204e'         // ⁎
    const val FLOWER_PUNCT = '\u2055'         // ⁕
    const val FOUR_DOT = '\u2058'             // ⁘
    const val ASTERISK_OP = '\u2217'          // ∗
    const val RING_OP = '\u2218'              // ∘
    const val ARROWHEAD_UP = '\u2303'         // ⌃
    const val ARROWHEAD_DOWN = '\u2304'       // ⌄
    const val CHECK_MARK = '\u2713'           // ✓
    const val MODIFIER_ARROW_UP = '\ua71b'    // ꜛ
    const val MODIFIER_ARROW_DOWN = '\ua71c'  // ꜜ
    const val BLACK_RIGHT_FINGER = '\u261b'   // ☛
    const val WHITE_RIGHT_FINGER = '\u261e'   // ☞

    const val KATAKANA_LONG_VOWEL_MARK = '\u30FC' // ー
    const val CJK_NUMBER_ONE = '\u4E00'           // 一

    val ANSI_LOWERCASE_LETTERS = listOf(
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
    )

    class Hiragana {
        val range = '\u3040' .. '\u309f' // includes some punctuation and special chars
        val normal = listOf(
            'あ', 'い', 'う', 'え', 'お',
            'か', 'き', 'く', 'け', 'こ',
            'さ', 'し', 'す', 'せ', 'そ',
            'た', 'ち', 'つ', 'て', 'と',
            'な', 'に', 'ぬ', 'ね', 'の',
            'は', 'ひ', 'ふ', 'へ', 'ほ',
            'ま', 'み', 'む', 'め', 'も',
            'や', 'ゆ', 'よ',
            'ら', 'り', 'る', 'れ', 'ろ',
            'わ', 'を', 'ん',
        )
        val dakuten = listOf(
            'ゔ',
            'が', 'ぎ', 'ぐ', 'げ', 'ご',
            'ざ', 'じ', 'ず', 'ぜ', 'ぞ',
            'だ', 'ぢ', 'づ', 'で', 'ど',
            'ば', 'び', 'ぶ', 'べ', 'ぼ',
        )
        val handakuten = listOf(
            'ぱ', 'ぴ', 'ぷ', 'ぺ', 'ぽ',
        )
        val small = listOf(
            'ぁ', 'ぃ', 'ぅ', 'ぇ', 'ぉ',
            'っ',
            'ゃ', 'ゅ', 'ょ',
        )
        val allCommon = normal + dakuten + handakuten + small
    }

    val HIRAGANA = Hiragana()

    class Katakana {
        val range = '\u30a0' .. '\u30ff' // includes some punctuation and special chars
        val phoneticsRange = '\u31f0' .. '\u31ff'
        val normal = listOf(
            'ア', 'イ', 'ウ', 'エ', 'オ',
            'カ', 'キ', 'ク', 'ケ', 'コ',
            'サ', 'シ', 'ス', 'セ', 'ソ',
            'タ', 'チ', 'ツ', 'テ', 'ト',
            'ナ', 'ニ', 'ヌ', 'ネ', 'ノ',
            'ハ', 'ヒ', 'フ', 'ヘ', 'ホ',
            'マ', 'ミ', 'ム', 'メ', 'モ',
            'ヤ', 'ユ', 'ヨ',
            'ラ', 'リ', 'ル', 'レ', 'ロ',
            'ワ', 'ヲ', 'ン',
        )
        val dakuten = listOf(
            'ヴ',
            'ガ', 'ギ', 'グ', 'ゲ', 'ゴ',
            'ザ', 'ジ', 'ズ', 'ゼ', 'ゾ',
            'ダ', 'ヂ', 'ヅ', 'デ', 'ド',
            'バ', 'ビ', 'ブ', 'ベ', 'ボ',
        )
        val handakuten = listOf(
            'パ', 'ピ', 'プ', 'ペ', 'ポ',
        )
        val small = listOf(
            'ァ', 'ィ', 'ゥ', 'ェ', 'ォ',
            'ヵ', 'ヶ',
            'ッ',
            'ャ', 'ュ', 'ョ',
        )
        val allCommon = normal + dakuten + handakuten + small
    }

    val KATAKANA = Katakana()

    val CJK_RADICALS = '\u2E80' .. '\u2EFF'
    val CJK_SYMBOLS = '\u3000' .. '\u303F'
    val CJK_STROKES = '\u31C0' .. '\u31EF'
    val CJK_ENCLOSED = '\u3200' .. '\u32FF'
    val CJK_IDEOGRAPHS = '\u4E00' .. '\u9FFF'
    val CJK_COMPAT = '\u3300' .. '\u33FF'
    val CJK_COMPAT_IDEOGRAPHS = '\uF900' .. '\uFAFF'
    val CJK_COMPAT_FORMS = '\uFE30' .. '\uFE4F'
    val CJK_IDEOGRAPHS_EXT_A = '\u3400' .. '\u4DBF'

    // The following CJK ranges are not supported.
    // @see https://stackoverflow.com/questions/60753965/kotlin-four-byte-unicode-literals
    // val KANA_ARCHAIC = '\u1b000' .. '\u1b0ff'
    // val KANA_EXT_A = '\u1b100' .. '\u1b12f'
    // val CJK_IDEOGRAPHS_EXT_B = '\u20000' .. '\u2A6DF'
    // val CJK_IDEOGRAPHS_EXT_C = '\u2A700' .. '\u2B73F'
    // val CJK_IDEOGRAPHS_EXT_D = '\u2B740' .. '\u2B81F'
    // val CJK_IDEOGRAPHS_EXT_E = '\u2B820' .. '\u2CEAF'
    // val CJK_IDEOGRAPHS_EXT_F = '\u2CEB0' .. '\u2EBEF'
    // val CJK_COMPAT_SUPPLEMENT = '\u2F800' .. '\u2FA1F'

    val ONE_STROKE_KANJI = arrayOf(
        CJK_NUMBER_ONE, '丶', '丿', '乙', '亅'
    )

    val TWO_STROKE_KANJI = arrayOf(
        '丁', '了', '乃', '乂', '七', '二', '亠', '人', '儿', '入', '八', '冂', '冖', '冫', '几', '凵',
        '刀', '力', '勹', '匕', '匸', '匚', '十', '卜', '卩', '厂', '厶', '又', '九'
    )

    val PUNCTUATION = arrayOf(
        ' ', ',', '.', ';', ':', '!', '?',
        '　', '、', '。', '；', '：', '！', '？'
    )

    val BRACKETS = arrayOf(
        '(', ')', '[', ']', '{', '}',
        '（', '）', '【', '】', '〔', '〕'
    )

    val WESTERN_DIGITS = arrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
    val WIDE_DIGITS = arrayOf('０', '１', '２', '３', '４', '５', '６', '７', '８', '９')

    val CHINESE_DIGITS = arrayOf(
        '丸', CJK_NUMBER_ONE, '二', '三', '四', '五', '六', '七', '八', '九', '十', '百', '千', '万', '億'
    )
}
