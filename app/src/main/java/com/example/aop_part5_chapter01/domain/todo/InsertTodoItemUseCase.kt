package com.example.aop_part5_chapter01.domain.todo

import com.example.aop_part5_chapter01.data.Entity.TodoEntity
import com.example.aop_part5_chapter01.data.Repository.TodoRepository
import com.example.aop_part5_chapter01.domain.UseCase

internal class InsertTodoItemUseCase(
    private val todoRepository: TodoRepository
) : UseCase {

    suspend operator fun invoke(todoItem: TodoEntity) : Long {
        return todoRepository.insertTodoItem(todoItem)
    }
}