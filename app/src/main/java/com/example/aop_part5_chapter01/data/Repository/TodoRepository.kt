package com.example.aop_part5_chapter01.data.Repository

import com.example.aop_part5_chapter01.data.Entity.TodoEntity

/**
 * 1. InsertTodoList
 * 2. GetTodoList
 * 3. UpdateTodoItem
 */

interface TodoRepository {

    suspend fun getTodoList() : List<TodoEntity>

    suspend fun insertTodoList(todoList : List<TodoEntity>)

    suspend fun insertTodoItem(todoItem : TodoEntity) : Long

    suspend fun updateTodoItem(todoItem: TodoEntity)

    suspend fun getTodoItem(itemId: Long) : TodoEntity?

    suspend fun deleteAll()

    suspend fun deleteTodoItem(id: Long)
}