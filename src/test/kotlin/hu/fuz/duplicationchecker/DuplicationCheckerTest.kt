package hu.fuz.duplicationchecker

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal class DuplicationCheckerTest{

    lateinit var checker : DuplicationChecker

    @BeforeEach
    fun setUp(){
        checker = DuplicationChecker()
    }

    @Test
    fun `collect duplicated files`(){
        checker.collectDuplicationsAndSingleFiles(test_gallery_path)
        assertEquals(4,checker.duplicatedFiles.size)
        assertTrue(checker.duplicatedFiles.contains(a_alma))
        assertTrue(checker.duplicatedFiles.contains(b_alma))
        assertTrue(checker.duplicatedFiles.contains(a_b_banan))
        assertTrue(checker.duplicatedFiles.contains(c_banan))
    }

    @Test
    fun `collect duplications`(){
        checker.collectDuplicationsAndSingleFiles(test_gallery_path)
        assertEquals(2,checker.duplications.size)
        assertTrue(checker.duplications.contains(mutableListOf(b_alma, a_alma)))
        assertTrue(checker.duplications.contains(mutableListOf(c_banan, a_b_banan)))
    }

    @Test
    fun `collect not duplicated files`(){
        checker.collectDuplicationsAndSingleFiles(test_gallery_path)
        assertEquals(1,checker.singleFiles.size)
        assertNotNull(checker.singleFiles.contains(a_korte))
    }

    @Test
    fun `collect by more root path`(){
        checker.collectDuplicationsAndSingleFiles(
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
        checker.collectDuplicationsAndSingleFiles(
            test_gallery_path,
            compareByFileContent = true)
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
        checker.collectDuplicationsAndSingleFiles(
            test_gallery_path,
            compareByFileName = false,
            compareByFileContent = true)
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
