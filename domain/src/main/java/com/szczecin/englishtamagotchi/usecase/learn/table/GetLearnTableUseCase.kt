package com.szczecin.englishtamagotchi.usecase.learn.table

import com.szczecin.englishtamagotchi.repo.LearnWordsTableRepository
import javax.inject.Inject

class GetLearnTableUseCase @Inject constructor(
    private val learnWordsTableRepository: LearnWordsTableRepository
) {
    fun execute() = learnWordsTableRepository.getLearnTableList()
}