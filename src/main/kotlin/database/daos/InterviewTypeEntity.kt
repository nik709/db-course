package database.daos

import database.tables.InterviewTypeTable
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class InterviewTypeEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<InterviewTypeEntity>(InterviewTypeTable)

    var name by InterviewTypeTable.name
}