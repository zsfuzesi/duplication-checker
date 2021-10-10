package hu.fuz.duplicationchecker

class DirectoryNotFoundException(directoryPath: String) : RuntimeException(directoryPath) {
}
