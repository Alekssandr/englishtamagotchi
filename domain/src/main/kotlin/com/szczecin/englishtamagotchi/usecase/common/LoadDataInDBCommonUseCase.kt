package com.szczecin.englishtamagotchi.usecase.common

import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.repo.WordsBlockRepository
import com.szczecin.englishtamagotchi.repo.WordsCommonRepository
import io.reactivex.Completable
import javax.inject.Inject

class LoadDataInDBCommonUseCase @Inject constructor(
    private val wordsCommonRepository: WordsCommonRepository

) {
    fun execute(pairRusEng: List<PairRusEng>): Completable =
        wordsCommonRepository.insertCommon(pairRusEng)

//    }
}

//fun execute(id: String): Single<Pair<String,String>> = routeRepository.loadTranslate()


//class GetTranslateUseCase @Inject constructor(
//    private val markerDetailsRepository: MarkerDetailsRepository
//) {
//    fun execute(id: String): Single<MarkerDetails> =
//        markerDetailsRepository.fetchMarkerDetails(id)
//}