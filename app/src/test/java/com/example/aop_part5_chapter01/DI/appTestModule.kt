package com.example.aop_part5_chapter01.DI

import com.example.aop_part5_chapter01.data.Repository.TestTodoRepository
import com.example.aop_part5_chapter01.data.Repository.TodoRepository
import com.example.aop_part5_chapter01.domain.todo.*
import com.example.aop_part5_chapter01.presentation.detail.DetailMode
import com.example.aop_part5_chapter01.presentation.detail.DetailViewModel
import com.example.aop_part5_chapter01.presentation.list.ListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appTestModule = module {

    //viewModel
    viewModel {
        ListViewModel(get(), get(), get())
    }
    viewModel { (detailMode: DetailMode, id: Long) ->
        DetailViewModel(
            detailMode, id, get(), get(), get(), get()
        )
    }

    // UseCase
    factory { GetTodoListUseCase(get()) }
    factory { InsertTodoListUseCase(get()) }
    factory { UpdateTodoUseCase(get()) }
    factory { GetTodoItemUseCase(get()) }
    factory { DeleteAllTodoItemUseCase(get()) }
    factory { InsertTodoItemUseCase(get()) }
    factory { DeleteTodoItemUseCase(get()) }

    // Repository
    single<TodoRepository> {
        TestTodoRepository()
    }
}