package com.szczecin.englishtamagotchi.usecase.common

import com.szczecin.englishtamagotchi.repo.WordsCommonRepository
import javax.inject.Inject

class GetCommonWordsRangeUseCase @Inject constructor(
    private val wordsCommonRepository: WordsCommonRepository

) {
    fun execute(group: Int) = wordsCommonRepository.getWordsCommonListByGroup(group)
}