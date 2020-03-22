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

}