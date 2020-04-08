package com.szczecin.englishtamagotchi.usecase.learn

import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.repo.LearnWordsRepository
import com.szczecin.englishtamagotchi.repo.WordsBlockRepository
import com.szczecin.englishtamagotchi.repo.WordsCommonRepository
import io.reactivex.Completable
import javax.inject.Inject

class FillLearnTableUseCase @Inject constructor(
    private val learnWordsRepository: LearnWordsRepository

) {
    fun execute(pairRusEng: List<PairRusEng>): Completable =
        learnWordsRepository.removeAllFromLearn().andThen(learnWordsRepository.insertLearnWords(pairRusEng))


//    }
}

//fun execute(id: String): Single<Pair<String,String>> = routeRepository.loadTranslate()


//class GetTranslateUseCase @Inject constructor(
//    private val markerDetailsRepository: MarkerDetailsRepository
//) {
//    fun execute(id: String): Single<MarkerDetails> =
//        markerDetailsRepository.fetchMarkerDetails(id)
//}