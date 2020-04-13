package com.szczecin.englishtamagotchi.usecase.repeating

import com.szczecin.englishtamagotchi.repo.RepeatingWordsRepository
import javax.inject.Inject

class RepeatingGetListWordsUseCase @Inject constructor(
    private val repeatingWordsRepository: RepeatingWordsRepository

) {
    fun execute(currentDay: Int) = repeatingWordsRepository.getRepeatingListToday(currentDay)
}