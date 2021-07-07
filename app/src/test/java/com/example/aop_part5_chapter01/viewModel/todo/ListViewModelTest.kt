package com.example.aop_part5_chapter01.viewModel.todo

import com.example.aop_part5_chapter01.data.Entity.TodoEntity
import com.example.aop_part5_chapter01.domain.todo.GetTodoItemUseCase
import com.example.aop_part5_chapter01.domain.todo.InsertTodoListUseCase
import com.example.aop_part5_chapter01.presentation.list.ListViewModel
import com.example.aop_part5_chapter01.presentation.list.ToDoListState
import com.example.aop_part5_chapter01.viewModel.ViewModelTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.koin.core.inject

/**
 *
 * [ListViewModel]을 테스트 하기 위한 Unit Test Class
 * 1. initData()
 * 2. test viewModel fetch
 * 3. test Item update
 * 4. test Item Delete All
 *
 */
@ExperimentalCoroutinesApi
internal class ListViewModelTest : ViewModelTest() {

    private val viewModel : ListViewModel by inject()
    private val insertTodoListUseCase : InsertTodoListUseCase by inject()
    private val getTodoItemUseCase : GetTodoItemUseCase by inject()
    private val mockList = (0 until 10).map {
        TodoEntity(
            id = it.toLong(),
            title = "title $it",
            description = "description $it",
            hasCompleted = false
        )
    }

    /**
     * 필요한 UseCase들
     * 1. Insert를 위한 TodoListUseCase
     * 2. GetTodoItemUseCase
      */

    @Before
    fun init() {
        initData()
    }

    private fun initData() = runBlockingTest {
        insertTodoListUseCase(mockList)
    }

    // Test : 입력된 데이터를 불러와서 검증한다.
    @Test
    fun `test viewModel fetch`() : Unit = runBlockingTest {
        val testObservable = viewModel.todoListLiveData.test()
        viewModel.fetchData()
        testObservable.assertValueSequence(
            listOf(
                ToDoListState.UnInitialized,
                ToDoListState.Loading,
                ToDoListState.Suceess(mockList)
            )
        )
    }

    // Test : 데이터를 업데이트 했을 떄 잘되는가?
    @Test
    fun `test Item Update`() : Unit = runBlockingTest {
        val todo = TodoEntity(
            id = 1,
            title = "title 1",
            description = "description 1",
            hasCompleted = true
        )
        viewModel.updateEntity(todo)
        assert(getTodoItemUseCase(todo.id)?.hasCompleted ?: false == todo.hasCompleted)
    }

    // Test : 데이터를 다 날렸을 떄 빈상태로 보여지는가?
    @Test  fun`test item delete all`() : Unit = runBlockingTest {
        val testObservable = viewModel.todoListLiveData.test()
        viewModel.deleteAll()
        testObservable.assertValueSequence(
            listOf(
                ToDoListState.UnInitialized,
                ToDoListState.Loading,
                ToDoListState.Suceess(listOf())
            )
        )
    }
}