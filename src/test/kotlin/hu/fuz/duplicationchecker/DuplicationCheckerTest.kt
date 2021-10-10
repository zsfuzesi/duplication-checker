package hu.fuz.duplicationchecker

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal class DuplicationCheckerTest{

    private val test_gallery_path = "./src/test/resources/testgallery"
    private val a_alma = File("$test_gallery_path/a/alma.txt")
    private val b_alma = File("$test_gallery_path/b/alma.txt")
    private val a_b_banan = File("$test_gallery_path/a/b/banán.txt")
    private val c_banan = File("$test_gallery_path/c/banán.txt")
    private val a_korte = File("$test_gallery_path/a/körte.txt")

    lateinit var checker : DuplicationChecker

    @BeforeEach
    fun setUp(){
        checker = DuplicationChecker()
    }

    @Test
    fun `collect duplicated files`(){
        checker.collectDuplications(test_gallery_path)
        assertEquals(4,checker.duplicatedFiles.size)
        assertTrue(checker.duplicatedFiles.contains(a_alma))
        assertTrue(checker.duplicatedFiles.contains(b_alma))
        assertTrue(checker.duplicatedFiles.contains(a_b_banan))
        assertTrue(checker.duplicatedFiles.contains(c_banan))
    }

    @Test
    fun `collect duplications`(){
        checker.collectDuplications(test_gallery_path)
        assertEquals(2,checker.duplications.size)
        assertTrue(checker.duplications.contains(mutableListOf(b_alma, a_alma)))
        assertTrue(checker.duplications.contains(mutableListOf(c_banan, a_b_banan)))
    }

    @Test
    fun `collect not duplicated files`(){
        checker.collectDuplications(test_gallery_path)
        assertEquals(1,checker.singleFiles.size)
        assertNotNull(checker.singleFiles.contains(a_korte))
    }

    @Test
    fun `collect by more root path`(){
        checker.collectDuplications(
            "$test_gallery_path/a/b",
            "$test_gallery_path/c"
        )
        assertEquals(1,checker.duplications.size)
        assertTrue(checker.duplications.contains(mutableListOf(c_banan, a_b_banan)))
        assertEquals(2,checker.duplicatedFiles.size)
        assertEquals(0,checker.singleFiles.size)
    }

    @Test
    fun `compare by name and file content`(){
        checker.collectDuplications(
            test_gallery_path,
            compareFileContent = true)
        assertEquals(2,checker.duplicatedFiles.size)
        assertTrue(checker.duplicatedFiles.contains(a_b_banan))
        assertTrue(checker.duplicatedFiles.contains(c_banan))

        assertEquals(3,checker.singleFiles.size)
        assertNotNull(checker.singleFiles.contains(a_korte))
        assertNotNull(checker.singleFiles.contains(a_alma))
        assertNotNull(checker.singleFiles.contains(b_alma))
    }

    @Test
    fun `compare by file content only`(){
        checker.collectDuplications(
            test_gallery_path,
            compareFileName = false,
            compareFileContent = true)
        assertEquals(3,checker.duplicatedFiles.size)
        assertTrue(checker.duplicatedFiles.contains(a_b_banan))
        assertTrue(checker.duplicatedFiles.contains(c_banan))
        assertTrue(checker.duplicatedFiles.contains(b_alma))
        assertEquals(1,checker.duplications.size)

        assertEquals(2,checker.singleFiles.size)
        assertNotNull(checker.singleFiles.contains(a_alma))
        assertNotNull(checker.singleFiles.contains(a_korte))
    }

}
