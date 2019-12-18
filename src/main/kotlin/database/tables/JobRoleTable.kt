package database.tables

import org.jetbrains.exposed.dao.IntIdTable

private const val TABLE_NAME = "job_role"
private const val COL_NAME = "name"
private const val COL_DESCRIPTION = "description"
private const val COL_SALARY = "salary"

object JobRoleTable: IntIdTable(TABLE_NAME) {
    val name = varchar(COL_NAME, 100)
    val description = varchar(COL_DESCRIPTION, 4000)
    val salary = float(COL_SALARY)
}