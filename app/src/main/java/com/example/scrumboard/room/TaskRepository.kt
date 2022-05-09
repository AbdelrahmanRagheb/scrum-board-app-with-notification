package com.example.scrumboard.room

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TaskRepository(private var taskDao: TaskDao) {

    val allTasks: LiveData<MutableList<Task>> = taskDao.getAllTasks()

    fun insert(task: Task) = GlobalScope.launch {
        taskDao.insert(task)
    }

    fun delete(task: Task) = GlobalScope.launch {
        taskDao.delete(task)
    }

    fun update(task: Task) = GlobalScope.launch {
        taskDao.update(task)
    }

    fun deleteAll() = GlobalScope.launch {
        taskDao.deleteAll()
    }

}