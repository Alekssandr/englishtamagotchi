package com.szczecin.englishtamagotchi.app.di

import android.app.Application
import android.content.Context
import com.szczecin.data.englishtamagotchi.database.dao.WordsBlockDao
import com.szczecin.data.englishtamagotchi.database.repo.ProviderDataBase
import com.szczecin.data.englishtamagotchi.database.repo.WordsBlockDatabase
import com.szczecin.data.englishtamagotchi.database.repo.WordsBlockRoomDatabase
import com.szczecin.data.englishtamagotchi.database.storage.WordsBlockStorage
import com.szczecin.pointofinterest.common.rx.RxSchedulers
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Singleton
    @Provides
    fun provideDatabase(context: Context): WordsBlockRoomDatabase = WordsBlockDatabase(
        ProviderDataBase(context)
    ).build()

    @Singleton
    @Provides
    fun provideVoiceRecorderDao(voiceRecorderRoomDatabase: WordsBlockRoomDatabase): WordsBlockDao =
        voiceRecorderRoomDatabase.wordsBlockDao()


    @Singleton
    @Provides
    fun provideVoiceRecorderStorage(voiceRecorderDao: WordsBlockDao): WordsBlockStorage =
        WordsBlockStorage(voiceRecorderDao)

//    @Singleton
//    @Provides
//    fun provideWikiApi(retrofit: Retrofit): WikiApi = retrofit.create(WikiApi::class.java)
//
//    @Singleton
//    @Provides
//    fun provideDirectionsApi(@Named("directions")retrofit: Retrofit): GoogleDirectionsApi = retrofit.create(GoogleDirectionsApi::class.java)

    @Singleton
    @Provides
    fun provideSchedulers(): RxSchedulers = RxSchedulers()

//    @Singleton
//    @Provides
//    fun provideNetworkProvider() = NetworkProvider()
//
//    @Singleton
//    @Provides
//    fun provideCommonApiRetrofit(
//        networkProvider: NetworkProvider
//    ): Retrofit = networkProvider.provideCommonApiRetrofit(BuildConfig.API_URL)
//
//    @Singleton
//    @Provides
//    @Named("directions")
//    fun provideCommonApiRetrofitRoute(
//        networkProvider: NetworkProvider
//    ): Retrofit = networkProvider.provideCommonApiRetrofit(BuildConfig.API_ROUTE_URL)
}