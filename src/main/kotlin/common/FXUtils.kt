package common

import javafx.application.Platform
import javafx.collections.FXCollections
import kotlinx.coroutines.*

inline fun <reified T: Any> emptyFXCollection() = FXCollections.observableArrayList<T>()!!

fun launchCoroutineForFXThread(task: () -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        while (true) {
            Platform.runLater(task)
            delay(2000)
        }
    }
}