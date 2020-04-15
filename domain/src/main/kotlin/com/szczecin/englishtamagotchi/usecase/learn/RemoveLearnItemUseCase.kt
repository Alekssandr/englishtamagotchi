package com.szczecin.englishtamagotchi.usecase.learn

import com.szczecin.englishtamagotchi.repo.LearnWordsRepository
import com.szczecin.englishtamagotchi.repo.WordsBlockRepository
import com.szczecin.englishtamagotchi.repo.WordsCommonRepository
import javax.inject.Inject

class RemoveLearnItemUseCase @Inject constructor(
    private val learnWordsRepository: LearnWordsRepository
) {
    fun execute(eng: String) = learnWordsRepository.removeLearnPairRusEng(eng).doOnComplete {
        val a = 0
    }.doOnError {
        val b = it
    }
}