package com.szczecin.data.englishtamagotchi.database.model.learning_exercise

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LearnWordsTableEntity")
data class LearnWordsTableEntity(
    @PrimaryKey @ColumnInfo val eng: String,
    @ColumnInfo val id: Int,
    @ColumnInfo val rus: String,
    @ColumnInfo val dayOfLearning: Int
)