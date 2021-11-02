package hu.fuz.duplicationchecker

import java.io.File

class ResultPrinter(
    private val isSingleFilesPrint: Boolean,
    private val isDuplicatedFilesPrint: Boolean,) {

    fun printResult(singleFiles: Iterable<File>, duplications: FileDuplicatinGroup) {
        if(isSingleFilesPrint) {
            printSingleFiles(singleFiles)
        }

        if(isDuplicatedFilesPrint) {
            printDuplicatedFiles(duplications)
        }
    }

    private fun printDuplicatedFiles(duplications: FileDuplicatinGroup) {
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

    private fun printSingleFiles(singleFiles: Iterable<File>) {
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
        println("$absolutePath deleted")
    }

}
