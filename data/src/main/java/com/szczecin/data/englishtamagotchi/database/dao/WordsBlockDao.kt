package com.szczecin.data.englishtamagotchi.database.dao

import androidx.room.*
import com.szczecin.data.englishtamagotchi.database.model.WordsBlockEntity
import com.szczecin.data.englishtamagotchi.database.model.common.WordsCommonEntity
import com.szczecin.data.englishtamagotchi.database.model.learn.LearnWordsBlockEntity
import com.szczecin.data.englishtamagotchi.database.model.learning_exercise.LearnWordsTableEntity
import com.szczecin.data.englishtamagotchi.database.model.repeating.RepeatingWordsEntity
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.Observable


@Dao
interface WordsBlockDao {

    @Query("SELECT * from WordsBlockEntity order by RANDOM()")
    fun getWordsBlockList(): Single<List<WordsBlockEntity>>

    @Query("SELECT * from WordsBlockEntity WHERE dayOfLearning = :dayOfLearning")
    fun getWordsBlockListBy(dayOfLearning: Int): Single<List<WordsBlockEntity>>

    @Query("SELECT rus from WordsBlockEntity WHERE eng = :eng")
    fun getWordTranslateEngToRus(eng: String): Single<String>

    @Query("SELECT rus from WordsBlockEntity WHERE rus = :rus")
    fun getWordTranslateRusToEng(rus: String): Single<String>

    @Query("SELECT COUNT(*) from WordsBlockEntity")
    fun getSizeOfWordsBlock() : Single<Int>

//    order by RANDOM() LIMIT 1


    @Query("DELETE FROM WordsBlockEntity")
    fun deleteAll() : Completable

    //common
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCommon(pairRusEng: WordsCommonEntity) : Long

    @Query("DELETE FROM WordsCommonEntity WHERE eng = :eng")
    fun deleteRowByEngFromCommon(eng: String) : Completable


    @Query("DELETE FROM WordsCommonEntity")
    fun deleteAllCommon() : Completable

//    @Query("SELECT COUNT(*) from WordsCommonEntity WHERE dayOfLearning = :dayOfLearning")
//    fun getSizeOfCommonBy(dayOfLearning: Int) : Single<Int>

    @Query("SELECT COUNT(*) from WordsCommonEntity")
    fun getSizeOfCommon() : Single<Int>

    @Query("SELECT * from WordsCommonEntity")
    fun getWordsCommonList(): Single<List<WordsCommonEntity>>

    @Query("UPDATE WordsCommonEntity SET isCheckbox = :isCheckbox WHERE eng = :eng")
    fun changeItemForCommon(eng: String, isCheckbox: Boolean) : Completable


    //learn words
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pairRusEng: LearnWordsBlockEntity) : Long

    @Query("SELECT * from LearnWordsBlockEntity")
    fun getLearnList(): Single<List<LearnWordsBlockEntity>>
//
//    @Query("SELECT * from LearnWordsBlockEntity WHERE dayOfLearning = :dayOfLearning")
//    fun getLearnListByDayOfLearning(dayOfLearning: Int): Single<List<LearnWordsBlockEntity>>

    @Query("SELECT * from LearnWordsBlockEntity WHERE dayOfLearning == 0 LIMIT :newWordsPerDay")
    fun getLearnListToday(newWordsPerDay: Int): Observable<List<LearnWordsBlockEntity>>

    @Query("UPDATE LearnWordsBlockEntity SET dayOfLearning = :dayOfLearning WHERE eng = :eng")
    fun updateLearnListBy(eng: String, dayOfLearning: Int)

    @Query("DELETE FROM LearnWordsBlockEntity")
    fun deleteAllFromLearnTable() : Completable

    @Query("SELECT COUNT(*) from LearnWordsBlockEntity")
    fun getSizeOfLearning() : Single<Int>


    @Query("DELETE FROM LearnWordsBlockEntity WHERE eng = :eng")
    fun deleteLearnRowByEng(eng: String) : Completable

    //know words
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pairRusEng: WordsBlockEntity) : Long

    @Query("DELETE FROM WordsBlockEntity WHERE eng = :eng")
    fun deleteRowByEng(eng: String) : Completable



    //repeating
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pairRusEng: RepeatingWordsEntity) : Long

    @Query("SELECT * from RepeatingWordsEntity WHERE dayOfRepeating <= :daysOfRepeating")
    fun getListRepeating(daysOfRepeating: Int): Single<List<RepeatingWordsEntity>>

    @Query("DELETE FROM RepeatingWordsEntity WHERE eng = :eng")
    fun deleteRepeatingWordBy(eng: String) : Completable

    @Query("UPDATE RepeatingWordsEntity SET dayOfRepeating = :daysOfRepeating WHERE eng = :eng")
    fun updateDayOfRepeating(eng: String, daysOfRepeating: Int) : Completable

    @Query("UPDATE RepeatingWordsEntity SET dayOfRepeating = :countIn5daysRepeating WHERE eng = :eng")
    fun updateIn5DaysRepeatingBy(eng: String, countIn5daysRepeating: Int) : Completable

    //table learn
    @Query("SELECT * from LearnWordsTableEntity")
    fun getLearnTableList(): Single<List<LearnWordsTableEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pairRusEng: LearnWordsTableEntity) : Long

    @Query("DELETE FROM LearnWordsTableEntity WHERE eng = :eng")
    fun deleteLearnTableRowByEng(eng: String) : Completable

    @Query("SELECT * from LearnWordsTableEntity WHERE dayOfLearning == 0 LIMIT :newWordsPerDay")//remove dayofle and limit for look
    fun getLearnTableListToday(newWordsPerDay: Int): Observable<List<LearnWordsTableEntity>>

    @Query("UPDATE LearnWordsTableEntity SET dayOfLearning = :dayOfLearning WHERE eng = :eng")
    fun updateLearningTableBy(eng: String, dayOfLearning: Int) : Completable
}
