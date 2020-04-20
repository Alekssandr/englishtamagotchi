package com.szczecin.englishtamagotchi.usecase.common

import com.szczecin.englishtamagotchi.TranslateRepository
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.repo.WordsCommonRepository
import io.reactivex.Maybe
import javax.inject.Inject

class GetDataFromJSONUseCase @Inject constructor(
    private val routeRepository: TranslateRepository,
    private val wordsBlockRepository: WordsCommonRepository

) {
    fun fetchDataFromJSON(level: Int): Maybe<List<PairRusEng>> =
//        wordsBlockRepository.getSizeOfCommon().filter {
//            it == 0
//        }.flatMap {
            Maybe.just(routeRepository.loadTranslate(level))
//        }
}
