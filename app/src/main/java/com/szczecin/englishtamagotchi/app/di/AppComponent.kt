package com.szczecin.englishtamagotchi.app.di

import android.app.Application
import com.szczecin.englishtamagotchi.app.EnglishTamagochiApplication
import com.szczecin.englishtamagotchi.di.MainActivitiesBinder
import com.szczecin.englishtamagotchi.di.TranslateModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        AppModule::class,
        MainActivitiesBinder::class,
        TranslateModule::class
    ]
)
interface AppComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(pointOfInterestApplication: EnglishTamagochiApplication)
}