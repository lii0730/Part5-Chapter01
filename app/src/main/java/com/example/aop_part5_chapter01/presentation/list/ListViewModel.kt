package com.example.aop_part5_chapter01.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aop_part5_chapter01.data.Entity.TodoEntity
import com.example.aop_part5_chapter01.domain.todo.DeleteAllTodoItemUseCase
import com.example.aop_part5_chapter01.domain.todo.GetTodoListUseCase
import com.example.aop_part5_chapter01.domain.todo.InsertTodoListUseCase
import com.example.aop_part5_chapter01.domain.todo.UpdateTodoUseCase
import com.example.aop_part5_chapter01.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


/**
 * 필요한 UseCase
 * 1. [GetTodoListUseCase]
 * 2. [UpdateTodoUseCase]
 * 3. [DeleteAllTodoItemUseCase]
 */
internal class ListViewModel(
    private val getTodoListUseCase: GetTodoListUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val deleteAllTodoItemUseCase: DeleteAllTodoItemUseCase
) : BaseViewModel(){

    private var _toDoListLiveData = MutableLiveData<ToDoListState>(ToDoListState.UnInitialized)
    val todoListLiveData :LiveData<ToDoListState> = _toDoListLiveData

    override fun fetchData(): Job = viewModelScope.launch {
        _toDoListLiveData.postValue(ToDoListState.Loading)
        _toDoListLiveData.postValue(ToDoListState.Suceess(getTodoListUseCase()))
    }

    fun updateEntity(todoEntity: TodoEntity) = viewModelScope.launch {
        updateTodoUseCase(todoEntity)
    }

    fun deleteAll() = viewModelScope.launch {
        _toDoListLiveData.postValue(ToDoListState.Loading)
        deleteAllTodoItemUseCase()
        _toDoListLiveData.postValue(ToDoListState.Suceess(getTodoListUseCase()))
    }

}