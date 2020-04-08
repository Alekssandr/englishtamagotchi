package com.szczecin.data.englishtamagotchi

import com.szczecin.data.englishtamagotchi.database.model.WordsBlockEntity
import com.szczecin.data.englishtamagotchi.database.model.common.WordsCommonEntity
import com.szczecin.data.englishtamagotchi.database.model.learn.LearnWordsBlockEntity
import com.szczecin.englishtamagotchi.model.PairRusEng

class Mapper {
    fun mapFromEntity(wordsBlockEntityList: List<WordsBlockEntity>) =
        mutableListOf<PairRusEng>().apply {
            wordsBlockEntityList.forEach {
                PairRusEng().run {
                    this.eng = it.eng
                    this.rus = it.rus
                    this.dayOfLearning = it.dayOfLearning
                    add(this)
                }
            }
        }

    fun mapToEntity(pairRusEng: PairRusEng) = WordsBlockEntity(pairRusEng.eng, pairRusEng.rus, pairRusEng.dayOfLearning)

    fun mapCommonToEntity(pairRusEng: PairRusEng) = WordsCommonEntity(pairRusEng.eng, pairRusEng.rus, pairRusEng.isChecked)

    fun mapLearnToEntity(pairRusEng: PairRusEng) = LearnWordsBlockEntity(pairRusEng.eng, pairRusEng.rus, pairRusEng.dayOfLearning)

    fun mapFromCommonEntity(wordsCommonEntity: List<WordsCommonEntity>) =
        mutableListOf<PairRusEng>().apply {
            wordsCommonEntity.forEach {
                PairRusEng().run {
                    this.eng = it.eng
                    this.rus = it.rus
                    this.isChecked = it.isCheckbox
                    add(this)
                }
            }
        }

    fun mapFromLearnEntity(wordsLeanringEntity: List<LearnWordsBlockEntity>) =
        mutableListOf<PairRusEng>().apply {
            wordsLeanringEntity.forEach {
                PairRusEng().run {
                    this.eng = it.eng
                    this.rus = it.rus
                    this.dayOfLearning = it.dayOfLearning
                    add(this)
                }
            }
        }
}