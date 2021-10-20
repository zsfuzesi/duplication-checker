package hu.fuz.duplicationchecker

import org.apache.commons.io.FileUtils
import java.io.File

typealias FileDuplicatinGroup = MutableList<List<File>>

class DuplicationChecker {

    val singleFiles: MutableSet<File> = mutableSetOf()
    val duplicatedFiles: MutableSet<File> = mutableSetOf()
    val duplications: FileDuplicatinGroup = mutableListOf()
    private val files: MutableList<File> = mutableListOf()

    fun collectDuplicationsAndSingleFiles(
        vararg directoryPath: String,
        compareByFileContent: Boolean = false,
        compareByFileName: Boolean = true
    ) {
        checkDirectories(directoryPath)
        collectAllFileInTheDirectoryStructure(directoryPath)
        collectDuplicationsAndSingleFiles(compareByFileName, compareByFileContent)
    }

    private fun checkDirectories(directoryPath: Array<out String>) {
        directoryPath.forEach {
            if (!File(it).exists()) {
                throw DirectoryNotFoundException(it)
            }
        }
    }

    private fun collectDuplicationsAndSingleFiles(compareByFileName: Boolean, compareByFileContent: Boolean) {
        while ( files.isNotEmpty() ) {
            val file = files[0]
            val duplicationsForFile = collectDuplicationsOf(file, compareByFileName, compareByFileContent)
            if (duplicationsForFile.isEmpty()) {
                singleFiles += file
                files.remove(file)
            } else {
                duplicatedFiles.addAll(duplicationsForFile)
                duplications.add(duplicationsForFile)
                files.removeAll(duplicationsForFile)
            }
        }
    }

    private fun collectAllFileInTheDirectoryStructure(directoriesPath: Array<out String>) {
        directoriesPath.forEach { collectAllFileInTheDirectoryStructure(it) }
    }

    private fun collectAllFileInTheDirectoryStructure(directoryPath: String) {
        File(directoryPath).walkTopDown().forEach {
            if (it.isFile) files.add(it)
        }
    }

    private fun collectDuplicationsOf(
        file: File,
        compareByFileName: Boolean,
        compareByFileContent: Boolean
    ): List<File> {
        val duplications = mutableListOf<File>()
        files.forEach {
            if ((!compareByFileName || isFileNameEquals(it, file))
                && (!compareByFileContent || isFileContentEquals(it, file))
            ) {
                duplications.add(it)
            }
        }
        if (duplications.isNotEmpty()) duplications.add(file)
        return duplications
    }

    private fun isFileContentEquals(it: File, file: File) =
        notSameFile(it, file) && FileUtils.contentEquals(it, file)

    private fun isFileNameEquals(it: File, file: File) = notSameFile(it, file) && file.name.equals(it.name)

    private fun notSameFile(it: File, file: File) = it != file
}
