package com.example.aop_part5_chapter01.data.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title : String,
    val description : String,
    val hasCompleted : Boolean = false
)
