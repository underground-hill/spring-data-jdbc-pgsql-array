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
        val items = listOf("first", "second")
        val entity = TestEntity(items = items)
        val persistedEntity = repository.save(entity)

        // when
        val retrievedEntity = repository.findById(persistedEntity.id!!).orElseThrow()

        // then
        Assertions.assertEquals(2, retrievedEntity.items.size)
        Assertions.assertEquals("first", retrievedEntity.items[0])
        Assertions.assertEquals("second", retrievedEntity.items[1])
    }

    @Test
    fun `should return correct items when fetching by using custom query`() {
        // given
        val items = listOf("first", "second")
        val entity = TestEntity(items = items)
        val persistedEntity = repository.save(entity)

        // when
        val retrievedItemCombinations = repository.findDistinctItemCombinations()

        // then
        Assertions.assertAll(
            Executable { Assertions.assertEquals(1, retrievedItemCombinations.size) },
            Executable { Assertions.assertEquals("first", retrievedItemCombinations.first()[0]) },
            Executable { Assertions.assertEquals("second", retrievedItemCombinations.first()[1]) }
        )
    }
}
