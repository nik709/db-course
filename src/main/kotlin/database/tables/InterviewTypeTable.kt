package database.tables

import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.*

private const val TABLE_NAME = "interview_type"
private const val COL_TYPE_NAME = "name"

object InterviewTypeTable : IntIdTable(TABLE_NAME) {

    val name = varchar(COL_TYPE_NAME, 100).uniqueIndex()

    private val DEFAULT_TYPES = listOf(
        "Programming",
        "English",
        "Communication skills"
    )

    fun insertDefault() {
        if (selectAll().toList().isNotEmpty()) {
            return
        }
        DEFAULT_TYPES.forEach { type ->
            insert { it[name] = type }
        }
    }
}