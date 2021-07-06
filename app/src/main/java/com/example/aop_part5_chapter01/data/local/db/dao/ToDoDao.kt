package com.example.aop_part5_chapter01.data.local.db.dao

import androidx.room.*
import com.example.aop_part5_chapter01.data.Entity.TodoEntity

@Dao
interface ToDoDao {

    @Query("SELECT * FROM TodoEntity")
    suspend fun getAll(): List<TodoEntity>

    @Query("SELECT * FROM TodoEntity WHERE id=:id")
    suspend fun getById(id: Long): TodoEntity?

    @Insert
    suspend fun insert(toDoEntity: TodoEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(toDoEntityList: List<TodoEntity>)

    @Query("DELETE FROM TodoEntity WHERE id=:id")
    suspend fun delete(id: Long) : Boolean

    @Query("DELETE FROM TodoEntity")
    suspend fun deleteAll()

    @Update
    suspend fun update(toDoEntity: TodoEntity) : Boolean

}