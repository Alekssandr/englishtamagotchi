package com.szczecin.data.englishtamagotchi.database.repo

import androidx.room.Database
import androidx.room.RoomDatabase
import com.szczecin.data.englishtamagotchi.database.dao.WordsBlockDao
import com.szczecin.data.englishtamagotchi.database.model.WordsBlockEntity
import com.szczecin.data.englishtamagotchi.database.model.common.WordsCommonEntity

@Database(
    entities = [
        WordsBlockEntity::class,
        WordsCommonEntity::class
    ], version = 2, exportSchema = false
)
abstract class WordsBlockRoomDatabase : RoomDatabase() {
    abstract fun wordsBlockDao(): WordsBlockDao
}