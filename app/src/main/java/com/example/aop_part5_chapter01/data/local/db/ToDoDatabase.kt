package com.example.aop_part5_chapter01.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.aop_part5_chapter01.data.Entity.TodoEntity
import com.example.aop_part5_chapter01.data.local.db.dao.ToDoDao

@Database(entities = [TodoEntity::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase(){

    companion object {
        const val DB_NAME = "ToDoDatabase.db"
    }

    abstract fun toDoDao() : ToDoDao


}