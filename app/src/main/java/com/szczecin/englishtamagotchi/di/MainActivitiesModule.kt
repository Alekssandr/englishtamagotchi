package com.szczecin.englishtamagotchi.di

import com.szczecin.data.englishtamagotchi.database.repo.WordsBlockDataRepository
import com.szczecin.data.englishtamagotchi.database.repo.common.WordsCommonDataRepository
import com.szczecin.data.englishtamagotchi.database.repo.learn.LearnWordsDataRepository
import com.szczecin.data.englishtamagotchi.database.repo.learn.LearnWordsTableDataRepository
import com.szczecin.data.englishtamagotchi.database.repo.repeating.RepeatingWordsDataRepository
import com.szczecin.data.englishtamagotchi.database.storage.RepeatingWordsStorage
import com.szczecin.data.englishtamagotchi.database.storage.WordsBlockStorage
import com.szczecin.englishtamagotchi.app.di.scopes.PerActivity
import com.szczecin.englishtamagotchi.repo.*
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

    @Provides
    @PerActivity
    fun provideLearnWordsDataRepository(wordsBlockStorage: WordsBlockStorage): LearnWordsRepository =
        LearnWordsDataRepository(wordsBlockStorage)

    @Provides
    @PerActivity
    fun provideLearnWordsTableDataRepository(wordsBlockStorage: WordsBlockStorage): LearnWordsTableRepository =
        LearnWordsTableDataRepository(wordsBlockStorage)


    @Provides
    @PerActivity
    fun provideRepeatingWordsDataRepository(repeatingWordsStorage: RepeatingWordsStorage): RepeatingWordsRepository =
        RepeatingWordsDataRepository(repeatingWordsStorage)

}