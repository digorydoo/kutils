@file:Suppress("unused", "MemberVisibilityCanBePrivate", "RedundantVisibilityModifier")

package ch.digorydoo.kutils.cjk

public class FuriganaIterator(private val text: CharSequence): Iterator<FuriganaIterator.Range> {
    class Range(val range: IntRange, val primaryText: CharSequence, val secondaryText: CharSequence)

    private var index = 0
    private var current: Range? = null

    override fun hasNext(): Boolean {
        return current != null
    }

    override fun next(): Range {
        val ret = current!!
        advance()
        return ret
    }

    private fun advance() {
        var startAt: Int
        var endAt: Int
        var sepAt: Int
        current = null

        do {
            if (index >= text.length) {
                return
            }

            startAt = text.indexOf(FURIGANA_START, index)

            if (startAt < 0) {
                return
            }

            endAt = text.indexOf(FURIGANA_END, startAt)

            if (endAt < 0) {
                return
            }

            sepAt = text.indexOf(FURIGANA_SEPARATOR, startAt)

            if (sepAt < 0) {
                return
            }

            index = endAt
        } while (sepAt > endAt)

        val primary = text.slice(startAt + 1 ..< sepAt)
        val secondary = text.slice(sepAt + 1 ..< endAt)
        current = Range(startAt .. endAt, primary, secondary)
    }

    fun forEachPart(
        furigana: (kanji: CharSequence, kana: CharSequence) -> Unit,
        between: (part: CharSequence) -> Unit,
    ) {
        var i = 0

        while (hasNext()) {
            val r = next()

            if (i < r.range.first) {
                between(text.slice(i ..< r.range.first))
            }

            furigana(r.primaryText, r.secondaryText)
            i = r.range.last + 1
        }

        if (i < text.length) {
            between(text.slice(i ..< text.length))
        }
    }

    init {
        advance()
    }

    companion object {
        private const val FURIGANA_START = '【'
        private const val FURIGANA_SEPARATOR = '：'
        private const val FURIGANA_END = '】'
    }
}
