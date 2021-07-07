package com.example.aop_part5_chapter01.presentation.list

import com.example.aop_part5_chapter01.data.Entity.TodoEntity

//TODO: sealed class 는 Super Class를 상속받는 Child 클래스의 종류 제한하는 특성을 갖고 있는 클래스
//  정의된 하위 클래스 외에 다른 하위 클래스는 존재하지 않는다?
//  sealed class 는 객체로 생성 불가
sealed class ToDoListState {

    object UnInitialized: ToDoListState()

    object Loading: ToDoListState()

    data class Suceess(
        val toDoList: List<TodoEntity>
    ): ToDoListState()

    object Error: ToDoListState()

}