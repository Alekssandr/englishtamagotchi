package com.szczecin.englishtamagotchi.app.di

import android.app.Application
import android.content.Context
import com.szczecin.pointofinterest.common.rx.RxSchedulers
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext


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