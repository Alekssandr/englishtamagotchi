package com.szczecin.englishtamagotchi.usecase.common

import com.szczecin.englishtamagotchi.repo.WordsCommonRepository
import javax.inject.Inject

class RemoveAllCommonUseCase @Inject constructor(
    private val wordsCommonRepository: WordsCommonRepository
) {
    fun execute() = wordsCommonRepository.removeDeleteAllCommon()
}