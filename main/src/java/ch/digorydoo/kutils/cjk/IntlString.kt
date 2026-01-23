package ch.digorydoo.kutils.cjk

import java.util.*

/**
 * Member variable names are supposed to be ISO 639-1.
 * @href https://www.loc.gov/standards/iso639-2/php/code_list.php
 */
class IntlString(
    de: String? = null,
    en: String? = null,
    fr: String? = null,
    it: String? = null,
    ja: String? = null
) {
    constructor(other: IntlString): this(
        de = other._de,
        en = other._en,
        fr = other._fr,
        it = other._it,
        ja = other._ja,
    )

    private var _de = de // this is the variable from the constructor, not the getter!
    private var _en = en
    private var _fr = fr
    private var _it = it
    private var _ja = ja

    var de: String
        get() = _de ?: ""
        set(v) {
            _de = v.takeIf { it.isNotEmpty() }
        }

    var en: String
        get() = _en ?: ""
        set(v) {
            _en = v.takeIf { it.isNotEmpty() }
        }

    var fr: String
        get() = _fr ?: ""
        set(v) {
            _fr = v.takeIf { it.isNotEmpty() }
        }

    var it: String
        get() = _it ?: ""
        set(v) {
            _it = v.takeIf { it.isNotEmpty() }
        }

    var ja: String
        get() = _ja ?: ""
        set(v) {
            _ja = v.takeIf { it.isNotEmpty() }
        }

    private val systemLang: String
        get() {
            var theLang = ""
            try {
                theLang = Locale.getDefault().isO3Language
            } catch (e: MissingResourceException) {
                // ignore
            }
            // theLang is ISO 639-2, return it as ISO 639-1
            return when (theLang) {
                "deu" -> "de"
                "fra" -> "fr"
                "fre" -> "fr"
                "ger" -> "de"
                "ita" -> "it"
                "jpn" -> "ja"
                else -> "en"
            }
        }

    val withSystemLang: String
        get() = when (systemLang) {
            "de" -> de
            "fr" -> fr
            "it" -> it
            "ja" -> ja
            else -> en
        }.takeIf { it.isNotEmpty() } ?: en

    fun withSystemLangExcept(except: String) =
        if (systemLang == except) {
            en
        } else {
            withSystemLang
        }

    fun isEmpty() =
        de.isEmpty() && en.isEmpty() && fr.isEmpty() && it.isEmpty() && ja.isEmpty()

    fun isNotEmpty() =
        !isEmpty()

    fun withLanguage(lang: String) = when (lang) {
        "de" -> de
        "en" -> en
        "fr" -> fr
        "it" -> it
        "ja" -> ja
        else -> ""
    }

    fun availableLanguages(): List<String> {
        val result = mutableListOf<String>()

        if (de.isNotEmpty()) result.add("de")
        if (en.isNotEmpty()) result.add("en")
        if (fr.isNotEmpty()) result.add("fr")
        if (it.isNotEmpty()) result.add("it")
        if (ja.isNotEmpty()) result.add("ja")

        return result
    }

    fun clear() {
        _de = null
        _en = null
        _fr = null
        _it = null
        _ja = null
    }

    fun set(other: IntlString) {
        _de = other._de
        _en = other._en
        _fr = other._fr
        _it = other._it
        _ja = other._ja
    }
}
