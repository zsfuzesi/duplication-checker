package hu.fuz.duplicationchecker

import java.io.File

class DuplicationCheckerResult(
    val singleFiles: MutableSet<File>,
    val duplicatedFiles: MutableSet<File>,
    val duplications: FileDuplicatinGroup,
)
