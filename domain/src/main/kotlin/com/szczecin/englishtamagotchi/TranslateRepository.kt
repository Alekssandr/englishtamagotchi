package com.szczecin.englishtamagotchi

import com.szczecin.englishtamagotchi.model.AppPluginsConfig
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.model.Tutorial
import io.reactivex.Single

interface TranslateRepository {
    fun loadTranslate(): Single<AppPluginsConfig>
}