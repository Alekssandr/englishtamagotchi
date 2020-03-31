package com.szczecin.data.englishtamagotchi.database.repo

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

const val DATA_BASE_NAME = "words_block"

class ProviderDataBase(private val appContext: Context) {
    fun createBuilder(): RoomDatabase.Builder<WordsBlockRoomDatabase> =
        Room.databaseBuilder(appContext, WordsBlockRoomDatabase::class.java, DATA_BASE_NAME)
}