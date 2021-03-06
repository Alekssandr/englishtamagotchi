package com.szczecin.englishtamagotchi

import com.szczecin.englishtamagotchi.model.PairRusEng

interface TranslateRepository {
    fun loadTranslate(level: Int): List<PairRusEng>
    fun loadTranslate20InCommon(numberBlock: Int, addNewData: Int): List<PairRusEng>
}