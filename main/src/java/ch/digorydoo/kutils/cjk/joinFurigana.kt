package ch.digorydoo.kutils.cjk

/**
 * Constructs a FuriganaString from the given kanji and kana. It is OK to call this function for a word that is kana
 * alone; in that case, pass the same value for `kanji` and `kana`. In case that one of kanji or kana is empty, the non-
 * empty one will be wrapped in a FuriganaString. If the algorithm cannot resolve a pairing, it will return the trivial
 * pair that just puts `kanji` as the kanji part and `kana` as the furigana part. Note that the algorithm sometimes
 * distributes kana incorrectly among kanjis; this cannot be fixed due to inherent ambiguity, see unit tests.
 * @param kanji The kanji plus okurigana, e.g. 立ち場
 * @param kana The same text in kana alone, e.g. たちば
 * @return A FuriganaString whose raw text is of the form "【立：た】ち【場：ば】"
 */
fun joinFurigana(kanji: String, kana: String): FuriganaString {
    if (kanji.isEmpty() || kanji == kana) return FuriganaString(kana)
    if (kana.isEmpty()) return FuriganaString(kanji)

    // Use Dynamic Programming to pair kanjis with kana

    val kanjiLen = kanji.length
    val kanaLen = kana.length

    // table[i][j] = maximum literal kana characters matched while consuming the first i and j characters.
    val table = Array(kanjiLen + 1) { IntArray(kanaLen + 1) { Int.MIN_VALUE } }
    val prev = Array(kanjiLen + 1) { arrayOfNulls<Pair<Int, Int>>(kanaLen + 1) }
    table[0][0] = 0

    fun update(ni: Int, nj: Int, score: Int, pi: Int, pj: Int) {
        if (score > table[ni][nj]) {
            table[ni][nj] = score
            prev[ni][nj] = pi to pj
        }
    }

    for (i in 0 .. kanjiLen) {
        for (j in 0 .. kanaLen) {
            val score = table[i][j]
            if (score == Int.MIN_VALUE) continue

            // Match identical kana literally
            if (i < kanjiLen && j < kanaLen && kanji[i] == kana[j]) {
                update(i + 1, j + 1, score + 1, i, j)
            }

            // Create a furigana block, consuming non-empty text on both sides. This is deliberately a fallback.
            for (ni in i + 1 .. kanjiLen) {
                for (nj in j + 1 .. kanaLen) {
                    update(ni, nj, score, i, j)
                }
            }
        }
    }

    if (table[kanjiLen][kanaLen] == Int.MIN_VALUE) {
        // Something's wrong.
        return FuriganaString("【$kanji：$kana】")
    }

    val segments = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
    var i = kanjiLen
    var j = kanaLen

    while (i > 0 || j > 0) {
        val (pi, pj) = prev[i][j] ?: return FuriganaString("【$kanji：$kana】")
        segments += (pi to i) to (pj to j)
        i = pi
        j = pj
    }

    segments.reverse()

    val result = StringBuilder()

    for ((i, j) in segments) {
        val pi = kanji.substring(i.first, i.second)
        val pj = kana.substring(j.first, j.second)

        if (pi == pj) {
            // This is okurigana.
            result.append(pi)
        } else {
            // This is a kanji with furigana.
            result.append("【$pi：$pj】")
        }
    }

    return FuriganaString(result.toString())
}
