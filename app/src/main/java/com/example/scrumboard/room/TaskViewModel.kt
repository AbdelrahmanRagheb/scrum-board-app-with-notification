package com.example.scrumboard.room

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    val allTasks: LiveData<MutableList<Task>>
    var repository: TaskRepository

    init {

        val dao = TasksRoomDb.getDatabase(application).taskDao()
        repository = TaskRepository(dao)
        allTasks = repository.allTasks
    }

    fun insert(task: Task) {
        repository.insert(task)
    }

    fun delete(task: Task) {
        repository.delete(task)
    }

    fun update(task: Task) {
        repository.update(task)
    }

    fun deleteAll() {
        repository.deleteAll()
    }


    class TaskViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TaskViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown VieModel Class")
        }
    }


}