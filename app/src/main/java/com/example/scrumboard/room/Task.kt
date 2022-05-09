package com.example.scrumboard.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.scrumboard.Const

@Entity(tableName = Const.TABLE_NAME)
data class Task(
    var title: String = "",
    var content: String = "",
    var date: String = "",
    var time: String = "",
    var flag: Int = 0,
    var importance: Int = 0
) {
    @PrimaryKey(autoGenerate = false)
    var id: String = generateName()

    private fun generateName(): String {
        val source = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12abcdef3456jhijkl98452mnopq1rs8tuv923wxyz"
        var rand = ""
        for (i in 0..20) {
            rand += source[(source.indices).random()]
        }
        return rand
    }

}