package hu.fuz.duplicationchecker

import org.apache.commons.io.FileUtils
import java.io.File

class DuplicationChecker {

    val singleFiles: MutableList<File> = mutableListOf()
    val duplicatedFiles: MutableSet<File> = mutableSetOf()
    val duplications: MutableList<List<File>> = mutableListOf()
    private val files: MutableList<File> = mutableListOf()

    fun collectDuplicationsAndSingleFiles(
        vararg directoryPath: String,
        isCompareFileContent: Boolean = false,
        isCompareFileName: Boolean = true
    ) {
        checkDirectories(directoryPath)
        collectAllFileInTheDirectoryStructure(directoryPath)
        collectDuplicationsAndSingleFiles(isCompareFileName, isCompareFileContent)
    }

    private fun checkDirectories(directoryPath: Array<out String>) {
        directoryPath.forEach {
            if (!File(it).exists()) {
                throw DirectoryNotFoundException(it)
            }
        }
    }

    private fun collectDuplicationsAndSingleFiles(isCompareFileName: Boolean, isCompareFileContent: Boolean) {
        while ( files.isNotEmpty() ) {
            val file = files[0]
            val duplicationsForFile = collectDuplicationsOf(file, isCompareFileName, isCompareFileContent)
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
        isCompareByFileName: Boolean,
        isCompareFileContent: Boolean
    ): List<File> {
        val duplications = mutableListOf<File>()
        files.forEach {
            if ((!isCompareByFileName || isFileWithSameName(it, file))
                && (!isCompareFileContent || isFileWithSameContent(it, file))
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
