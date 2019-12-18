package database.daos

import database.tables.InterviewerTable
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class InterviewerEntity(id: EntityID<Int>): IntEntity(id) {

    companion object : IntEntityClass<InterviewerEntity>(InterviewerTable) {
        fun insertDefaults() {
            if (all().empty()) {
                (1..10).forEach {
                    new {
                        firstName = "Interviewer"
                        lastName = "$it"
                    }
                }
            }
        }
    }

    var firstName by InterviewerTable.firstName
    var lastName by InterviewerTable.lastName
    var jobRole by JobRoleEntity optionalReferencedOn InterviewerTable.jobRoleId
    var location by LocationEntity optionalReferencedOn InterviewerTable.location
}