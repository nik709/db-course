package database.tables

import common.VARCHAR_LENGTH
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

private const val TABLE_NAME = "candidate"
private const val COL_FIRST_NAME = "first_name"
private const val COL_LAST_NAME = "last_name"
private const val COL_AGE = "age"
private const val COL_PREVIOUS_JOB = "previous_job"
private const val COL_EXPECTED_ROLE_ID = "expected_role_id"
private const val COL_EXPECTED_SALARY = "expected_salary"
private const val REF_LOCATION = "location"

object CandidateTable : IntIdTable(TABLE_NAME) {
    val firstName = varchar(COL_FIRST_NAME, VARCHAR_LENGTH)
    val lastName = varchar(COL_LAST_NAME, VARCHAR_LENGTH)
    val age = short(COL_AGE)
    val previousJob = varchar(COL_PREVIOUS_JOB, VARCHAR_LENGTH)
    val expectedJobRole = reference(COL_EXPECTED_ROLE_ID, JobRoleTable, ReferenceOption.SET_NULL).nullable()
    val expectedSalary = float(COL_EXPECTED_SALARY)
    val location = reference(REF_LOCATION, LocationTable, ReferenceOption.SET_NULL).nullable()
}