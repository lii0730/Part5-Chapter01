package com.example.aop_part5_chapter01.data.Repository

import com.example.aop_part5_chapter01.data.Entity.TodoEntity

class DefaultTodoRepository : TodoRepository {
    override suspend fun getTodoList(): List<TodoEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun insertTodoList(todoList: List<TodoEntity>) {
        TODO("Not yet implemented")
    }

}