package com.szczecin.englishtamagotchi.repo

import com.szczecin.englishtamagotchi.model.PairRusEng
import io.reactivex.Completable
import io.reactivex.Single

interface WordsCommonRepository {
    fun insertCommon(pairRusEng: List<PairRusEng>) : Completable
    fun removeDeleteAllCommon() : Completable
    fun getWordsCommonList(): Single<List<PairRusEng>>
    fun getWordsCommonListByGroup(group: Int): Single<List<PairRusEng>>
    fun getWordsBlockListBy(dayOfLearning: Int): Single<List<PairRusEng>>
    fun getSizeOfCommonBy(numberOfLearningDay: Int): Single<Int>
    fun getSizeOfCommon(): Single<Int>
    fun removePairRusEngFromCommon(eng: String): Completable
    fun updateItemForCommon(eng: String, isCheckbox: Boolean): Completable
}