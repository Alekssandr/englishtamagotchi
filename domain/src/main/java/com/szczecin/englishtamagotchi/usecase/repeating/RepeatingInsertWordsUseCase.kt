package com.szczecin.englishtamagotchi.usecase.repeating

import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.repo.RepeatingWordsRepository
import javax.inject.Inject

class RepeatingInsertWordsUseCase @Inject constructor(
    private val repeatingWordsRepository: RepeatingWordsRepository

) {
    fun execute(pairRusEng: List<PairRusEng>) =
        repeatingWordsRepository.insertLearnedWords(pairRusEng)
}