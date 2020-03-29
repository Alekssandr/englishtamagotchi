package com.szczecin.englishtamagotchi.repo

import com.szczecin.englishtamagotchi.model.PairRusEng
import io.reactivex.Completable
import io.reactivex.Single

interface WordsCommonRepository {
    fun insertCommon(pairRusEng: List<PairRusEng>) : Completable
    fun getWordsCommonList(): Single<List<PairRusEng>>
    fun getSizeOfCommon(): Single<Int>
    fun removePairRusEngFromCommon(eng: String): Completable
}