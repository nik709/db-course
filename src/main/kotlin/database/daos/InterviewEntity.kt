package database.daos

import database.tables.InterviewTable
import org.jetbrains.exposed.dao.*

class InterviewEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<InterviewEntity>(InterviewTable)

    var typeId by InterviewTypeEntity optionalReferencedOn InterviewTable.typeId
    var candidateId by CandidateEntity referencedOn InterviewTable.candidateId
    var interviewerId by InterviewerEntity optionalReferencedOn InterviewTable.interviewerId
    var result by InterviewTable.result
}