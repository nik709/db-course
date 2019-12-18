package generalui

import database.daos.CandidateEntity
import javafx.scene.control.*
import javafx.util.Callback
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.sql.transactions.transaction

class ListCellFactory<CellType : IntEntity?>(
    private val onCellClicked: ((ListCell<CellType>) -> Unit)? = null
) : Callback<ListView<CellType>, ListCell<CellType>> {

    override fun call(param: ListView<CellType>?): ListCell<CellType> {
        return createListCell().apply<ListCell<CellType>> {
            setOnMouseClicked {
                onCellClicked?.invoke(this)
            }
        }
    }

    private fun createListCell() = object : ListCell<CellType>() {
        override fun updateItem(item: CellType, empty: Boolean) {
            super.updateItem(item, empty)
            item ?: return
            transaction {
                when (item) {
                    is CandidateEntity -> {
                        text = "${item.firstName} ${item.lastName}"
                        tooltip = Tooltip(getTooltipMessage(item))
                    }
                }
            }
        }
    }

    private fun getTooltipMessage(item: CandidateEntity) =
        "ID: ${item.id.value}" +
                "\nAge: ${item.age}" +
                "\nPrevious job: ${item.previousJob}" +
                "\nExpected salary: ${item.expectedSalary}" +
                "\nExpected position: ${item.expectedJobRole?.name}" +
                "\nLocation: ${item.location?.name}"
}