package com.example.aop_part5_chapter01.data.Repository

import com.example.aop_part5_chapter01.data.Entity.TodoEntity

/**
 * 1. InsertTodoList
 * 2. GetTodoList
 */

interface TodoRepository {

    suspend fun getTodoList() : List<TodoEntity>

    suspend fun insertTodoList(todoList : List<TodoEntity>)

}