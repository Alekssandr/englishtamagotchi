package com.szczecin.data.englishtamagotchi.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.szczecin.data.englishtamagotchi.database.model.WordsBlockEntity
import com.szczecin.data.englishtamagotchi.database.model.common.WordsCommonEntity
import com.szczecin.englishtamagotchi.model.PairRusEng
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface WordsBlockDao {

    @Query("SELECT * from WordsBlockEntity order by RANDOM()")
    fun getWordsBlockList(): Single<List<WordsBlockEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pairRusEng: WordsBlockEntity) : Long

    @Query("SELECT rus from WordsBlockEntity WHERE eng = :eng")
    fun getWordTranslateEngToRus(eng: String): Single<String>

    @Query("SELECT rus from WordsBlockEntity WHERE rus = :rus")
    fun getWordTranslateRusToEng(rus: String): Single<String>

    @Query("SELECT COUNT(*) from WordsBlockEntity")
    fun getSizeOfWordsBlock() : Single<Int>

//    order by RANDOM() LIMIT 1
    @Query("DELETE FROM WordsBlockEntity WHERE eng = :eng")
    fun deleteRowByEng(eng: String) : Completable

    @Query("DELETE FROM WordsBlockEntity")
    fun deleteAll() : Completable

    //common
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCommon(pairRusEng: WordsCommonEntity) : Long

    @Query("DELETE FROM WordsCommonEntity WHERE eng = :eng")
    fun deleteRowByEngFromCommon(eng: String) : Completable

    @Query("SELECT COUNT(*) from WordsCommonEntity")
    fun getSizeOfCommon() : Single<Int>

    @Query("SELECT * from WordsCommonEntity")
    fun getWordsCommonList(): Single<List<WordsCommonEntity>>
}