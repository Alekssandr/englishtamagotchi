package com.szczecin.englishtamagotchi.repo

import com.szczecin.englishtamagotchi.model.PairRusEng
import io.reactivex.Completable
import io.reactivex.Single

interface RepeatingWordsRepository {
    fun insertLearnedWords(pairRusEng: List<PairRusEng>) : Completable
    fun getRepeatingListToday(currentDay: Int): Single<List<PairRusEng>>
    fun removeWordFromRepeatingBy(eng: String): Completable
    fun updateRepeatingWordsIn3Or5Days(pairRusEng: PairRusEng, dayOfRepeating: Int) : Completable
}