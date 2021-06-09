package hill.underground.springdatajdbcpgsqlarray

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TestEntityRepositoryTest {

    @Autowired
    private lateinit var repository: TestEntityRepository

    @Test
    fun `should return correct items when fetching by id`() {
        // given
        val items = listOf("first", "second", "third")
        val entity = TestEntity(items = items)
        val persistedEntity = repository.save(entity)

        // when
        val retrievedEntity = repository.findById(persistedEntity.id!!).orElseThrow()

        // then
        Assertions.assertEquals(3, retrievedEntity.items.size)
        Assertions.assertEquals("first", retrievedEntity.items[0])
        Assertions.assertEquals("second", retrievedEntity.items[1])
        Assertions.assertEquals("third", retrievedEntity.items[2])
    }

    @Test
    fun `should return correct items when fetching by using custom query`() {
        // given
        val items = listOf("first", "second", "third")
        val entity = TestEntity(items = items)
        repository.save(entity)

        // when
        val retrievedItemCombinations = repository.findDistinctItemCombinations()

        // then
        Assertions.assertAll(
            Executable { Assertions.assertEquals(1, retrievedItemCombinations.size) },
            Executable { Assertions.assertEquals(3, retrievedItemCombinations.first().size) },
            Executable { Assertions.assertEquals("first", retrievedItemCombinations.first()[0]) },  // is "{first"
            Executable { Assertions.assertEquals("second", retrievedItemCombinations.first()[1]) },
            Executable { Assertions.assertEquals("third", retrievedItemCombinations.first()[2]) }   // is "third}"
        )
    }
}
