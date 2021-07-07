package com.example.aop_part5_chapter01.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.aop_part5_chapter01.data.Entity.TodoEntity
import com.example.aop_part5_chapter01.domain.todo.DeleteTodoItemUseCase
import com.example.aop_part5_chapter01.domain.todo.GetTodoItemUseCase
import com.example.aop_part5_chapter01.domain.todo.InsertTodoItemUseCase
import com.example.aop_part5_chapter01.domain.todo.UpdateTodoUseCase
import com.example.aop_part5_chapter01.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class DetailViewModel(
    var detailMode: DetailMode,
    var id: Long = -1,
    private val getTodoItemUseCase: GetTodoItemUseCase,
    private val deleteTodoItemUseCase: DeleteTodoItemUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val insertTodoItemUseCase: InsertTodoItemUseCase
) : BaseViewModel() {

    private var _toDoDetailLiveData = MutableLiveData<ToDoDetailState>(ToDoDetailState.UnInitialized)
    val todoDetailLiveData : LiveData<ToDoDetailState> = _toDoDetailLiveData

    override fun fetchData(): Job = viewModelScope.launch{
        when(detailMode) {
            DetailMode.WRITE -> {
                //TODO: 나중에 작성 모드로 상세화면 진입 로직 처리
                _toDoDetailLiveData.postValue(ToDoDetailState.Write)
            }
            DetailMode.DETAIL -> {
                _toDoDetailLiveData.postValue(ToDoDetailState.Loading)
                try {
                    getTodoItemUseCase(id)?.let{
                        _toDoDetailLiveData.postValue(ToDoDetailState.Success(it))
                    } ?: kotlin.run {
                        _toDoDetailLiveData.postValue(ToDoDetailState.Error)
                    }
                } catch(e: Exception) {
                    e.printStackTrace()
                    _toDoDetailLiveData.postValue(ToDoDetailState.Error)
                }
            }
        }
    }

    fun deleteTodo() = viewModelScope.launch{
        _toDoDetailLiveData.postValue(ToDoDetailState.Loading)
        try{
            deleteTodoItemUseCase(id)
            _toDoDetailLiveData.postValue(ToDoDetailState.Delete)
        } catch (e: Exception) {
            e.printStackTrace()
            _toDoDetailLiveData.postValue(ToDoDetailState.Error)
        }
    }

    fun setModifyMode() = viewModelScope.launch {
        _toDoDetailLiveData.postValue(ToDoDetailState.Modify)
    }

    fun writeTodo(title: String, description: String) = viewModelScope.launch {
        _toDoDetailLiveData.postValue(ToDoDetailState.Loading)
        when(detailMode) {
            DetailMode.WRITE -> {
                try {
                    val todoEntity = TodoEntity(
                        title = title,
                        description = description
                    )
                    id = insertTodoItemUseCase(todoEntity)
                    _toDoDetailLiveData.postValue(ToDoDetailState.Success(todoEntity))
                    detailMode = DetailMode.DETAIL
                } catch(e: Exception) {
                    _toDoDetailLiveData.postValue(ToDoDetailState.Error)
                }
            }
            DetailMode.DETAIL -> {
                _toDoDetailLiveData.postValue(ToDoDetailState.Loading)
                try {
                    getTodoItemUseCase(id)?.let {
                        val updateTodoEntity = it.copy(
                            title = title,
                            description = description
                        )
                        updateTodoUseCase(updateTodoEntity)
                        _toDoDetailLiveData.postValue(ToDoDetailState.Success(updateTodoEntity))
                    } ?: kotlin.run {
                        _toDoDetailLiveData.postValue(ToDoDetailState.Error)
                    }
                } catch(e: Exception) {
                    e.printStackTrace()
                    _toDoDetailLiveData.postValue(ToDoDetailState.Error)
                }
            }
        }
    }
}