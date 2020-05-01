package com.szczecin.englishtamagotchi.usecase.know

import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.repo.WordsBlockRepository
import io.reactivex.Completable
import javax.inject.Inject

class AddWordKnowUseCase @Inject constructor(
    private val wordsBlockRepository: WordsBlockRepository

) {
    fun execute(pairRusEng: PairRusEng): Completable =
        wordsBlockRepository.insertKnowWord(pairRusEng)
}
