package ch.digorydoo.kutils.cjk

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * NOTE: goigoi-compiler's WordChecker contains an integrated unit test for joinFurigana! That check ensures
 * joinFurigana doesn't crash for any word in goigoi's vocabulary, and always produces a FuriganaString that
 * preserves the original kanji and kana even though the actual distribution of the furigana may be different.
 * We cannot move this test here, because we don't have access to goigoi's vocabulary from kutils project.
 */
internal class JoinFuriganaTest {
    @Test
    fun `should return empty furigana when both kanji and kana are empty`() {
        assertEquals("", joinFurigana("", "").raw)
    }

    @Test
    fun `should return the non-empty one when one of the inputs is empty and the other one isn't`() {
        assertEquals("猫", joinFurigana("猫", "").raw)
        assertEquals("ねこ", joinFurigana("", "ねこ").raw)
    }

    @Test
    fun `should return the kana input when both kanji and kana are the same`() {
        assertEquals("あ", joinFurigana("あ", "あ").raw)
        assertEquals("めっちゃ", joinFurigana("めっちゃ", "めっちゃ").raw)
    }

    @Test
    fun `should return one furigana pair when there is no okurigana`() {
        assertEquals("【私：わたし】", joinFurigana("私", "わたし").raw)

        // The algorithm cannot tell which kana part belongs to which kanji when there is no okurigana between.
        // The resulting furigana is not ideal, but not wrong either.
        assertEquals("【電車：でんしゃ】", joinFurigana("電車", "でんしゃ").raw)
        assertEquals("【方々：かたがた】", joinFurigana("方々", "かたがた").raw)
        assertEquals("【文章：ぶんしょう】", joinFurigana("文章", "ぶんしょう").raw)
        assertEquals("【小説：しょうせつ】", joinFurigana("小説", "しょうせつ").raw)

        // Here, the kana part must not be split, so the resulting furigana is already optimal.
        assertEquals("【今日：きょう】", joinFurigana("今日", "きょう").raw)
    }

    @Test
    fun `should allow arbitrary characters in the kanji part`() {
        assertEquals("【TV：てれび】", joinFurigana("TV", "てれび").raw)
    }

    @Test
    fun `should allow katakana and other characters as furigana`() {
        assertEquals("【私：ワタシ】", joinFurigana("私", "ワタシ").raw)
        assertEquals("【七：７】", joinFurigana("七", "７").raw)
    }

    @Test
    fun `should put shared kana in front of the furigana pair when it appears at the start`() {
        assertEquals("お【風呂：ふろ】", joinFurigana("お風呂", "おふろ").raw)
        assertEquals("ムシ【歯：ば】", joinFurigana("ムシ歯", "ムシば").raw)
    }

    @Test
    fun `should put shared kana behind the furigana pair when it appears at the end`() {
        assertEquals("【食：た】べる", joinFurigana("食べる", "たべる").raw)
    }

    @Test
    fun `should handle okurigana in the expected way`() {
        // These cases produce perfect furigana.
        assertEquals("【立：た】ち【場：ば】", joinFurigana("立ち場", "たちば").raw)
        assertEquals("【乗：の】り【物：もの】", joinFurigana("乗り物", "のりもの").raw)
        assertEquals("【切：き】り【傷：きず】", joinFurigana("切り傷", "きりきず").raw)
        assertEquals("【思：おも】い【思：おも】い", joinFurigana("思い思い", "おもいおもい").raw)
        assertEquals("【渡：わた】り【鳥：どり】", joinFurigana("渡り鳥", "わたりどり").raw)

        // Here, the situation is ambiguous, and the algorithm can't tell where to put the つ.
        assertEquals("【五：い】つ【星：つぼし】ホテル", joinFurigana("五つ星ホテル", "いつつぼしホテル").raw)

        // Here, the situation is ambiguous, too, but the algorithm manages to find the perfect solution by chance.
        assertEquals("【同：おな】じ【冗談：じょうだん】", joinFurigana("同じ冗談", "おなじじょうだん").raw)
    }

    @Test
    fun `should allow kana in the kanji part`() {
        assertEquals("【タンパク質：たんぱくしつ】", joinFurigana("タンパク質", "たんぱくしつ").raw)
        assertEquals("【ヶ月：かげつ】", joinFurigana("ヶ月", "かげつ").raw)
    }

    @Test
    fun `should handle some odd edge-cases in the expected way`() {
        // The algorithm is not confused by the small tsu, even though this is nonsense, of course.
        assertEquals("【BAD：ょうだん】", joinFurigana("BAD", "ょうだん").raw)

        // Adjacent furigana pairs are unaffected, too.
        assertEquals(
            "その【文章：ぶんしょう】は【BAD：ょうだん】です。",
            joinFurigana("その文章はBADです。", "そのぶんしょうはょうだんです。").raw
        )

        // The input is illegal, forcing the algorithm to put a kana into the kanji part.
        assertEquals("お【風呂ふ：ふ】ろ", joinFurigana("お風呂ふろ", "おふろ").raw)
    }
}
