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

    override suspend fun insertTodoItem(todoItem: TodoEntity) : Long {
        this.toDoList.add(todoItem)
        return todoItem.id
    }

    override suspend fun updateTodoItem(todoItem: TodoEntity) : Boolean {
        val foundTodoEntity = toDoList.find { it.id == todoItem.id }
        return if(foundTodoEntity == null) {
             false
        }
        else {
            this.toDoList[toDoList.indexOf(foundTodoEntity)] = todoItem
             true
        }
    }

    override suspend fun getTodoItem(itemId: Long): TodoEntity? {
        return toDoList.find { it.id == itemId }
    }

    override suspend fun deleteAll() {
        toDoList.clear()
    }

    override suspend fun deleteTodoItem(id: Long) : Boolean {
        val foundTodoEntity = toDoList.find { it.id == id }
        return if(foundTodoEntity == null) {
            false
        }
        else {
            this.toDoList.removeAt(toDoList.indexOf(foundTodoEntity))
            true
        }
    }
}