package com.szczecin.englishtamagotchi.usecase.common

import com.szczecin.englishtamagotchi.TranslateRepository
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.repo.WordsCommonRepository
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class GetSizeCommonUseCase @Inject constructor(
    private val wordsBlockRepository: WordsCommonRepository

) {
    fun getSizeOfCommon(): Single<Int> =
        wordsBlockRepository.getSizeOfCommon()
}
