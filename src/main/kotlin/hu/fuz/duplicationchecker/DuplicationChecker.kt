package hu.fuz.duplicationchecker

import org.apache.commons.io.FileUtils
import java.io.File

class DuplicationChecker {

    var singleFiles: MutableList<File> = mutableListOf()
        private set
    val duplicatedFiles: MutableSet<File> = mutableSetOf()
    val duplications: MutableList<List<File>> = mutableListOf()
    private val files: MutableList<File> = mutableListOf()

    fun collectDuplications(
        vararg directoryPath: String,
        compareFileContent: Boolean = false,
        compareFileName: Boolean = true
    ) {
        checkDirectories(directoryPath)
        collectAllFileInTheDirectoryStructure(directoryPath)
        collectDuplications(compareFileName, compareFileContent)
        collectSingleFiles()
    }

    private fun checkDirectories(directoryPath: Array<out String>) {
        directoryPath.forEach {
            if (!File(it).exists()) {
                throw DirectoryNotFoundException(it)
            }
        }
    }

    private fun collectDuplications(compareFileName: Boolean, compareFileContent: Boolean) {
        files.forEach { file ->
            if (!duplicatedFiles.contains(file)) {
                val duplicationsForFile = getDuplicationsOfFile(file, compareFileName, compareFileContent)
                if (duplicationsForFile.isNotEmpty()) {
                    duplicatedFiles.addAll(duplicationsForFile)
                    duplications.add(duplicationsForFile)
                }
            }
        }
    }

    private fun collectSingleFiles() {
        singleFiles = files.toMutableList()
        singleFiles.removeAll(duplicatedFiles)
    }

    private fun collectAllFileInTheDirectoryStructure(directoriesPath: Array<out String>) {
        directoriesPath.forEach { collectAllFileInTheDirectoryStructure(it) }
    }

    private fun collectAllFileInTheDirectoryStructure(directoryPath: String) {
        File(directoryPath).walkTopDown().forEach {
            if (it.isFile) files.add(it)
        }
    }

    private fun getDuplicationsOfFile(
        file: File,
        compareFileName: Boolean,
        compareFileContent: Boolean
    ): List<File> {
        val duplications = mutableListOf<File>()
        files.forEach {
            if ((!compareFileName || isFileWithSameName(it, file))
                && (!compareFileContent || isFileWithSameContent(it, file))
            ) {
                duplications.add(it)
            }
        }
        if (duplications.isNotEmpty()) duplications.add(file)
        return duplications
    }

    private fun isFileWithSameContent(it: File, file: File) =
        notSameFile(it, file) && FileUtils.contentEquals(it, file)

    private fun isFileWithSameName(it: File, file: File) = notSameFile(it, file) && file.name.equals(it.name)

    private fun notSameFile(it: File, file: File) = !it.equals(file)
}