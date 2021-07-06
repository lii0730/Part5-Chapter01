package com.example.aop_part5_chapter01.viewModel.todo

import com.example.aop_part5_chapter01.data.Entity.TodoEntity
import com.example.aop_part5_chapter01.domain.todo.InsertTodoItemUseCase
import com.example.aop_part5_chapter01.presentation.detail.DetailMode
import com.example.aop_part5_chapter01.presentation.detail.DetailViewModel
import com.example.aop_part5_chapter01.presentation.detail.ToDoDetailState
import com.example.aop_part5_chapter01.presentation.list.ListViewModel
import com.example.aop_part5_chapter01.presentation.list.ToDoListState
import com.example.aop_part5_chapter01.viewModel.ViewModelTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.koin.core.inject
import org.koin.core.parameter.parametersOf


/**
 * [DetailViewModel]을 테스트 하기 위한 Unit Test Class
 *
 * 1. initData()
 * 2. test viewModel fetch()
 * 3. test delete todo
 * 4. test update todo
 */

@ExperimentalCoroutinesApi
internal class DetailViewModelTest: ViewModelTest() {

    private val id = 1L
    private val detailViewModel by inject<DetailViewModel> { parametersOf(DetailMode.DETAIL, id)}
    private val insertTodoItemUseCase: InsertTodoItemUseCase by inject()
    private val listViewModel by inject<ListViewModel>()
    private val todo = TodoEntity(
        id = id,
        title = "title $id",
        description = "description $id",
        hasCompleted = false
    )

    @Before
    fun init() {
        initData()
    }

    private fun initData() = runBlockingTest {
        insertTodoItemUseCase(todo)
    }

    @Test
    fun `test viewModel fetch`() = runBlockingTest {
        val testObservable = detailViewModel.todoDetailLiveData.test()
        detailViewModel.fetchData()
        testObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialized,
                ToDoDetailState.Loading,
                ToDoDetailState.Success(todo)
            )
        )
    }

    @Test
    fun `test delete todo`() = runBlockingTest {
        val detailTestObservable = detailViewModel.todoDetailLiveData.test()

        detailViewModel.deleteTodo()
        detailTestObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialized,
                ToDoDetailState.Loading,
                ToDoDetailState.Delete
            )
        )

        val listTestObservable = listViewModel.todoListLiveData.test()
        listViewModel.fetchData()
        listTestObservable.assertValueSequence(
            listOf(
                ToDoListState.UnInitialized,
                ToDoListState.Loading,
                ToDoListState.Suceess(listOf())
            )
        )
    }

    @Test
    fun `test update todo`() = runBlockingTest {
        val testObservable = detailViewModel.todoDetailLiveData.test()
        val updateTitle = "title 1 update"
        val updateDescription = "description 1 update"
        val updateTodo = todo.copy(
            title = updateTitle,
            description = updateDescription
        )
        detailViewModel.writeTodo(
            title = updateTitle,
            description = updateDescription
        )
        testObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialized,
                ToDoDetailState.Loading,
                ToDoDetailState.Success(updateTodo)
            )
        )
    }
}