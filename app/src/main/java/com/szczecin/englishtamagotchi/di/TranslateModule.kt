package com.szczecin.englishtamagotchi.di

import android.content.Context
import com.szczecin.data.englishtamagotchi.plugins.LocalCommonTranslateRepository
import com.szczecin.englishtamagotchi.TranslateRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TranslateModule {

    @Provides
    @Singleton
    fun provideRemoteCommonTranslateUseCase(context: Context): TranslateRepository = LocalCommonTranslateRepository(context)

//    @Provides
//    @Singleton
//    fun provideLocalCommonTranslateRepository(context: Context) = LocalCommonTranslateRepository(context)
//
//    @Provides
//    @Singleton
//    fun provideCustomConfigProvider(context: Context, lunaSdk: LunaSDK) = CustomConfigProvider(context, lunaSdk)
}