package hu.fuz.duplicationchecker

import java.io.File
import java.nio.file.Files
import java.nio.file.Path

val test_gallery_path = "./src/test/resources/testgallery"
val a_alma = File("$test_gallery_path/a/alma.txt")
val b_alma = File("$test_gallery_path/b/alma.txt")
val a_b_banan = File("$test_gallery_path/a/b/banán.txt")
val c_banan = File("$test_gallery_path/c/banán.txt")
val a_korte = File("$test_gallery_path/a/körte.txt")
