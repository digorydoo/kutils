package ch.digorydoo.kutils.string

fun CharSequence.indexOfAnyExcept(except: CharArray, startIdx: Int = 0): Int =
    foldIndexed(-1) { idx, result, c ->
        if (result >= 0) {
            result
        } else if (idx < startIdx) {
            -1
        } else if (except.contains(c)) {
            -1
        } else {
            idx
        }
    }
