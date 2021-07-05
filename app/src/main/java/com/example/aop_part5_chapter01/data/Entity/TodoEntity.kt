package com.example.aop_part5_chapter01.data.Entity

data class TodoEntity(
    val id: Long = 0,
    val title : String,
    val description : String,
    val hasCompleted : Boolean = false
)
