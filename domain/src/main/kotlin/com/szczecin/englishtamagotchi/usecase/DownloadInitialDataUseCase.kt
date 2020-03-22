package com.szczecin.englishtamagotchi.usecase

import com.szczecin.englishtamagotchi.TranslateRepository
import com.szczecin.englishtamagotchi.model.AppPluginsConfig
import com.szczecin.englishtamagotchi.repo.WordsBlockRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class DownloadInitialDataUseCase @Inject constructor(
    private val routeRepository: TranslateRepository,
    private val wordsBlockRepository: WordsBlockRepository

) {
    fun execute(): AppPluginsConfig =
        routeRepository.loadTranslate()
//            .pairRusEng.forEach {
//            wordsBlockRepository.insert(it)
//        }
//    }


    fun saveDataToDB(): Single<Boolean> =
        wordsBlockRepository.getSizeOfWordsBlock().map {
            if(  it > 0 ){
                false
            } else {
                routeRepository.loadTranslate()
                true
            }
        }
}

//fun execute(id: String): Single<Pair<String,String>> = routeRepository.loadTranslate()


//class GetTranslateUseCase @Inject constructor(
//    private val markerDetailsRepository: MarkerDetailsRepository
//) {
//    fun execute(id: String): Single<MarkerDetails> =
//        markerDetailsRepository.fetchMarkerDetails(id)
//}