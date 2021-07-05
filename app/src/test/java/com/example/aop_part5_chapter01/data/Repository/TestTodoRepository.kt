package com.example.aop_part5_chapter01.data.Repository

import com.example.aop_part5_chapter01.data.Entity.TodoEntity

class TestTodoRepository : TodoRepository {

    private val toDoList: MutableList<TodoEntity> = mutableListOf()

    override suspend fun getTodoList(): List<TodoEntity> {
        return toDoList
    }

    override suspend fun insertTodoList(todoList: List<TodoEntity>) {
        this.toDoList.addAll(todoList)
    }
}