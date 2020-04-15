package com.szczecin.englishtamagotchi.usecase.learn.table

import com.szczecin.englishtamagotchi.repo.LearnWordsTableRepository
import javax.inject.Inject

class UpdateLearnTableWordUseCase @Inject constructor(
    private val learnWordsTableRepository: LearnWordsTableRepository
) {
    fun execute(eng: String, dayOfLearning: Int) = learnWordsTableRepository.updateLearningTableBy(eng, dayOfLearning)
}