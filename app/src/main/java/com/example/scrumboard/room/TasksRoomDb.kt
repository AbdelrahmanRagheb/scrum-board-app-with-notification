package com.example.scrumboard.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.scrumboard.Const

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TasksRoomDb : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: TasksRoomDb? = null
        fun getDatabase(context: Context): TasksRoomDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TasksRoomDb::class.java,
                    Const.DB_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}