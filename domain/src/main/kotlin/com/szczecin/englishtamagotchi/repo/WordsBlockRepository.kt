package com.szczecin.englishtamagotchi.repo

import com.szczecin.englishtamagotchi.model.PairRusEng
import io.reactivex.Completable
import io.reactivex.Single

interface WordsBlockRepository {
    fun insert(pairRusEng: List<PairRusEng>) : Completable
    fun getAll(): Single<List<PairRusEng>>
    fun getTranslate(): Single<String>
    fun getSizeOfWordsBlock(): Single<Int>

//    fun deleteById(voiceRecorderId: Int): Completable
}