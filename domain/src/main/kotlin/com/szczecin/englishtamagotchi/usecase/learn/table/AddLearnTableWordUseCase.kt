package com.szczecin.englishtamagotchi.usecase.learn.table

import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.repo.LearnWordsRepository
import com.szczecin.englishtamagotchi.repo.LearnWordsTableRepository
import com.szczecin.englishtamagotchi.repo.WordsBlockRepository
import com.szczecin.englishtamagotchi.repo.WordsCommonRepository
import io.reactivex.Completable
import javax.inject.Inject

class AddLearnTableWordUseCase @Inject constructor(
    private val learnWordsTableRepository: LearnWordsTableRepository

) {
    fun execute(pairRusEng: PairRusEng): Completable =
        learnWordsTableRepository.insertLearnTableWord(pairRusEng)
}
