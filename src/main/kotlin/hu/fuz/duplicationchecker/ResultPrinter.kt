package hu.fuz.duplicationchecker

import java.io.File

class ResultPrinter(
    val isSingleFilesPrint: Boolean,
    val isDuplicatedFilesPrint: Boolean,) {

    fun printResult(singleFiles: List<File>, duplications: List<List<File>>) {
        if(isSingleFilesPrint) {
            printSingleFiles(singleFiles)
        }

        if(isDuplicatedFilesPrint) {
            printDuplicatedFiles(duplications)
        }
    }

    private fun printDuplicatedFiles(duplications: List<List<File>>) {
        println(
            """
            
            Duplicated files:""".trimIndent()
        )
        duplications.forEach {
            println("---")
            it.forEach {
                println(it)
            }
        }
    }

    private fun printSingleFiles(singleFiles: List<File>) {
        println(
            """
            
            Single files:""".trimIndent()
        )
        singleFiles.forEach {
            println(it)
        }
    }

    fun printEreaseDryRun(files: List<String>) {
        println(
            """
            
            Erease DRY RUN:""".trimIndent()
        )
        files.forEach { println(it) }
    }

    fun printFileAfterErease(absolutePath: String) {
        println("${absolutePath} deleted")
    }

}
