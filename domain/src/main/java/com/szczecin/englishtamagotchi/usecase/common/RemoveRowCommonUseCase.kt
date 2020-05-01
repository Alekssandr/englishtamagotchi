package com.szczecin.englishtamagotchi.usecase.common

import com.szczecin.englishtamagotchi.repo.WordsCommonRepository
import javax.inject.Inject

class RemoveRowCommonUseCase @Inject constructor(
    private val wordsCommonRepository: WordsCommonRepository
) {
    fun execute(eng: String) = wordsCommonRepository.removePairRusEngFromCommon(eng)
}