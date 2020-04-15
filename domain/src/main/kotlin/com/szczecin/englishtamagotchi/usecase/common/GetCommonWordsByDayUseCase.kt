package com.szczecin.englishtamagotchi.usecase.common

import com.szczecin.englishtamagotchi.repo.WordsCommonRepository
import javax.inject.Inject

class GetCommonWordsByDayUseCase @Inject constructor(
    private val wordsCommonRepository: WordsCommonRepository

) {
    fun execute(dayOfLearning: Int) = wordsCommonRepository.getWordsBlockListBy(dayOfLearning)
}