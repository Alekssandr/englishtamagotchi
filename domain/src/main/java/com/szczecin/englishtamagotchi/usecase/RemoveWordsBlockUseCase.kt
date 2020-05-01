package com.szczecin.englishtamagotchi.usecase

import com.szczecin.englishtamagotchi.repo.WordsBlockRepository
import javax.inject.Inject

class RemoveWordsBlockUseCase @Inject constructor(
    private val wordsBlockRepository: WordsBlockRepository

) {
    fun execute() = wordsBlockRepository.removeAll()
}