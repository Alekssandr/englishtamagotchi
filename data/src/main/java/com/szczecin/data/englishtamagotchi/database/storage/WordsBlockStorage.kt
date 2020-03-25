package com.szczecin.data.englishtamagotchi.database.storage

import com.szczecin.data.englishtamagotchi.Mapper
import com.szczecin.data.englishtamagotchi.database.dao.WordsBlockDao
import com.szczecin.englishtamagotchi.model.PairRusEng
import io.reactivex.Completable
import io.reactivex.Single

class WordsBlockStorage(private val wordsBlockDao: WordsBlockDao) {

    fun insertPairRusEng(pairRusEng: List<PairRusEng>): Completable  =
        Completable.fromCallable {
            pairRusEng.forEach {
                wordsBlockDao.insert(
                    Mapper().mapToEntity(it)
                )
            }
        }

    fun getWordsBlockList(): Single<List<PairRusEng>> =
        wordsBlockDao.getWordsBlockList().map { Mapper().mapFromEntity(it) }

    fun getTranslate(): Single<String> =
        wordsBlockDao.getWordTranslate("few")

    fun getSizeOfWordsBlock(): Single<Int> =
        wordsBlockDao.getSizeOfWordsBlock()

    fun removePairRusEng(eng: String): Completable =
        wordsBlockDao.deleteRowByEng(eng)

    fun removeAll(): Completable =
        wordsBlockDao.deleteAll()


    //------------------COMMON---------------------------
    fun insertCommon(pairRusEng: List<PairRusEng>): Completable  =
        Completable.fromCallable {
            pairRusEng.forEach {
                wordsBlockDao.insertCommon(
                    Mapper().mapCommonToEntity(it)
                )
            }
        }

    fun removePairRusEngFromCommon(eng: String): Completable =
        wordsBlockDao.deleteRowByEngFromCommon(eng)

    fun getSizeOfCommon(): Single<Int> =
        wordsBlockDao.getSizeOfCommon()

    fun getWordsCommonList(): Single<List<PairRusEng>> =
        wordsBlockDao.getWordsCommonList().map { Mapper().mapFromCommonEntity(it) }


}