@file:Suppress("unused")

package ch.digorydoo.kutils.file

import java.io.File

fun checkPath(
    path: String,
    allowNonExisting: Boolean = false,
    allowFile: Boolean = false,
    allowDir: Boolean = false,
    allowSpecial: Boolean = false,
    checkWritable: Boolean = false,
    onFail: (msg: String) -> File, // should throw or return a fallback file
): File {
    try {
        if (path.isEmpty()) {
            return onFail("Path is empty")
        }

        val f = File(path)
        val exists = f.exists()
        val isDirectory = exists && f.isDirectory
        val isFile = exists && !isDirectory && f.isFile
        val isSpecial = exists && !isFile && !isDirectory

        return when {
            checkWritable && exists && !f.canWrite() -> return onFail("Cannot overwrite: $path")
            checkWritable && !exists && f.parentFile?.canWrite() != true -> return onFail("Cannot write to path: $path")
            isFile && allowFile -> f
            isDirectory && allowDir -> f
            isSpecial && allowSpecial -> f
            !exists && allowNonExisting -> f
            else -> when {
                !allowNonExisting && !exists && allowFile -> return onFail("File does not exist: $path")
                !allowNonExisting && !exists && allowDir -> return onFail("Directory does not exist: $path")
                allowFile && isDirectory -> return onFail("Expected a file, but got a directory: $path")
                allowDir && isFile -> return onFail("Expected a directory, but got a file: $path")
                else -> return onFail("Path does not meet requirements: $path")
            }
        }
    } catch (e: Throwable) {
        return onFail("Exception while accessing path: $path\nException: ${e.message}")
    }
}
