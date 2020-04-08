package com.szczecin.englishtamagotchi.usecase.common

import com.szczecin.englishtamagotchi.TranslateRepository
import com.szczecin.englishtamagotchi.model.AppPluginsConfig
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.repo.WordsBlockRepository
import com.szczecin.englishtamagotchi.repo.WordsCommonRepository
import io.reactivex.Maybe
import javax.inject.Inject

class GetDataFromJSONUseCase @Inject constructor(
    private val routeRepository: TranslateRepository,
    private val wordsBlockRepository: WordsCommonRepository

) {
    fun fetchDataFromJSON(): Maybe<List<PairRusEng>> =
        wordsBlockRepository.getSizeOfCommon().filter {
            it == 0
        }.flatMap {
            Maybe.just(routeRepository.loadTranslate())
        }
}
