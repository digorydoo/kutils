package ch.digorydoo.kutils.cjk

import ch.digorydoo.kutils.cjk.Unicode.CJK_COMPAT_IDEOGRAPHS
import ch.digorydoo.kutils.cjk.Unicode.CJK_IDEOGRAPHS
import ch.digorydoo.kutils.cjk.Unicode.CJK_IDEOGRAPHS_EXT_A
import ch.digorydoo.kutils.cjk.Unicode.NORMAL_WIDTH_SPACE
import ch.digorydoo.kutils.cjk.Unicode.WESTERN_DIGITS
import ch.digorydoo.kutils.cjk.Unicode.WIDE_DIGITS
import ch.digorydoo.kutils.cjk.Unicode.WIDE_WIDTH_SPACE
import ch.digorydoo.kutils.cjk.Unicode.ZERO_WIDTH_SPACE

class FuriganaAligner {
    /**
     * @param primary Japanese text including kanji, kana, punctuation, etc.
     * @param secondary The same text whose kanji have been replaced by kana
     * @return A list of kanji/kana pairs or okurigana/punctuation/literal, or null if alignment is not possible
     */
    fun align(primary: String, secondary: String): List<KanjiAndKanaBase>? {
        var p = removeSpaces(primary)
        var s = removeSpaces(secondary)

        // Add a ZERO_WIDTH_SPACE after certain patterns to force an alignment
        forcedAlignments.forEach { (kanji, kana) ->
            var pi = 0
            var si = 0

            while (true) {
                val pj = p.indexOf(kanji, pi)
                val sj = s.indexOf(kana, si)

                if (pj >= 0 && sj >= 0) {
                    p = p.substring(0, pj) + kanji + ZERO_WIDTH_SPACE + p.substring(pj + kanji.length)
                    s = s.substring(0, sj) + kana + ZERO_WIDTH_SPACE + s.substring(sj + kana.length)
                    pi = pj + 1
                    si = sj + 1
                } else break
            }
        }

        val solved = align(p, 0, s, 0) ?: return null

        // Verify correctness, and also remove the ZERO_WIDTH_SPACE we've introduced
        return solved.map { part ->
            when (part) {
                is KanjiAndKana -> {
                    if (part.kanji.isEmpty()) error("kanji is empty!")
                    if (part.kana.isEmpty()) error("kana is empty!")

                    // Special case for ほお、ほほ is for an entry in jpod101
                    if (part.kana.length > 4 * part.kanji.length && part.kana != "ほお、ほほ") {
                        // 4 is possible, e.g. 雷・かみなり
                        error("Furigana is exceedingly long: kanji=${part.kanji}, kana=${part.kana}")
                    }

                    KanjiAndKana(removeSpaces(part.kanji), removeSpaces(part.kana))
                }
                is OkuriganaOrLiteral -> {
                    if (part.span.isEmpty()) error("Empty okurigana span in furigana!")
                    part
                }
            }
        }
    }

    private fun align(primary: String, pi: Int, secondary: String, si: Int): List<KanjiAndKanaBase>? {
        if (pi == primary.length && si == secondary.length) {
            return listOf() // nothing to match
        }

        if (pi >= primary.length) {
            return null // primary exhausted
        }

        if (si >= secondary.length) {
            return null // secondary exhausted
        }

        val pc = primary[pi]

        if (canHaveFurigana(pc)) {
            var kanjiEnd = pi + 1

            while (kanjiEnd < primary.length && canHaveFurigana(primary[kanjiEnd])) {
                kanjiEnd++ // make the kanji part as large as possible
            }

            val kanji = primary.substring(pi, kanjiEnd)
            var kanaEnd = si + 1

            while (kanaEnd < secondary.length && !canHaveFurigana(secondary[kanaEnd])) {
                kanaEnd++ // make the kana part as large as possible
            }

            while (kanaEnd > si) {
                val kana = secondary.substring(si, kanaEnd)
                val rest = align(primary, kanjiEnd, secondary, kanaEnd)

                if (rest != null) {
                    return mutableListOf<KanjiAndKanaBase>(KanjiAndKana(kanji, kana)).apply { addAll(rest) }
                } else {
                    kanaEnd-- // backtrack, try assigning fewer kana to the kanji
                }
            }
        }

        val sc = secondary[si]

        if (
            pc == sc ||
            pc.toHiragana() == sc ||
            isSameDigit(pc, sc) ||
            ((pc == '～' || pc == '〜') && sc == '~')
        ) {
            // Part with no furigana or suppressed furigana, i.e. kana-only word, okurigana, punctuation, etc.

            var pj = pi + 1
            var sj = si + 1

            while (pj < primary.length && sj < secondary.length && primary[pj] == secondary[sj]) {
                pj++
                sj++
            }

            val span = primary.substring(pi, pj) // should be same as secondary.substring(si, sj)
            val rest = align(primary, pj, secondary, sj)

            if (rest != null) {
                return mutableListOf<KanjiAndKanaBase>(OkuriganaOrLiteral(span)).apply { addAll(rest) }
            }
        }

        return null // dead end
    }

    private fun removeSpaces(s: String) = s.filter {
        it != NORMAL_WIDTH_SPACE && it != WIDE_WIDTH_SPACE && it != ZERO_WIDTH_SPACE
    }

    private fun canHaveFurigana(c: Char) =
        c == '々' ||
            c == 'ヶ' ||
            c == '．' ||
            c == '.' ||
            c == '％' ||
            c == '%' ||
            c in CJK_IDEOGRAPHS ||
            c in CJK_IDEOGRAPHS_EXT_A ||
            c in CJK_COMPAT_IDEOGRAPHS ||
            c in WESTERN_DIGITS ||
            c in WIDE_DIGITS ||
            (c >= 'a' && c <= 'z') ||
            (c >= 'A' && c <= 'Z') ||
            (c >= 'ａ' && c <= 'ｚ') ||
            (c >= 'Ａ' && c <= 'Ｚ')

    companion object {
        // When a sentence contains both the kanji and kana part of one of these pairs, a ZERO_WIDTH_SPACE will be
        // inserted into both the kanji and kana part to force an alignment there. This helps for ambiguous cases where
        // the algorithm incorrectly distributes kana part among the kanji. Note that each entry should start with one
        // or more kanji and end in a kana to prevent from accidental matches that would lead to new problems.
        private val forcedAlignments = listOf(
            KanjiAndKana("一杯", "いっぱい"),
            KanjiAndKana("一番", "いちばん"),
            KanjiAndKana("二度", "にど"),
            KanjiAndKana("人々", "ひとびと"),
            KanjiAndKana("人が", "ひとが"),
            KanjiAndKana("人は", "ひとは"),
            KanjiAndKana("今日", "きょう"),
            KanjiAndKana("使う", "つかう"),
            KanjiAndKana("兄と", "あにと"),
            KanjiAndKana("全国に", "ぜんこくに"),
            KanjiAndKana("出張は", "しゅっちょうは"),
            KanjiAndKana("半分", "はんぶん"),
            KanjiAndKana("古い", "ふるい"),
            KanjiAndKana("壁掛け", "かべかけ"),
            KanjiAndKana("女性は", "じょせいは"),
            KanjiAndKana("小説は", "しょうせつは"),
            KanjiAndKana("弱い", "よわい"),
            KanjiAndKana("強い", "つよい"),
            KanjiAndKana("彼は", "かれは"),
            KanjiAndKana("担当医と", "たんとういと"),
            KanjiAndKana("時計は", "とけいは"),
            KanjiAndKana("毎日", "まいにち"),
            KanjiAndKana("毎週", "まいしゅう"),
            KanjiAndKana("汚い", "きたない"),
            KanjiAndKana("深い", "ふかい"),
            KanjiAndKana("濃い", "こい"),
            KanjiAndKana("火事と", "かじと"),
            KanjiAndKana("男性は", "だんせいは"),
            KanjiAndKana("私の", "わたしの"),
            KanjiAndKana("私は", "わたしは"),
            KanjiAndKana("競走は", "きょうそうは"),
            KanjiAndKana("臭い", "くさい"),
            KanjiAndKana("良い", "よい"),
            KanjiAndKana("薄い", "うすい"),
            KanjiAndKana("蜘蛛は", "くもは"),
            KanjiAndKana("象は", "ぞうは"),
            KanjiAndKana("赤い", "あかい"),
            KanjiAndKana("醜い", "みにくい"),
            KanjiAndKana("鉛は", "なまりは"),
            KanjiAndKana("雪の", "ゆきの"),
            KanjiAndKana("高い", "たかい"),
            KanjiAndKana("黒い", "くろい"),
            KanjiAndKana("黒酢は", "くろずは"),
        )
    }
}
