package com.szczecin.englishtamagotchi.di

import com.szczecin.englishtamagotchi.app.di.scopes.PerActivity
import com.szczecin.englishtamagotchi.view.*
import com.szczecin.englishtamagotchi.view.common.CommonWordsActivity
import com.szczecin.englishtamagotchi.view.know.KnowTableActivity
import com.szczecin.englishtamagotchi.view.learn.LearnTableActivity
import com.szczecin.englishtamagotchi.view.learning.*
import com.szczecin.englishtamagotchi.view.repeat.RepeatActivity
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

    @ContributesAndroidInjector(modules = [MainActivitiesModule::class])
    @PerActivity
    abstract fun bindWordsBindWordsActivity(): BindWordsActivity


    @ContributesAndroidInjector(modules = [MainActivitiesModule::class])
    @PerActivity
    abstract fun bindCommonWordsActivity(): CommonWordsActivity


    @ContributesAndroidInjector(modules = [MainActivitiesModule::class])
    @PerActivity
    abstract fun bindLearnTableActivity(): LearnTableActivity

    @ContributesAndroidInjector(modules = [MainActivitiesModule::class])
    @PerActivity
    abstract fun bindKnowTableActivity(): KnowTableActivity


    @ContributesAndroidInjector(modules = [MainActivitiesModule::class])
    @PerActivity
    abstract fun bindLearningActivity(): LearningActivity

    @ContributesAndroidInjector(modules = [MainActivitiesModule::class])
    @PerActivity
    abstract fun bindRepeatActivity(): RepeatActivity


}