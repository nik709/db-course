package database.daos

import database.tables.LocationTable
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class LocationEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<LocationEntity>(LocationTable) {
        fun insertDefaults() {
            if (all().toList().isEmpty()) {
                (1..10).forEach { new { name = "Location $it" } }
            }
        }
    }

    var name by LocationTable.name
}