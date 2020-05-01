package com.szczecin.englishtamagotchi.usecase.learn.table

import com.szczecin.englishtamagotchi.repo.LearnWordsRepository
import com.szczecin.englishtamagotchi.repo.LearnWordsTableRepository
import com.szczecin.englishtamagotchi.repo.WordsBlockRepository
import com.szczecin.englishtamagotchi.repo.WordsCommonRepository
import javax.inject.Inject

class RemoveTableLearnItemUseCase @Inject constructor(
    private val learnWordsTableRepository: LearnWordsTableRepository
) {
    fun execute(eng: String) = learnWordsTableRepository.removeItemFromLearnTable(eng)
}