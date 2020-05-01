package com.szczecin.englishtamagotchi.usecase.learn

import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.repo.LearnWordsRepository
import com.szczecin.englishtamagotchi.repo.LearnWordsTableRepository
import io.reactivex.Completable
import javax.inject.Inject

class InsertToToLearnUseCase @Inject constructor(
    private val learnWordsRepository: LearnWordsRepository,
    private val learnWordsTableRepository: LearnWordsTableRepository

) {
    fun execute(wordsForLearning: List<PairRusEng>): Completable  = learnWordsRepository.insertLearnWords(wordsForLearning)

}

