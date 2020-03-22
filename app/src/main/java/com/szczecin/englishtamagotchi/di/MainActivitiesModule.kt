package com.szczecin.englishtamagotchi.di

import com.szczecin.data.englishtamagotchi.database.repo.WordsBlockDataRepository
import com.szczecin.data.englishtamagotchi.database.storage.WordsBlockStorage
import com.szczecin.englishtamagotchi.app.di.scopes.PerActivity
import com.szczecin.englishtamagotchi.repo.WordsBlockRepository
import dagger.Module
import dagger.Provides

@Module
class MainActivitiesModule {

    @Provides
    @PerActivity
    fun provideRobotAccountRepository(voiceRecorderStorage: WordsBlockStorage): WordsBlockRepository =
        WordsBlockDataRepository(voiceRecorderStorage)

//    @Provides
//    @PerActivity
//    fun provideArticlesRepository(wikiApi: WikiApi): ArticlesRepository =
//        ArticlesDataRepository(wikiApi)
//
//    @Provides
//    @PerActivity
//    fun provideMarkerDetailsRepository(wikiApi: WikiApi): MarkerDetailsRepository =
//        MarkerDetailsDataRepository(wikiApi)
//
//    @Provides
//    @PerActivity
//    fun provideImageRepository(wikiApi: WikiApi): ImageRepository =
//        ImageDataRepository(wikiApi)
//
//    @Provides
//    @PerActivity
//    fun provideRouteRepository(googleDirectionsApi: GoogleDirectionsApi, context: Context): RouteRepository =
//        RouteDataRepository(googleDirectionsApi, context.getString(R.string.google_maps_key))
}