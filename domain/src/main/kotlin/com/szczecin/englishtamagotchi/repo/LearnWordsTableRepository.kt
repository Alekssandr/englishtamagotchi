package com.szczecin.englishtamagotchi.repo

import com.szczecin.englishtamagotchi.model.PairRusEng
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface LearnWordsTableRepository {
    fun insertLearnTableWords(pairRusEng: List<PairRusEng>): Completable
    fun insertLearnTableWord(pairRusEng: PairRusEng): Completable
    fun removeAllFromLearnTable(): Completable
    fun updateLearningTableBy(eng: String, dayOfLearning: Int): Completable
    fun removeItemFromLearnTable(eng: String): Completable
    fun getLearnTableList(): Single<List<PairRusEng>>
    fun getLearnTableListToday(maxLearningWords: Int): Observable<List<PairRusEng>>
}