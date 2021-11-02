package hu.fuz.duplicationchecker

import java.io.File

class FilesForEraseResult(
    var filesForErase: MutableList<File>,
    var duplicationsInEraseFromDirectory: MutableSet<File>
)
