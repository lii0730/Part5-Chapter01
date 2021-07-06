package com.example.aop_part5_chapter01.DI

import android.content.Context
import androidx.room.Room
import com.example.aop_part5_chapter01.data.Repository.DefaultTodoRepository
import com.example.aop_part5_chapter01.data.Repository.TodoRepository
import com.example.aop_part5_chapter01.data.local.db.ToDoDatabase
import com.example.aop_part5_chapter01.data.local.db.ToDoDatabase.Companion.DB_NAME
import com.example.aop_part5_chapter01.domain.todo.*
import com.example.aop_part5_chapter01.domain.todo.DeleteAllTodoItemUseCase
import com.example.aop_part5_chapter01.domain.todo.DeleteTodoItemUseCase
import com.example.aop_part5_chapter01.domain.todo.GetTodoItemUseCase
import com.example.aop_part5_chapter01.domain.todo.GetTodoListUseCase
import com.example.aop_part5_chapter01.domain.todo.InsertTodoItemUseCase
import com.example.aop_part5_chapter01.domain.todo.InsertTodoListUseCase
import com.example.aop_part5_chapter01.domain.todo.UpdateTodoUseCase
import com.example.aop_part5_chapter01.presentation.detail.DetailMode
import com.example.aop_part5_chapter01.presentation.detail.DetailViewModel
import com.example.aop_part5_chapter01.presentation.list.ListViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appModule = module {
    //viewModel
    viewModel {
        ListViewModel(get(), get(), get())
    }
    viewModel { (detailMode: DetailMode, id: Long) ->
        DetailViewModel(
            detailMode, id, get(), get(), get(), get()
        )
    }
    single { Dispatchers.IO }
    single { Dispatchers.Main }

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
        DefaultTodoRepository(get(), get())
    }
    single { provideDB(androidApplication()) }
    single { provideToDoDao(get()) }
}

internal fun provideDB(context: Context) : ToDoDatabase =
    Room.databaseBuilder(context,ToDoDatabase::class.java, DB_NAME).build()

internal fun provideToDoDao(database: ToDoDatabase) = database.toDoDao()