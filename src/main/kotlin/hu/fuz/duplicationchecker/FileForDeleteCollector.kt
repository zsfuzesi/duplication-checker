package hu.fuz.duplicationchecker

import java.io.File

class FileForDeleteCollector(
    val files: List<List<File>>,
    val ereaseFromDirectory: File?
) {

    lateinit var filesForErease: MutableList<File>
    lateinit var duplicationsInEreaseFromDirectory: MutableSet<File>

    fun calculateFilesForErease() {
        filesForErease = mutableListOf()
        duplicationsInEreaseFromDirectory = mutableSetOf()

        files.forEach { duplications ->
            val filesInEreaseFromDirectory = collectFilesInEreaseFromDirectory(duplications)
            if(isExactlyOneFileExistsIn(filesInEreaseFromDirectory)){
                filesForErease += filesInEreaseFromDirectory[0]
            }else if(isMoreThanOneFileExistsIn(filesInEreaseFromDirectory)){
                duplicationsInEreaseFromDirectory += filesInEreaseFromDirectory
            }
        }
    }

    private fun collectFilesInEreaseFromDirectory(duplications: List<File>) =
        duplications.filter { it.parentFile.equals(ereaseFromDirectory) }

    private fun isExactlyOneFileExistsIn(files: List<File>) = files.size == 1
    private fun isMoreThanOneFileExistsIn(files: List<File>) = files.size > 1

}
