package hill.underground.springdatajdbcpgsqlarray

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository

interface TestEntityRepository : CrudRepository<TestEntity, Int> {

    @Query("SELECT DISTINCT items FROM test_entity WHERE array_length(items, 1) > 0")
    fun findDistinctItemCombinations() : Set<List<String>>
}
