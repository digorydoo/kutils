package ch.digorydoo.kutils.utils

import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit

class UnexpectedGitResponse(output: String): Exception("Unexpected git response: $output")

fun isUnderGit(inputDir: String, filename: String): Boolean {
    val cmd = arrayOf(
        "cd \"${File(inputDir).absolutePath}\"",
        "git ls-files --error-unmatch -- \"${filename}\"",
    ).joinToString(" && ")

    val p1 = Runtime.getRuntime().exec(arrayOf("bash", "-c", cmd))
    p1.waitFor(30, TimeUnit.SECONDS)
    return p1.exitValue() == 0
}

/**
 * @return The timestamp as YYYY-MM-DD HH:mm:ss zZZZZ; throws on error
 */
fun getTimeOfFirstCommit(inputDir: String, filename: String): String {
    val cmd = arrayOf(
        "cd \"${File(inputDir).absolutePath}\"",
        "git log --diff-filter=A --format='%ci' -- \"${filename}\"",
    ).joinToString(" && ")

    val p2 = Runtime.getRuntime().exec(arrayOf("bash", "-c", cmd))
    p2.waitFor(30, TimeUnit.SECONDS)

    val output = p2.inputStream.use { BufferedReader(InputStreamReader(it)).readText() }
    val stamp = output.split("\n").firstOrNull()?.trim() ?: ""

    // stamp is expected to be YYYY-MM-DD HH:mm:ss zZZZZ
    if (stamp.length != 25) {
        throw UnexpectedGitResponse(output)
    }

    return stamp
}
