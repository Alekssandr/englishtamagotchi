package com.szczecin.data.englishtamagotchi.database.repo

import androidx.room.Database
import androidx.room.RoomDatabase
import com.szczecin.data.englishtamagotchi.database.dao.WordsBlockDao
import com.szczecin.data.englishtamagotchi.database.model.WordsBlockEntity
import com.szczecin.data.englishtamagotchi.database.model.common.WordsCommonEntity
import com.szczecin.data.englishtamagotchi.database.model.learn.LearnWordsBlockEntity
import com.szczecin.data.englishtamagotchi.database.model.learning_exercise.LearnWordsExerciseEntity
import com.szczecin.data.englishtamagotchi.database.model.repeating.RepeatingWordsEntity

@Database(
    entities = [
        WordsBlockEntity::class,
        WordsCommonEntity::class,
        LearnWordsBlockEntity::class,
        LearnWordsExerciseEntity::class,
        RepeatingWordsEntity::class
    ], version = 3, exportSchema = false
)
abstract class WordsBlockRoomDatabase : RoomDatabase() {
    abstract fun wordsBlockDao(): WordsBlockDao
}