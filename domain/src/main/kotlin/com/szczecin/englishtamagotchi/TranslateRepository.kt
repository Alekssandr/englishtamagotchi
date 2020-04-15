package com.szczecin.englishtamagotchi

import com.szczecin.englishtamagotchi.model.PairRusEng

interface TranslateRepository {
    fun loadTranslate(): List<PairRusEng>
    fun loadTranslate20InCommon(numberBlock: Int, addNewData: Int): List<PairRusEng>
}