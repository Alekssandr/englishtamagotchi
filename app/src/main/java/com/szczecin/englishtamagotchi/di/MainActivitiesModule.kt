package com.szczecin.englishtamagotchi.di

import com.szczecin.data.englishtamagotchi.database.repo.WordsBlockDataRepository
import com.szczecin.data.englishtamagotchi.database.repo.common.WordsCommonDataRepository
import com.szczecin.data.englishtamagotchi.database.storage.WordsBlockStorage
import com.szczecin.englishtamagotchi.app.di.scopes.PerActivity
import com.szczecin.englishtamagotchi.repo.WordsBlockRepository
import com.szczecin.englishtamagotchi.repo.WordsCommonRepository
import dagger.Module
import dagger.Provides

@Module
class MainActivitiesModule {

    @Provides
    @PerActivity
    fun provideWordsBlockRepository(wordsBlockStorage: WordsBlockStorage): WordsBlockRepository =
        WordsBlockDataRepository(wordsBlockStorage)

    @Provides
    @PerActivity
    fun provideWordsCommonRepository(wordsBlockStorage: WordsBlockStorage): WordsCommonRepository =
        WordsCommonDataRepository(wordsBlockStorage)
}