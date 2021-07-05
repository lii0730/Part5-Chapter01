package com.example.aop_part5_chapter01.DI

import com.example.aop_part5_chapter01.data.Repository.TestTodoRepository
import com.example.aop_part5_chapter01.data.Repository.TodoRepository
import com.example.aop_part5_chapter01.domain.todo.GetTodoListUseCase
import com.example.aop_part5_chapter01.domain.todo.InsertTodoListUseCase
import com.example.aop_part5_chapter01.presentation.list.ListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appTestModule = module {

    //viewModel
    viewModel {
        ListViewModel(get())
    }

    // UseCase
    factory { GetTodoListUseCase(get()) }
    factory { InsertTodoListUseCase(get()) }

    // Repository
    single<TodoRepository> {
        TestTodoRepository()
    }
}