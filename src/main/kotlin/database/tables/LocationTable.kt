package database.tables

import org.jetbrains.exposed.dao.IntIdTable

private const val TABLE_NAME = "location"
private const val COL_NAME = "name"

object LocationTable : IntIdTable(TABLE_NAME) {
    val name = varchar(COL_NAME, 100)
}