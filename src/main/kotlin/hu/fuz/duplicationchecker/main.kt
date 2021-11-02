import hu.fuz.duplicationchecker.DuplicationCheckerCli
import picocli.CommandLine

fun main(args: Array<String>) {
    val cli = CommandLine(DuplicationCheckerCli())
    cli.execute(*args)
}


