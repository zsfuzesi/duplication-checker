package hu.fuz.duplicationchecker

import picocli.CommandLine.Option
import java.io.File
import java.util.concurrent.Callable

class DuplicationCheckerCli(

    @Option(names = ["--directories"], required = true, description = ["directories of files"])
    val directories: List<File> = mutableListOf(),

    @Option(names = ["--erease-from"], description = ["erease duplications from here"])
    val isEreaseFromDirectory: File? = null,

    @Option(names = ["--erease-from-dry-run"], description = ["create remove script"])
    val isEreaseFromDirectoryDryRun: Boolean = false,

    @Option(names = ["--print-single-files"], description = ["print not duplicated files"])
    val isPrintSingleFiles: Boolean = false,

    @Option(names = ["--print-duplication"], description = ["print duplicated files"])
    var isPrintDuplicatedFiles: Boolean = false,

    @Option(names = ["--comparation-strategy"], description = ["\${COMPLETION-CANDIDATES}"])
    var comparationStrategy: ComparationStrategy = ComparationStrategy.FILE_NAME_ONLY
) : Callable<Int> {

    override fun call(): Int {
        try {
            val executor = DuplicationCheckerExecutor(
                directories,
                comparationStrategy,
                isPrintSingleFiles,
                isPrintDuplicatedFiles,
                isEreaseFromDirectory,
                isEreaseFromDirectoryDryRun
            )
            executor.execute()
            return 0
        } catch (exception: Exception) {
            exception.printStackTrace()
            return -1
        }
    }
}
