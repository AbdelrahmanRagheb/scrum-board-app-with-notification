package com.example.scrumboard.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.scrumboard.Const

@Dao
interface TaskDao {
    @Insert
     fun insert(task: Task)

    @Update
     fun update(task: Task)

    @Query("SELECT * FROM ${Const.TABLE_NAME}")
    fun getAllTasks(): LiveData<MutableList<Task>>

    @Delete
     fun delete(task: Task)

    @Query("DELETE FROM ${Const.TABLE_NAME}")
     fun deleteAll()
}