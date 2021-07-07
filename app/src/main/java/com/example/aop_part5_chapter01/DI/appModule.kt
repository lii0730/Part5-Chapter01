package com.example.aop_part5_chapter01.DI

import android.content.Context
import androidx.room.Room
import com.example.aop_part5_chapter01.data.Repository.DefaultTodoRepository
import com.example.aop_part5_chapter01.data.Repository.TodoRepository
import com.example.aop_part5_chapter01.data.local.db.ToDoDatabase
import com.example.aop_part5_chapter01.data.local.db.ToDoDatabase.Companion.DB_NAME
import com.example.aop_part5_chapter01.domain.todo.*
import com.example.aop_part5_chapter01.presentation.detail.DetailMode
import com.example.aop_part5_chapter01.presentation.detail.DetailViewModel
import com.example.aop_part5_chapter01.presentation.list.ListViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

//TODO: 모든 구성요소를 선언하는 공간, Koin으로 제공할 객체를 명세하는 곳
internal val appModule = module {
    //TODO: 제공할 객체 멩세

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
    // Factory Method Pattern : 객체 생성을 직접 하지 않고 객체를 생성 / 제공하는 클래스를 사용하여 획득하는 패턴.
    factory { GetTodoListUseCase(get()) }
    factory { GetTodoItemUseCase(get()) }
    factory { InsertTodoListUseCase(get()) }
    factory { InsertTodoItemUseCase(get()) }
    factory { UpdateTodoUseCase(get()) }
    factory { DeleteAllTodoItemUseCase(get()) }
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