package com.szczecin.data.englishtamagotchi.database.model.common

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "WordsCommonEntity")
data class WordsCommonEntity(
    @PrimaryKey @ColumnInfo val eng: String,
    @ColumnInfo val rus: String
)