package hill.underground.springdatajdbcpgsqlarray

import org.springframework.data.annotation.Id

data class TestEntity(@field:Id val id: Int? = null, val items: List<String> = emptyList())
