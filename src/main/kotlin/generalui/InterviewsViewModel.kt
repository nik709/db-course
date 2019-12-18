package generalui

import database.CandidatesDatabase
import database.daos.InterviewEntity
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import org.jetbrains.exposed.sql.transactions.transaction

class InterviewsViewModel {
    val interviewItemsProp = SimpleObjectProperty<ObservableList<InterviewEntity>>()

    init {
        transaction {
            interviewItemsProp.value = FXCollections.observableArrayList(InterviewEntity.all().toList())
        }
        CandidatesDatabase.getInstance().registerListener(object : CandidatesDatabase.OnTransactionCommittedListener {
            override fun onDataBaseChanged() {
                reload()
            }
        })
    }

    private fun reload() {

    }
}