package com.szczecin.englishtamagotchi.usecase.repeating

import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.repo.RepeatingWordsRepository
import io.reactivex.Completable
import javax.inject.Inject

class RepeatingUpdateWordsUseCase @Inject constructor(
    private val repeatingWordsRepository: RepeatingWordsRepository

) {
    fun execute(word: PairRusEng, dayOfRepeating: Int): Completable =
        repeatingWordsRepository.updateRepeatingWordsIn3Or5Days(word, dayOfRepeating)
}
