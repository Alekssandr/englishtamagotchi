package com.szczecin.englishtamagotchi.usecase.common

import com.szczecin.englishtamagotchi.TranslateRepository
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.model.WordsFilterParams
import com.szczecin.englishtamagotchi.repo.WordsCommonRepository
import io.reactivex.Maybe
import javax.inject.Inject

class GetDataFromJSONCommonUseCase @Inject constructor(
    private val routeRepository: TranslateRepository,
    private val wordsCommonRepository: WordsCommonRepository
    ) {
    fun fetchDataFromJSON(wordsFilterParams: WordsFilterParams): Maybe<List<PairRusEng>> =
        wordsCommonRepository.getSizeOfCommonBy(wordsFilterParams.numberOfLearningDay).filter {
            it < wordsFilterParams.dailyWords
        }.flatMap {
            Maybe.just(routeRepository.loadTranslate20InCommon(wordsFilterParams.numberStart, wordsFilterParams.numberFinish))
        }
}
