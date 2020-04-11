package com.szczecin.data.englishtamagotchi.database.storage

import com.szczecin.data.englishtamagotchi.Mapper
import com.szczecin.data.englishtamagotchi.database.dao.WordsBlockDao
import com.szczecin.englishtamagotchi.model.PairRusEng
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class WordsBlockStorage(private val wordsBlockDao: WordsBlockDao) {

    fun insertPairRusEng(pairRusEng: List<PairRusEng>): Completable =
        Completable.fromCallable {
            pairRusEng.forEach {
                wordsBlockDao.insert(
                    Mapper().mapToEntity(it)
                )
            }
        }

    fun insertKnowWord(pairRusEng: PairRusEng): Completable =
        Completable.fromCallable {
            wordsBlockDao.insert(
                Mapper().mapToEntity(pairRusEng)
            )
        }

    fun getWordsBlockList(): Single<List<PairRusEng>> =
        wordsBlockDao.getWordsBlockList().map { Mapper().mapFromEntity(it) }

    fun getWordsBlockListBy(dayOfLearning: Int): Single<List<PairRusEng>> =
        wordsBlockDao.getWordsBlockListBy(dayOfLearning).map { Mapper().mapFromEntity(it) }

    fun getTranslate(): Single<String> =
        wordsBlockDao.getWordTranslateEngToRus("few")

    fun getSizeOfWordsBlock(): Single<Int> =
        wordsBlockDao.getSizeOfWordsBlock()

    fun removePairRusEng(eng: String): Completable =
        wordsBlockDao.deleteRowByEng(eng)

    fun removeAll(): Completable =
        wordsBlockDao.deleteAll()


    //------------------COMMON---------------------------
    fun insertCommon(pairRusEng: List<PairRusEng>): Completable =
        Completable.fromCallable {
            pairRusEng.forEach {
                wordsBlockDao.insertCommon(
                    Mapper().mapCommonToEntity(it)
                )
            }
        }

    fun removePairRusEngFromCommon(eng: String): Completable =
        wordsBlockDao.deleteRowByEngFromCommon(eng)

    fun getSizeOfCommonBy(dayOfLearning: Int): Single<Int> =
//        wordsBlockDao.getSizeOfCommonBy(dayOfLearning)
        Single.just(0)

    fun getWordsCommonList(): Single<List<PairRusEng>> =
        wordsBlockDao.getWordsCommonList().map { Mapper().mapFromCommonEntity(it) }

    fun getWordsCommonListBy(dayOfLearning: Int): Single<List<PairRusEng>> =
//        wordsBlockDao.getWordsCommonListBy(dayOfLearning).map { Mapper().mapFromCommonEntity(it) }
        Single.just(arrayListOf())

    //new
    fun getSizeOfCommon() = wordsBlockDao.getSizeOfCommon()

    fun updateItemForCommon(eng: String, isCheckbox: Boolean) =
        wordsBlockDao.changeItemForCommon(eng, isCheckbox)

    //learn
    fun insertLearnWords(pairRusEng: List<PairRusEng>): Completable =
        Completable.fromCallable {
            pairRusEng.forEach {
                wordsBlockDao.insert(
                    Mapper().mapLearnToEntity(it)
                )
            }
        }

    fun updateLearnWords(pairRusEng: List<PairRusEng>): Completable =
        Completable.fromCallable {
            pairRusEng.forEach {
                wordsBlockDao.updateLearnListBy(
                    Mapper().mapLearnToEntity(it).eng, Mapper().mapLearnToEntity(it).dayOfLearning
                )
            }
        }

    fun insertLearnWord(pairRusEng: PairRusEng): Completable =
        Completable.fromCallable {
            wordsBlockDao.insert(
                Mapper().mapLearnToEntity(pairRusEng)
            )
        }

    fun removeAllFromLearn(): Completable =
        wordsBlockDao.deleteAllFromLearnTable()

    fun getLearnList(): Single<List<PairRusEng>> =
        wordsBlockDao.getLearnList().map { Mapper().mapFromLearnEntity(it) }

    fun getLearnListBy(dayOfLearning: Int): Single<List<PairRusEng>> =
        wordsBlockDao.getLearnListByDayOfLearning(dayOfLearning).map {
            Mapper().mapFromLearnEntity(
                it
            )
        }

    fun getLearnListToday(newWordsPerDay: Int): Observable<List<PairRusEng>> =
        wordsBlockDao.getLearnListToday(newWordsPerDay).map {
            Mapper().mapFromLearnEntity(
                it
            )
        }


    fun removeLearnPairRusEng(eng: String): Completable =
        wordsBlockDao.deleteLearnRowByEng(eng)


}