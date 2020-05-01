package com.szczecin.englishtamagotchi.usecase.learn

import android.util.Log
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.repo.LearnWordsRepository
import com.szczecin.englishtamagotchi.repo.LearnWordsTableRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class GetListFromTableWordUseCase @Inject constructor(
    private val learnWordsRepository: LearnWordsRepository,
    private val learnWordsTableRepository: LearnWordsTableRepository

) {
    //get from LT items equals wordsForLearning - get size of learning and put it into L
    fun execute(wordsForLearning: Int): Observable<List<PairRusEng>> {
        return learnWordsRepository.getSizeOfLearning().flatMapObservable { size ->
            learnWordsTableRepository.getLearnTableListToday(wordsForLearning)
        }
    }
}
