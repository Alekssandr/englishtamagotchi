package com.szczecin.englishtamagotchi.usecase.common

import com.szczecin.englishtamagotchi.TranslateRepository
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.repo.WordsCommonRepository
import io.reactivex.Maybe
import javax.inject.Inject

class GetDataFromJSONCommonUseCase @Inject constructor(
    private val routeRepository: TranslateRepository,
    private val wordsCommonRepository: WordsCommonRepository
) {
    fun fetchDataFromJSON(numberStart: Int, addNewData: Int): Maybe<List<PairRusEng>> =
        wordsCommonRepository.getSizeOfCommon().filter {
            //it < 20
            it < 5
        }.flatMap {
            Maybe.just(routeRepository.loadTranslate20InCommon(numberStart, addNewData))
        }
}
