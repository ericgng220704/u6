package eric.u6.firebase

import java.util.UUID

data class Task (
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val completed: Boolean = false,
)