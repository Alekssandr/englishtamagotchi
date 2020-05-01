package com.szczecin.englishtamagotchi.usecase.learn

import com.szczecin.englishtamagotchi.repo.LearnWordsRepository
import com.szczecin.englishtamagotchi.repo.WordsCommonRepository
import javax.inject.Inject

class GetLearnWordsUseCase @Inject constructor(
    private val learnWordsRepository: LearnWordsRepository
) {
    fun execute() = learnWordsRepository.getLearnList()
}