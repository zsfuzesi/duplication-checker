package hu.fuz.duplicationchecker

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

private class DuplicationCheckerTest {

    lateinit var checker: DuplicationChecker

    @BeforeEach
    fun setUp() {
        checker = DuplicationChecker()
    }

    @Test
    fun `collect duplicated files`() {
        val result = checker.collectDuplicationsAndSingleFiles(test_gallery_path)
        with(result.duplicatedFiles) {
            assertEquals(4, size)
            assertTrue(a_alma in this)
            assertTrue(b_alma in this)
            assertTrue(a_b_banan in this)
            assertTrue(c_banan in this)
        }
    }

    @Test
    fun `collect duplications`() {
        val result = checker.collectDuplicationsAndSingleFiles(test_gallery_path)
        with(result.duplications) {
            assertEquals(2, size)
            assertTrue(mutableListOf(b_alma, a_alma) in this)
            assertTrue(mutableListOf(c_banan, a_b_banan) in this)
        }
    }

    @Test
    fun `collect not duplicated files`() {
        val result = checker.collectDuplicationsAndSingleFiles(test_gallery_path)
        with(result.singleFiles) {
            assertEquals(1, size)
            assertContains(this, a_korte)
        }
    }

    @Test
    fun `collect by more root path`() {
        val result = checker.collectDuplicationsAndSingleFiles(
            "$test_gallery_path/a/b",
            "$test_gallery_path/c"
        )
        assertEquals(1, result.duplications.size)
        assertTrue(result.duplications.contains(mutableListOf(c_banan, a_b_banan)))
        assertEquals(2, result.duplicatedFiles.size)
        assertEquals(0, result.singleFiles.size)
    }

    @Test
    fun `compare by name and file content`() {
        val result = checker.collectDuplicationsAndSingleFiles(
            test_gallery_path,
            compareByFileContent = true
        )

        with(result.duplicatedFiles) {
            assertEquals(2, size)
            assertTrue(contains(a_b_banan))
            assertTrue(contains(c_banan))
        }

        with(result.singleFiles) {
            assertEquals(3, size)
            assertNotNull(contains(a_korte))
            assertNotNull(contains(a_alma))
            assertNotNull(contains(b_alma))
        }

    }

    @Test
    fun `compare by file content only`() {
        val result = checker.collectDuplicationsAndSingleFiles(
            test_gallery_path,
            compareByFileName = false,
            compareByFileContent = true
        )

        with(result.duplicatedFiles) {
            assertEquals(3, size)
            assertTrue(contains(a_b_banan))
            assertTrue(contains(c_banan))
            assertTrue(contains(b_alma))
        }

        assertEquals(1, result.duplications.size)

        with(result.singleFiles) {
            assertEquals(2, size)
            assertNotNull(contains(a_alma))
            assertNotNull(contains(a_korte))
        }
    }

}
