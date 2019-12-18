package generalui

import common.EMPTY
import database.CandidatesDatabase
import database.daos.JobRoleEntity
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.event.ActionEvent
import javafx.event.EventHandler
import org.jetbrains.exposed.sql.transactions.transaction

class JobRoleViewModel {

    val jobRoleNameProp = SimpleStringProperty().apply { value = EMPTY }
    val jobRoleDescriptionProp = SimpleStringProperty().apply { value = EMPTY }
    val jobRoleSalaryProp = SimpleStringProperty().apply { value = EMPTY }
    val jobRoleListProp =
        SimpleObjectProperty<ObservableList<String>>().apply { value = FXCollections.observableArrayList() }
    val onAddPressedProperty = SimpleObjectProperty<EventHandler<ActionEvent>>().apply { value = onAddPressed() }

    init {
        CandidatesDatabase.getInstance().registerListener(object : CandidatesDatabase.OnTransactionCommittedListener {
            override fun onDataBaseChanged() {
                reload()
            }
        })
    }

    private fun reload() {
        jobRoleListProp.value.clear()
        transaction {
            jobRoleListProp.value.addAll(JobRoleEntity.all().map { it.name })
            return@transaction
        }
    }

    private fun onAddPressed(): EventHandler<ActionEvent> {
        return EventHandler {
            CandidatesDatabase.getInstance().executeInTransaction {
                JobRoleEntity.new {
                    name = jobRoleNameProp.value
                    salary = jobRoleSalaryProp.value.toFloat()
                    description = jobRoleDescriptionProp.value
                }
            }
        }
    }
}