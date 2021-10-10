package hu.fuz.duplicationchecker

import picocli.CommandLine.*
import java.io.File
import java.util.concurrent.Callable

class DuplicationCheckerCli : Callable<Int> {

    @Option(names = ["--directories"], required = true,  description = ["directories of files"])
    lateinit var directories : Array<File>

    @Option(names = ["--erease-from"], description = ["erease duplications from here"])
    var ereaseFromDirectory : File? = null

    @Option(names = ["--erease-from-dry-run"], description = ["create remove script"])
    var ereaseFromDirectoryDryRun : Boolean = false

    @Option(names = ["--compare-strategy"], description = ["\${COMPLETION-CANDIDATES}"])
    var comparationStrategy : ComparationStrategy = ComparationStrategy.FILE_NAME_ONLY

    override fun call(): Int {
        val executor = DuplicationCheckerExecutor(
            directories,
            comparationStrategy,
            ereaseFromDirectory,
            ereaseFromDirectoryDryRun
        )
        executor.execute()

        return 0
    }
}
