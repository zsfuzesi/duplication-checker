package hu.fuz.duplicationchecker

import java.io.File

class FileForDeleteCollector(
    private val files: List<List<File>>,
    private val eraseFromDirectory: File?
) {

    fun calculateFilesForErease(): FilesForEraseResult {
        val filesForErase: MutableList<File> = mutableListOf()
        val duplicationsInEraseFromDirectory: MutableSet<File> = hashSetOf()

        files.forEach { duplications ->
            val filesInEreaseFromDirectory = collectFilesInEreaseFromDirectory(duplications)
            if (isExactlyOneFileExistsIn(filesInEreaseFromDirectory)) {
                filesForErase += filesInEreaseFromDirectory[0]
            } else if (isMoreThanOneFileExistsIn(filesInEreaseFromDirectory)) {
                duplicationsInEraseFromDirectory += filesInEreaseFromDirectory
            }
        }

        return FilesForEraseResult(filesForErase, duplicationsInEraseFromDirectory)
    }

    private fun collectFilesInEreaseFromDirectory(duplications: List<File>) =
        duplications.filter { it.parentFile.equals(eraseFromDirectory) }

    private fun isExactlyOneFileExistsIn(files: List<File>) = files.size == 1
    private fun isMoreThanOneFileExistsIn(files: List<File>) = files.size > 1

}
