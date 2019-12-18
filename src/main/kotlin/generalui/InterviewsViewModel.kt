package generalui

import database.CandidatesDatabase
import database.daos.*
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import org.jetbrains.exposed.sql.transactions.transaction

class InterviewsViewModel {
    val interviewItemsProp = SimpleObjectProperty<ObservableList<InterviewEntity>>()
    val candidateValueProp = SimpleObjectProperty<CandidateEntity>()

    val interviewerItemsProp = SimpleObjectProperty<ObservableList<InterviewerEntity>>()
    val interviewerValueProp = SimpleObjectProperty<InterviewerEntity>()

    val typeItemsProp = SimpleObjectProperty<ObservableList<InterviewTypeEntity>>()
    val typeValueProp = SimpleObjectProperty<InterviewTypeEntity>()

    val markProp = SimpleObjectProperty<Int>()

    init {
        transaction {
            interviewItemsProp.value = FXCollections.observableArrayList(InterviewEntity.all().toList())
            interviewerItemsProp.value = FXCollections.observableArrayList(InterviewerEntity.all().toList())
            typeItemsProp.value = FXCollections.observableArrayList(InterviewTypeEntity.all().toList())
        }
        CandidatesDatabase.getInstance().registerListener(object : CandidatesDatabase.OnTransactionCommittedListener {
            override fun onDataBaseChanged() {
                reload()
            }
        })
    }

    private fun reload() {
        interviewItemsProp.value.clear()
        transaction {
            interviewItemsProp.value.addAll(InterviewEntity.all().toList())
        }
    }

    fun add() {
        CandidatesDatabase.getInstance().executeInTransaction {
            InterviewEntity.new {
                typeId = typeValueProp.value
                candidateId = candidateValueProp.value
                interviewerId = interviewerValueProp.value
                result = markProp.value
            }
        }
    }
}