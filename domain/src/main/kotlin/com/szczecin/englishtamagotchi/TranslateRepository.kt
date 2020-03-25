package com.szczecin.englishtamagotchi

import com.szczecin.englishtamagotchi.model.AppPluginsConfig
import com.szczecin.englishtamagotchi.model.PairRusEng

interface TranslateRepository {
    fun loadTranslate(): AppPluginsConfig
    fun loadTranslate20InCommon(numberBlock: Int, addNewData: Int): List<PairRusEng>
}