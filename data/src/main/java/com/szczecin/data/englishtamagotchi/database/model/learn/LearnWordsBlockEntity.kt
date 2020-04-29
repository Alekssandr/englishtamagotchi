package com.szczecin.data.englishtamagotchi.database.model.learn

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LearnWordsBlockEntity")
data class LearnWordsBlockEntity(
    @PrimaryKey @ColumnInfo val eng: String,
    @ColumnInfo val id: Int,
    @ColumnInfo val rus: String,
    @ColumnInfo val dayOfLearning: Int
)