package ch.digorydoo.kutils.cjk

class RomajiBuilder {
    fun build(textWithFurigana: CharSequence): String {
        var result = ""
        var incompletePrimary = ""
        var incompleteKana = ""
        var addSpaceNext = false

        FuriganaIterator(textWithFurigana).forEachPart(
            { primary, kana ->
                // This is a furigana part. Do not emit it right away, because we want
                // to examine it when the non-furigana part follows.
                incompletePrimary += primary
                incompleteKana += kana
            },
            { between ->
                // This is a non-furigana part.

                if (between.endsWith("っ") || between.endsWith("ッ")) {
                    // Don't emit the part yet, we must still see what follows.
                    incompletePrimary = ""
                    incompleteKana += between
                } else {
                    if (addSpaceNext) {
                        result += " "
                        addSpaceNext = false
                    }

                    var part = between

                    if (incompleteKana.endsWith("っ") || incompleteKana.endsWith("ッ")) {
                        // Merge the furigana part with the non-furigana part, to emit
                        // the tsu correctly.
                        part = incompleteKana + part
                        incompletePrimary = ""
                        incompleteKana = ""
                    }

                    // Emit the furigana part.
                    result += furiganaPartToRomaji(incompletePrimary, incompleteKana)

                    // Emit the non-furigana part.
                    result += splitAtObviousBoundaries(part).joinToString(" ") {
                        // FIXME incompletePrimary should only be called on the first split part!
                        betweenPartToRomaji(it, incompletePrimary)
                    }

                    // Prepare for the next round.
                    addSpaceNext = part.endsWith("を")
                    incompletePrimary = ""
                    incompleteKana = ""
                }
            }
        )

        if (incompleteKana.isNotEmpty()) {
            // The iteration ended in a furigana part. Emit it!
            result += furiganaPartToRomaji(incompletePrimary, incompleteKana)
        }

        // Special cases that would require us to rewrite kanji already emitted.
        return result
            .replace("nihon'ashi", "ni-hon ashi")
            .replace("gofun'inai", "go-fun inai")
            .replace("hyakuman'en", "hyaku-man en")
            .replace("gosen'en", "go-sen-en") // TODO: treat dash as apostrophe
            .replace("sen'en'ijō", "sen-en ijō")
            .replace("sen'en", "sen-en")
            .replace("sarainen'intai", "sarainen intai") // TODO: use space when apostrophe is between kanjis
            .replace("momen'ito", "momen ito")
            .replace("ohayou", "o-hayō")
            .replace("yasumiha", "yasumi wa ")
            .replace("itamiha", "itami wa ")
            .replace("kareraha", "karera wa ")
            .replace("shiteha", "shite wa ")
            .replace("kotaeha", "kotae wa ")
            .replace("hajimeha", "hajime wa ")
            .replace("tanakakunha", "tanakakun wa ")
            .replace("tanondeha", "tanonde wa ")
            .replace("nemurutoha", "nemuru to wa ")
            .replace("nashideha", "nashi de wa ")
            .replace("deha ", "de wa ")
            .replace("sutteha", "sutte wa ")
            .replace("sugiha", "sugi wa ")
            .replace("itteha", "itte wa ")
            .replace("oyoideha", "oyoide wa ")
            .replace("noha ", "no wa ")
            .replace("mitsumerunoha", "mitsumeru no wa ")
            .replace("nohadare", "no wa dare ")
            .replace("nimaino wa wa", "nimai no ha wa ")
            .replace("dekiru toha", "dekiru to wa ")
            .replace("sanha ", "san wa ")
            .replace("yamadasanha", "yamada san wa ")
            .replace("nakamurasanha", "nakamura san wa ")
            .replace("kunha ", "kun wa ")
            .replace("toko rohe", "tokoro e")
            .replace("kyōtoheha", "kyōto e wa ")
            .replace("kunhanemu", "kun wa nemu")
            .replace("machiheno", "machi e no")
            .replace("shiroheno", "shiro e no")
            .replace("hai kenai", "wa ikenai")
            .replace("hai masen", "wa imasen")
            .replace("hai tsumo", "wa itsumo")
            .replace("takusan'aru", "takusan aru")
            .replace("tōkyōheno", "tōkyō e no")
            .replace("doko hemo", "doko e mo")
            .replace("nōmiyage", "no omiyage")
            .replace("nōjīsan", "no ojiisan")
            .replace("jikan'ijō", "jikan ijō")
            .replace("tōshiro", "to o-shiro")
            .replace("nōiwai", "no o-iwai")
            .replace("gohannōkawari", "gohan no o-kawari")
            .replace("monōto", "monooto")
            .replace("monōki", "monooki") // Langenscheidt also has monooki
            .replace("kippūriba", "kippu uriba")
            .replace("sonōmatsuri", "sono o-matsuri")
            .replace("ippon'uemashita", "ippon uemashita")
            .replace("nōjisan", "no o-jisan")
            .replace("nōmimai", "no o-mimai")
            .replace("taihen'isogashī", "taihen isogashii")
            .replace("senseinōtakuni", "sensei no o-takuni")
            .replace(" wa  ita .", " haita.")
            .replace(" wa  ite   kudasai", " haite kudasai")
            .replace("konōcha", "kono o-cha")
            .replace("kekkon'aite", "kekkon aite")
            .replace("nihonheno", "nihon e no")
            .replace("gūzen'aimashita", "gūzen aimashita")
            .replace("jikan'atoni", "jikan atoni")
            .replace("jūnin'ika", "jū-nin ika")
            .replace("bainōkane", "bai no o-kane")
            .replace("'inai", " inai")
    }

    /**
     * Converts the kanji/kana from a furigana part to rōmaji.
     */
    private fun furiganaPartToRomaji(kanji: String, kana: String): String {
        return when ("$kanji：$kana") {
            "走：はし" -> " hashi" // to prevent misinterpretation "wa shirimasu" from "hashirimasu"
            else -> kanaToRomaji(kana)
        }
    }

    /**
     * Converts the kana from a non-furigana part to romaji. This function is called multiple times
     * for each part around obvious word boundaries such as を.
     */
    private fun betweenPartToRomaji(part: CharSequence, preKanji: String): String {
        specialCasesToRomaji(part)?.let { return it }

        if (preKanji == "大" && part == "きな") {
            return "ki na "
        }

        return splitAtKnownWords(part).joinToString(" ") {
            specialCasesToRomaji(it) ?: kanaToRomaji(it)
        }
    }

    /**
     * Handles special cases where kanaToRomaji does not yield the correct result.
     */
    private fun specialCasesToRomaji(kana: CharSequence): String? {
        return when (kana) {
            "うとうと" -> " utouto "
            "うろうろ" -> " urouro "
            "くじらは" -> "kujira wa "
            "これはいくらですか" -> " kore wa ikura desu ka" // not "kore hai kura desu ka"
            "さんは" -> "-san wa "
            "さんのお" -> "-san no o"
            "では" -> " de wa "
            "のうち" -> " no uchi "
            "のひげは" -> " no hige wa "
            "は" -> " wa "
            "はいつですか" -> " wa itsu desu ka " // not "hai tsu desu ka"
            "はきはき" -> " hakihaki "
            "へ" -> " e "
            "はい" -> " hai "
            else -> null
        }
    }

    /**
     * Breaks the given kana string at the boundaries of KNOWN_HIRAGANA_WORDS.
     */
    private fun splitAtKnownWords(part: CharSequence): List<String> {
        val result = mutableListOf<String>()
        var candidates = listOf<String>()
        var sub = ""
        var shiranai = ""
        var i = 0

        fun take(theSub: String) {
            // We assume theSub is a known hiragana word.
            // Try adding characters from shiranai to see if we find a longer word.
            // This is relevant for e.g. はいつも, theSub=も, shiranai=はいつ

            var j = 0
            var extension = ""
            var ext = ""

            while (j < shiranai.length) {
                ext = shiranai[shiranai.length - 1 - j] + ext

                if (KNOWN_HIRAGANA_WORDS.contains(ext + extension + theSub)) {
                    extension += ext
                    ext = ""
                }

                j++
            }

            if (extension.length < shiranai.length) {
                result.add(shiranai.slice(0 ..< shiranai.length - extension.length))
            }

            result.add(extension + theSub)
            shiranai = ""
        }

        while (i < part.length) {
            val prevSub = sub
            val prevCandidates = candidates

            sub += part[i]

            if (candidates.isEmpty()) {
                candidates = KNOWN_HIRAGANA_WORDS
            }

            candidates = candidates.filter { it.startsWith(sub) }

            if (candidates.isEmpty()) {
                if (prevCandidates.contains(prevSub) && !shiranai.endsWith("っ")) {
                    // prevSub is a known word
                    take(prevSub)
                    sub = ""
                    i--
                } else {
                    // prevSub is not a known word

                    if (KNOWN_HIRAGANA_WORDS.contains(shiranai + sub[0]) && !prevSub.endsWith("っ")) {
                        result.add(shiranai + sub[0])

                        // We expect shiranai to be empty already, because if it was non-empty, there must be a
                        // prefix which is NOT contained in KNOWN_HIRAGANA_WORDS, which is impossible. Still, we
                        // explicitly empty shiranai here to make it clear that things start over.
                        shiranai = ""
                    } else {
                        shiranai += sub[0]
                    }

                    i -= sub.length - 1
                    sub = ""
                }
            }

            i++
        }

        if (candidates.isNotEmpty() && candidates.contains(sub) && !shiranai.endsWith("っ")) {
            // sub is a known word
            take(sub)
        } else if (shiranai.isNotEmpty()) {
            // Happens when multiple characters were seen, but there was no match.
            result.add(shiranai + sub)
        } else if (sub.isNotEmpty()) {
            // Happens when only one character was left since we added something to result.
            result.add(sub)
        }

        return result
    }

    /**
     * Breaks the given kana string at punctuation, katakana/hiragana change, or を.
     */
    private fun splitAtObviousBoundaries(part: CharSequence): List<CharSequence> {
        val result = mutableListOf<CharSequence>()
        var startAt = 0
        var wasKatakana = false
        var takeNext = false

        for (i in part.indices) {
            val c = part[i]
            var take = false

            when {
                takeNext -> {
                    take = true
                    takeNext = false
                    wasKatakana = c.isKatakana()
                }

                c == 'を' || c.isPunctuation() -> {
                    take = true
                    takeNext = true
                    wasKatakana = false
                }

                c.isHiragana() -> {
                    if (wasKatakana) {
                        take = true
                        wasKatakana = false
                    }
                }

                c.isKatakana() -> {
                    if (!wasKatakana) {
                        take = true
                        wasKatakana = true
                    }
                }
            }

            if (take && i > startAt) {
                result.add(part.slice(startAt ..< i))
                startAt = i
            }
        }

        if (startAt < part.length) {
            result.add(part.slice(startAt ..< part.length))
        }

        return result
    }

    companion object {
        private val KNOWN_HIRAGANA_WORDS = listOf(
            "あなた",
            "ありました",
            "あります",
            "ある",
            "あれ",
            "いじめて",
            "いつ",
            "いつも",
            "いとこ",
            "いらっしゃる",
            "うち",
            "うとうと",
            "うろうろ",
            "おかげ",
            "か",
            "から",
            "かれら",
            "が",
            "がっかり",
            "きっと",
            "ここ",
            "こちら",
            "こっそり",
            "こっち",
            "こと",
            "これ",
            "こんにち",
            "こんばん",
            "ごにょ",
            "ごはん",
            "さっき",
            "しっかり",
            "すぎる",
            "すっかり",
            "ずいぶん",
            "せっかく",
            "そこ",
            "そちら",
            "それ",
            "それで",
            "たくさん",
            "たち",
            "ちょっと",
            "できる",
            "です",
            "とき",
            "ところ",
            "とても",
            "どうして",
            "どこ",
            "どんな",
            "なくて",
            "に",
            "は",
            "はがき",
            "はきかえて",
            "はく",
            "はさみ",
            "はしご",
            "はじめ",
            "はず",
            "はっきり",
            "はまる",
            "はめる",
            "ほとんど",
            "ぼく",
            "まず",
            "また",
            "まだ",
            "も",
            "もう",
            "もっと",
            "よ",
            "よう",
            "ようこそ",
        )
    }
}
