package com.szczecin.englishtamagotchi.usecase.repeating

import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.repo.LearnWordsRepository
import com.szczecin.englishtamagotchi.repo.RepeatingWordsRepository
import io.reactivex.Completable
import javax.inject.Inject

class RepeatingRemoveItemUseCase @Inject constructor(
    private val repeatingWordsRepository: RepeatingWordsRepository
) {
    fun execute(pairRusEng: PairRusEng): Completable =
        repeatingWordsRepository.removeWordFromRepeatingBy(pairRusEng.eng)
}