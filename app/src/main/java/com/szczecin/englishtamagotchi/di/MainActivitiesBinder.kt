package com.szczecin.englishtamagotchi.di

import com.szczecin.englishtamagotchi.view.MainActivity
import com.szczecin.englishtamagotchi.app.di.scopes.PerActivity
import com.szczecin.englishtamagotchi.view.OrdinaryCardActivity
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

}