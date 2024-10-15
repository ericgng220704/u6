package eric.u6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import eric.u6.firebase.Task
import eric.u6.firebase.TodoViewModel
import eric.u6.ui.theme.U6Theme
import androidx.compose.foundation.lazy.items


@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: TodoViewModel = ViewModelProvider(this).get(TodoViewModel::class.java)

        setContent {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("NoteKeeper 2.0") },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color(0xFF6200EE),
                            titleContentColor = Color.White
                        )
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(onClick = { viewModel.addTask() }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Task")
                    }
                }
            ) { paddingValues -> // Scaffold's content padding
                Column(
                    modifier = Modifier
                        .padding(paddingValues) // Apply Scaffold's padding here
                        .padding(16.dp) // Apply additional padding
                        .fillMaxSize()
                ) {
                    // Task Input
                    TextField(
                        value = viewModel.taskText.value,
                        onValueChange = { viewModel.taskText.value = it },
                        label = { Text("Enter Task") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Task List
                    LazyColumn {
                        items(viewModel.taskList.value) { task ->
                            TodoItem(task, viewModel)
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun TodoItem(task: Task, viewModel: TodoViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = task.completed,
            onCheckedChange = { viewModel.toggleTaskCompletion(task) }
        )
        Text(
            text = task.name,
            modifier = Modifier.weight(1f),
            style = TextStyle(
                fontSize = 18.sp,
                color = if (task.completed) Color.Gray else Color.Black,
                textDecoration = if (task.completed) TextDecoration.LineThrough else TextDecoration.None
            )
        )
        IconButton(onClick = { viewModel.deleteTask(task) }) {
            Icon(Icons.Default.Delete, contentDescription = "Delete Task")
        }
    }
}