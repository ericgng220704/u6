package eric.u6.firebase

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.firestore.FirebaseFirestore


class TodoViewModel(application: Application) : AndroidViewModel(application){
    private val db = FirebaseFirestore.getInstance()
    var taskList = mutableStateOf<List<Task>>(listOf())
    var taskText = mutableStateOf("")

    init{
        loadTask()
    }

    private fun loadTask(){
        db.collection("tasks").addSnapshotListener{snapshot, e ->
            if(e != null || snapshot == null){
                return@addSnapshotListener
            }
            val tasks = snapshot.documents.mapNotNull {
                it.toObject(Task::class.java)
            }

            taskList.value = tasks
        }
    }

    fun addTask() {
        if (taskText.value != "") {
            val newTask = Task(name = taskText.value)
            db.collection("tasks").document(newTask.id).set(newTask)
            taskText.value = ""
        }
    }

    fun toggleTaskCompletion(task: Task) {
        val updatedTask = task.copy(completed = !task.completed)
        db.collection("tasks").document(task.id).set(updatedTask)
    }

    fun deleteTask(task: Task) {
        db.collection("tasks").document(task.id).delete()
    }

}

