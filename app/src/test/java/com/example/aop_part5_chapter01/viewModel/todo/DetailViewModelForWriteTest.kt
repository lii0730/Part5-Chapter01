package com.example.aop_part5_chapter01.viewModel.todo

import com.example.aop_part5_chapter01.data.Entity.TodoEntity
import com.example.aop_part5_chapter01.presentation.detail.DetailMode
import com.example.aop_part5_chapter01.presentation.detail.DetailViewModel
import com.example.aop_part5_chapter01.presentation.detail.ToDoDetailState
import com.example.aop_part5_chapter01.presentation.list.ListViewModel
import com.example.aop_part5_chapter01.presentation.list.ToDoListState
import com.example.aop_part5_chapter01.viewModel.ViewModelTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

/**
 * [DetailViewModel]을 테스트 하기 위한 Unit Test Class
 *
 * 1. test viewModel fetch()
 * 2. test insert todo
 *
 */

@ExperimentalCoroutinesApi
internal class DetailViewModelForWriteTest : ViewModelTest() {
    private val id = 0L
    private val detailViewModel by inject<DetailViewModel> { parametersOf(DetailMode.WRITE, id) }
    private val listViewModel by inject<ListViewModel>()

    private val todo = TodoEntity(
        id = id,
        title = "title $id",
        description = "description $id",
        hasCompleted = false
    )

    @Test
    fun `test viewModel fetch`() = runBlockingTest {
        val testObservable = detailViewModel.todoDetailLiveData.test()
        detailViewModel.fetchData()
        testObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialized,
                ToDoDetailState.Write
            )
        )
    }

    @Test
    fun `test insert todo`() = runBlockingTest {
        val detailTestObservable = detailViewModel.todoDetailLiveData.test()
        val listTestObservable = listViewModel.todoListLiveData.test()

        detailViewModel.writeTodo(title = todo.title, description = todo.description)
        detailTestObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialized,
                ToDoDetailState.Loading,
                ToDoDetailState.Success(todo)
            )
        )
        assert(detailViewModel.detailMode == DetailMode.DETAIL)
        assert(detailViewModel.id == id)

        // 뒤로 나가서 리스트 보기
        listViewModel.fetchData()
        listTestObservable.assertValueSequence(
            listOf(
                ToDoListState.UnInitialized,
                ToDoListState.Loading,
                ToDoListState.Suceess(
                    listOf(
                        todo
                    )
                )
            )
        )
    }
}