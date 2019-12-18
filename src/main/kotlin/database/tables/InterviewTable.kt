package database.tables

import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

private const val TABLE_NAME = "interview"
private const val REF_TYPE = "type"
private const val REF_CANDIDATE = "candidate"
private const val REF_INTERVIEWER = "interviewer"
private const val COL_RESULT = "result"

object InterviewTable : IntIdTable(TABLE_NAME) {
    val typeId = reference(REF_TYPE, InterviewTypeTable, ReferenceOption.SET_NULL).nullable()
    
    val candidateId = reference(REF_CANDIDATE, CandidateTable, ReferenceOption.CASCADE)
    val interviewerId = reference(REF_INTERVIEWER, InterviewerTable, ReferenceOption.SET_NULL).nullable()
    val result = integer(COL_RESULT)
}