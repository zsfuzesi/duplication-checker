package hu.fuz.duplicationchecker

import org.junit.jupiter.api.Test
import kotlin.test.*

internal class FileForDeleteCollectorTest{

    @Test
    fun `collect file for erease`(){
        val files = listOf(listOf(
            a_alma,
            c_banan
        ))
        val filesForErease =
            FileForDeleteCollector(files, c_banan.parentFile)
                .calculateFilesForErease()
                .filesForErase

        assertTrue(filesForErease.isNotEmpty())
        assertEquals(1, filesForErease.size)
        assertEquals(c_banan, filesForErease[0])
    }

    @Test
    fun `not collect file for erease from erease from directory`(){
        val files = listOf(listOf(
            a_alma,
            a_korte
        ))
        val result = FileForDeleteCollector(files, a_alma.parentFile).calculateFilesForErease()
        assertTrue(result.filesForErase.isEmpty())
    }
}
