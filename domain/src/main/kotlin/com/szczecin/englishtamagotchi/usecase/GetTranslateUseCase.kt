package com.szczecin.englishtamagotchi.usecase

import com.szczecin.englishtamagotchi.TranslateRepository
import com.szczecin.englishtamagotchi.model.AppPluginsConfig
import io.reactivex.Single
import javax.inject.Inject

class GetTranslateUseCase @Inject constructor(
    private val routeRepository: TranslateRepository
) {
    fun execute(): Single<AppPluginsConfig> = routeRepository.loadTranslate()
}

//fun execute(id: String): Single<Pair<String,String>> = routeRepository.loadTranslate()


//class GetTranslateUseCase @Inject constructor(
//    private val markerDetailsRepository: MarkerDetailsRepository
//) {
//    fun execute(id: String): Single<MarkerDetails> =
//        markerDetailsRepository.fetchMarkerDetails(id)
//}