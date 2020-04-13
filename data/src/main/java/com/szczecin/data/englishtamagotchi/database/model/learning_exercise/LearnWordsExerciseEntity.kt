package com.szczecin.data.englishtamagotchi.database.model.learning_exercise

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LearnWordsExerciseEntity")
data class LearnWordsExerciseEntity(
    @PrimaryKey @ColumnInfo val eng: String,
    @ColumnInfo val rus: String,
    @ColumnInfo val dayOfLearning: Int
)