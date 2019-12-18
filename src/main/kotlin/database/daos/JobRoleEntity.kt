package database.daos

import database.tables.JobRoleTable
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class JobRoleEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<database.daos.JobRoleEntity>(JobRoleTable) {

        fun insertDefaults() {
            new {
                name = "Junior SE"
                salary = 25000f
                description = "No experience"
            }
            new {
                name = "SE"
                salary = 100000f
                description = "Experience > 1.5 years"
            }
            new {
                name = "Senior SE"
                salary = 300000f
                description = "Experience > 3 years"
            }
        }
    }

    var name by JobRoleTable.name
    var salary by JobRoleTable.salary
    var description by JobRoleTable.description


}