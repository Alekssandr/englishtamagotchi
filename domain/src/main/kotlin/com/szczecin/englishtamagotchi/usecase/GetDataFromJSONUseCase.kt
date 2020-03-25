package com.szczecin.englishtamagotchi.usecase

import com.szczecin.englishtamagotchi.TranslateRepository
import com.szczecin.englishtamagotchi.model.AppPluginsConfig
import com.szczecin.englishtamagotchi.repo.WordsBlockRepository
import io.reactivex.Maybe
import javax.inject.Inject

class GetDataFromJSONUseCase @Inject constructor(
    private val routeRepository: TranslateRepository,
    private val wordsBlockRepository: WordsBlockRepository

) {
    fun fetchDataFromJSON(): Maybe<AppPluginsConfig> =
        wordsBlockRepository.getSizeOfWordsBlock().filter {
            it == 0
        }.flatMap {
            Maybe.just(routeRepository.loadTranslate())
        }
}
