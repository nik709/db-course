package generalui

import common.*
import database.CandidatesDatabase
import database.daos.*
import javafx.beans.property.*
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.event.ActionEvent
import javafx.event.EventHandler
import org.jetbrains.exposed.sql.transactions.transaction

class CandidatesViewModel {

    val firstNameProp = SimpleStringProperty().apply { value = ENTER_FIRST_NAME }
    val lastNameProp = SimpleStringProperty().apply { value = ENTER_LAST_NAME }
    val onAddPressedProperty = SimpleObjectProperty<EventHandler<ActionEvent>>().apply { value = onAddPressed() }
    val candidatesList = SimpleObjectProperty<ObservableList<CandidateEntity>>().apply { value = emptyFXCollection() }
    val selectedAgeProp = SimpleObjectProperty<Int>().apply { set(18) }
    val prevJobProp = SimpleStringProperty().apply { value = ENTER_PREVIOUS_JOB }
    val expectedSalaryProp = SimpleStringProperty().apply { value = ENTER_EXPECTED_SALARY }
    val expectedJobRoleItemsProp = SimpleObjectProperty<ObservableList<JobRoleEntity>>()
    val expectedJobRoleValueProp = SimpleObjectProperty<JobRoleEntity>()
    val expectedJobRoleShowing = SimpleBooleanProperty()
    val locationItemsProp = SimpleObjectProperty<ObservableList<LocationEntity>>()
    val locationValueProp = SimpleObjectProperty<LocationEntity>()

    var selectedCandidate: CandidateEntity? = null

    init {
        CandidatesDatabase.getInstance().executeInTransaction {
            expectedJobRoleItemsProp.value =
                FXCollections.observableArrayList(JobRoleEntity.all().toList())
            locationItemsProp.value =
                FXCollections.observableArrayList(LocationEntity.all().toList())
        }
        CandidatesDatabase.getInstance().registerListener(object : CandidatesDatabase.OnTransactionCommittedListener {
            override fun onDataBaseChanged() {
                reload()
            }
        })
    }

    private fun onAddPressed(): EventHandler<ActionEvent> {
        return EventHandler {
            CandidatesDatabase.getInstance().executeInTransaction {
                CandidateEntity.new {
                    firstName = firstNameProp.value
                    lastName = lastNameProp.value
                    age = selectedAgeProp.value.toShort()
                    previousJob = prevJobProp.value
                    expectedSalary = expectedSalaryProp.value.toFloat()
                    expectedJobRole = expectedJobRoleValueProp.value
                    location = locationValueProp.value
                }
            }
        }
    }

    private fun reload() {
        candidatesList.value.clear()

        transaction {
            candidatesList.value.addAll(CandidateEntity.all().toList())
            if (!expectedJobRoleShowing.value) {
                val expected = expectedJobRoleValueProp.value
                expectedJobRoleItemsProp.value.clear()
                expectedJobRoleItemsProp.value.addAll(JobRoleEntity.all().toList())
                expectedJobRoleValueProp.value = expected
            }

            return@transaction
        }
    }

    fun deleteCandidate() {
        selectedCandidate?.run {
            CandidatesDatabase.getInstance().executeInTransaction { delete() }
        }
    }

    fun updateCandidate() {
        selectedCandidate?.run {
            CandidatesDatabase.getInstance().executeInTransaction {
                firstName = firstNameProp.value
                lastName = lastNameProp.value
                age = selectedAgeProp.value.toShort()
                previousJob = prevJobProp.value
                expectedSalary = expectedSalaryProp.value.toFloat()
                expectedJobRole = expectedJobRoleValueProp.value
                location = null
            }
        }
    }
}