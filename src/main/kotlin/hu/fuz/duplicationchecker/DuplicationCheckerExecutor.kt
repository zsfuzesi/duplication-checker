package hu.fuz.duplicationchecker

import java.io.File

class DuplicationCheckerExecutor(
    val directories: Array<File>,
    val comparationStrategy: ComparationStrategy,
    val ereaseFromDirectory: File?,
    val ereaseFromDirectoryDryRun: Boolean
) {
    val checker = DuplicationChecker()

    fun execute() {
        checker.collectDuplications(
            directoryPath = directories.map { d -> d.path }.toTypedArray(),
            compareFileContent = comparationStrategy == ComparationStrategy.FILE_NAME_AND_CONTENT
                    || comparationStrategy == ComparationStrategy.FILE_CONTENT_ONLY,
            compareFileName = comparationStrategy == ComparationStrategy.FILE_NAME_AND_CONTENT
                    || comparationStrategy == ComparationStrategy.FILE_NAME_ONLY,
        )

        println("""
        
        Single files:""".trimIndent())
        checker.singleFiles.forEach{
            println(it)
        }

        println("""
        
        Duplicated files:""".trimIndent())
        checker.duplications.forEach{
            println("---")
            it.forEach{
                println(it)
            }
        }
    }
}
