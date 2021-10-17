package hu.fuz.duplicationchecker

import java.io.File

class DuplicationCheckerExecutor(
    val directories: Array<File>,
    val comparationStrategy: ComparationStrategy,
    val isSingleFilesPrint: Boolean,
    val isDuplicatedFilesPrint: Boolean,
    val ereaseFromDirectory: File?,
    val ereaseFromDirectoryDryRun: Boolean
) {

    val resultPrinter = ResultPrinter(isSingleFilesPrint, isDuplicatedFilesPrint)

    fun execute() {
        val checker = DuplicationChecker()
        /**
         * named arguments, pl.: 2 boolean paraméter esetén könnyű összekeverni
         * https://kotlinlang.org/docs/functions.html#named-arguments
         */
        checker.collectDuplications(
            directoryPath = getFilePaths(directories),
            isCompareFileContent = isCompareFileContent(comparationStrategy),
            isCompareFileName = isCompareFileName(comparationStrategy),
        )
        resultPrinter.printResult(checker.singleFiles, checker.duplications)

        deleteFiles(checker, resultPrinter)
    }

    private fun deleteFiles(
        checker: DuplicationChecker,
        resultPrinter: ResultPrinter
    ) {
        if (ereaseFromDirectory != null) {
            val collector = FileForDeleteCollector(checker.duplications, ereaseFromDirectory)
            collector.calculateFilesForErease()
            val filesForErease = collector.filesForErease
            /** when expression
             * https://kotlinlang.org/docs/control-flow.html#when-expression
             */
            when (ereaseFromDirectoryDryRun) {
                true -> resultPrinter.printEreaseDryRun(filesForErease.map { "rm ${it.path}" })
                false -> deleteFiles(filesForErease)
            }
        }
    }

    private fun deleteFiles(filesForErease: List<File>) {
        filesForErease.forEach {
            it.delete()
            resultPrinter.printFileAfterErease(it.absolutePath)
        }
    }

    private fun isCompareFileName(strategy: ComparationStrategy) =
        (strategy == ComparationStrategy.FILE_NAME_AND_CONTENT
                || strategy == ComparationStrategy.FILE_NAME_ONLY)

    private fun isCompareFileContent(strategy: ComparationStrategy) =
        (strategy == ComparationStrategy.FILE_NAME_AND_CONTENT
                || strategy == ComparationStrategy.FILE_CONTENT_ONLY)

    private fun getFilePaths(dirs: Array<File>) = dirs.map { d -> d.path }.toTypedArray()
}
