package generalui

import database.CandidatesDatabase
import javafx.application.Application
import javafx.fxml.FXMLLoader.load
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Main : Application() {

    companion object {
        private const val LAYOUT = "Candidates.fxml"

        @JvmStatic
        fun main(vararg args: String) = runBlocking {
            launch { print(CandidatesDatabase.getInstance().dbVersion) }
            launch { launch(Main::class.java) }
            return@runBlocking
        }
    }

    override fun start(primaryStage: Stage?) {
        System.setProperty("prism.lcdtext", "false")
        primaryStage?.scene = Scene(load<Parent?>(Main::class.java.getResource(LAYOUT)))
        primaryStage?.show()
    }
}
