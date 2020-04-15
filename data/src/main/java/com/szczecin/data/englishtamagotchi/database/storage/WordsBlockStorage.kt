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
            pairRusEng.dayOfLearning = 0
            wordsBlockDao.insert(
                Mapper().mapLearnToEntity(pairRusEng)
            )
        }


    fun getLearnList(): Single<List<PairRusEng>> =
        wordsBlockDao.getLearnList().map { Mapper().mapFromLearnEntity(it) }

//    fun getLearnListRepeating(dayOfLearning: Int): Single<List<PairRusEng>> {
//        when(dayOfLearning){
//        }
//        wordsBlockDao.getLearnListRepeating(daysOfRepeating).map { Mapper().mapFromLearnEntity(it) }
//
//    }

//
//    fun getLearnListBy(dayOfLearning: Int): Single<List<PairRusEng>> =
//        wordsBlockDao.getLearnListByDayOfLearning(dayOfLearning).map {
//            Mapper().mapFromLearnEntity(
//                it
//            )
//        }

    //don't get new words when click on the don;t know, - only words from repeating why?
    fun getLearnListToday(newWordsPerDay: Int): Observable<List<PairRusEng>> =
        wordsBlockDao.getLearnListToday(newWordsPerDay).map {
            Mapper().mapFromLearnEntity(
                it
            )
        }


    fun removeLearnPairRusEng(eng: String): Completable =
        wordsBlockDao.deleteLearnRowByEng(eng)


    //table learning
    fun insertTableLearnWords(pairRusEng: List<PairRusEng>): Completable =
        Completable.fromCallable {
            pairRusEng.forEach {
                wordsBlockDao.insert(
                    Mapper().mapLearnTableToEntity(it)
                )
            }
        }


    fun removeAllFromTableLearn(): Completable =
        wordsBlockDao.deleteAllFromLearnTable()

    fun getLearnTableList(): Single<List<PairRusEng>> =
        wordsBlockDao.getLearnTableList().map { Mapper().mapFromLearnTableEntity(it) }

    fun insertLearnTableWord(pairRusEng: PairRusEng): Completable =
        Completable.fromCallable {
            wordsBlockDao.insert(
                Mapper().mapLearnTableToEntity(pairRusEng)
            )
        }

    fun removeItemFromLearnTable(eng: String): Completable =
        wordsBlockDao.deleteLearnTableRowByEng(eng)

    fun getLearnTableListToday(maxLearningWords: Int): Observable<List<PairRusEng>> =
        wordsBlockDao.getLearnTableListToday(maxLearningWords).map {
            Mapper().mapFromLearnTableEntity(
                it
            )
        }

    fun updateLearningTableBy(eng: String, dayOfLearning: Int) : Completable =
    wordsBlockDao.updateLearningTableBy(eng, dayOfLearning)

    //learning
    fun getSizeOfLearning() = wordsBlockDao.getSizeOfLearning()
}