package com.szczecin.data.englishtamagotchi.database.model.repeating

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RepeatingWordsEntity")
data class RepeatingWordsEntity(
    @PrimaryKey @ColumnInfo val eng: String,
    @ColumnInfo val rus: String,
    @ColumnInfo val dayOfRepeating: Int
)