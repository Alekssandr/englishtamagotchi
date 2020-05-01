package com.szczecin.englishtamagotchi.usecase

import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.repo.WordsBlockRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class TranslateWordUseCase @Inject constructor(
    private val wordsBlockRepository: WordsBlockRepository

) {
    fun execute(): Single<String> =
            wordsBlockRepository.getTranslate()

//    }
}

//fun execute(id: String): Single<Pair<String,String>> = routeRepository.loadTranslate()


//class GetTranslateUseCase @Inject constructor(
//    private val markerDetailsRepository: MarkerDetailsRepository
//) {
//    fun execute(id: String): Single<MarkerDetails> =
//        markerDetailsRepository.fetchMarkerDetails(id)
//}