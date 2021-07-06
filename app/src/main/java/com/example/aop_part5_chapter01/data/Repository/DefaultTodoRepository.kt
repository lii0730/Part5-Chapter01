package com.example.aop_part5_chapter01.data.Repository

import com.example.aop_part5_chapter01.data.Entity.TodoEntity
import com.example.aop_part5_chapter01.data.local.db.dao.ToDoDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultTodoRepository(
    private val todoDao: ToDoDao,
    private val ioDispatcher: CoroutineDispatcher
) : TodoRepository {
    override suspend fun getTodoList(): List<TodoEntity> = withContext(ioDispatcher) {
        todoDao.getAll()
    }

    override suspend fun insertTodoList(todoList: List<TodoEntity>) = withContext(ioDispatcher) {
        todoDao.insert(todoList)
    }

    override suspend fun insertTodoItem(todoItem: TodoEntity) : Long = withContext(ioDispatcher){
        todoDao.insert(todoItem)
    }

    override suspend fun updateTodoItem(todoItem: TodoEntity) : Boolean = withContext(ioDispatcher){
        todoDao.update(todoItem)
    }

    override suspend fun getTodoItem(itemId: Long): TodoEntity? = withContext(ioDispatcher){
        todoDao.getById(itemId)
    }

    override suspend fun deleteAll() = withContext(ioDispatcher){
        todoDao.deleteAll()
    }

    override suspend fun deleteTodoItem(id: Long) : Boolean = withContext(ioDispatcher){
        todoDao.delete(id)
    }
}