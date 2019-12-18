package database.tables

import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

private const val TABLE_NAME = "interviewer"
private const val COL_FIRST_NAME = "first_name"
private const val COL_LAST_NAME = "last_name"
private const val REF_JOB_ROLE = "job_role"
private const val REF_LOCATION = "location"

object InterviewerTable : IntIdTable(TABLE_NAME) {
    val firstName = varchar(COL_FIRST_NAME, 100)
    val lastName = varchar(COL_LAST_NAME, 100)
    val jobRoleId = reference(REF_JOB_ROLE, JobRoleTable, ReferenceOption.SET_NULL).nullable()
    var location = reference(REF_LOCATION, LocationTable, ReferenceOption.SET_NULL).nullable()
}