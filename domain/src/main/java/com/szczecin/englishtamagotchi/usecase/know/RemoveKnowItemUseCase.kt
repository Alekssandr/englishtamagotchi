package com.szczecin.englishtamagotchi.usecase.know

import com.szczecin.englishtamagotchi.repo.WordsBlockRepository
import javax.inject.Inject

class RemoveKnowItemUseCase @Inject constructor(
    private val wordsBlockRepository: WordsBlockRepository
) {
    fun execute(eng: String) = wordsBlockRepository.removePairRusEng(eng)
}