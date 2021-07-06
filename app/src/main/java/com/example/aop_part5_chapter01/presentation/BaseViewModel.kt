package com.example.aop_part5_chapter01.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aop_part5_chapter01.domain.todo.GetTodoItemUseCase
import com.example.aop_part5_chapter01.presentation.detail.DetailMode
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal abstract class BaseViewModel: ViewModel() {

    abstract fun fetchData(): Job
}