package database.daos

import database.tables.CandidateTable
import database.tables.JobRoleTable
import org.jetbrains.exposed.dao.*

class CandidateEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<CandidateEntity>(CandidateTable)

    var firstName by CandidateTable.firstName
    var lastName by CandidateTable.lastName
    var age by CandidateTable.age
    var previousJob by CandidateTable.previousJob
    var expectedJobRole by JobRoleEntity optionalReferencedOn CandidateTable.expectedJobRole
    var expectedSalary by CandidateTable.expectedSalary
    var location by LocationEntity optionalReferencedOn CandidateTable.location
}


