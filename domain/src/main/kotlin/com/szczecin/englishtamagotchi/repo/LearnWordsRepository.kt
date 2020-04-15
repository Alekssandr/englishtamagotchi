package com.szczecin.englishtamagotchi.repo

import com.szczecin.englishtamagotchi.model.PairRusEng
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface LearnWordsRepository {
    fun insertLearnWords(pairRusEng: List<PairRusEng>) : Completable
    fun updateLearnWords(pairRusEng: List<PairRusEng>) : Completable
    fun insertLearnWord(pairRusEng: PairRusEng) : Completable
    fun getLearnList(): Single<List<PairRusEng>>
    fun getLearnListToday(newWordsPerDay: Int): Observable<List<PairRusEng>>
    fun getWordsBlockListBy(dayOfLearning: Int): Single<List<PairRusEng>>
    fun getSizeOfCommonBy(numberOfLearningDay: Int): Single<Int>
    fun getSizeOfLearning(): Single<Int>
    fun removeLearnPairRusEng(eng: String): Completable
    fun getLearnListBy(dayOfLearning: Int): Single<List<PairRusEng>>
}