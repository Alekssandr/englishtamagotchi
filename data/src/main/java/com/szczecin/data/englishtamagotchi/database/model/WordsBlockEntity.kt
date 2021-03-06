package com.szczecin.data.englishtamagotchi.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "WordsBlockEntity")
data class WordsBlockEntity(
    @PrimaryKey @ColumnInfo val eng: String,
    @ColumnInfo val rus: String,
    @ColumnInfo val dayOfLearning: Int
)