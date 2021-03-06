package com.szczecin.englishtamagotchi.usecase.learn

import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.repo.LearnWordsRepository
import com.szczecin.englishtamagotchi.repo.WordsBlockRepository
import com.szczecin.englishtamagotchi.repo.WordsCommonRepository
import io.reactivex.Completable
import javax.inject.Inject

class UpdateLearnWordUseCase @Inject constructor(
    private val learnWordsRepository: LearnWordsRepository

) {
    fun execute(pairRusEng: List<PairRusEng>): Completable =
        learnWordsRepository.updateLearnWords(pairRusEng)
}
