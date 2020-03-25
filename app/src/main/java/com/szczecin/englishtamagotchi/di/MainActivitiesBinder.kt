package com.szczecin.englishtamagotchi.di

import com.szczecin.englishtamagotchi.view.MainActivity
import com.szczecin.englishtamagotchi.app.di.scopes.PerActivity
import com.szczecin.englishtamagotchi.view.OrdinaryCardActivity
import com.szczecin.englishtamagotchi.view.OrdinaryCardChoiceActivity
import com.szczecin.englishtamagotchi.view.WordsWritingActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivitiesBinder {

    @ContributesAndroidInjector(modules = [MainActivitiesModule::class])
    @PerActivity
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [MainActivitiesModule::class])
    @PerActivity
    abstract fun bindOrdinaryCardActivity(): OrdinaryCardActivity

    @ContributesAndroidInjector(modules = [MainActivitiesModule::class])
    @PerActivity
    abstract fun bindOrdinaryCardChoiceActivity(): OrdinaryCardChoiceActivity

    @ContributesAndroidInjector(modules = [MainActivitiesModule::class])
    @PerActivity
    abstract fun bindWordsWritingActivity(): WordsWritingActivity


}