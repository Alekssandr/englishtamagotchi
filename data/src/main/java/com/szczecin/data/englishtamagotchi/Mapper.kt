package com.szczecin.data.englishtamagotchi

import com.szczecin.data.englishtamagotchi.database.model.WordsBlockEntity
import com.szczecin.data.englishtamagotchi.database.model.common.WordsCommonEntity
import com.szczecin.data.englishtamagotchi.database.model.learn.LearnWordsBlockEntity
import com.szczecin.data.englishtamagotchi.database.model.learning_exercise.LearnWordsTableEntity
import com.szczecin.data.englishtamagotchi.database.model.repeating.RepeatingWordsEntity
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

    fun mapCommonToEntity(pairRusEng: PairRusEng) = WordsCommonEntity(id = pairRusEng.id, eng = pairRusEng.eng, rus = pairRusEng.rus, isCheckbox = pairRusEng.isChecked)

    fun mapLearnToEntity(pairRusEng: PairRusEng) = LearnWordsBlockEntity(pairRusEng.eng, pairRusEng.id, pairRusEng.rus, pairRusEng.dayOfLearning)

    fun mapFromCommonEntity(wordsCommonEntity: List<WordsCommonEntity>) =
        mutableListOf<PairRusEng>().apply {
            wordsCommonEntity.forEach {
                PairRusEng().run {
                    this.id = it.id
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
                    this.id = it.id
                    this.eng = it.eng
                    this.rus = it.rus
                    this.dayOfLearning = it.dayOfLearning
                    add(this)
                }
            }
        }

    fun mapFromLearnEntity2(wordsLeanringEntity: List<LearnWordsBlockEntity>) =
        mutableListOf<PairRusEng>().apply {
            wordsLeanringEntity.forEach {
                PairRusEng().run {
                    this.eng = it.eng
                    this.rus = it.rus
                    this.dayOfLearning = it.dayOfLearning
                    add(this)
                }
            }
        }.take(5)

    //repeating
    fun mapToRepeatingEntity(pairRusEng: PairRusEng) = RepeatingWordsEntity(pairRusEng.eng, pairRusEng.rus, pairRusEng.dayOfLearning)

    fun mapFromRepeatingEntity(repeatingWords: List<RepeatingWordsEntity>) =
        mutableListOf<PairRusEng>().apply {
            repeatingWords.forEach {
                PairRusEng().run {
                    this.eng = it.eng
                    this.rus = it.rus
                    this.dayOfLearning = it.dayOfRepeating
                    add(this)
                }
            }
        }

    //table learn
    fun mapLearnTableToEntity(pairRusEng: PairRusEng) = LearnWordsTableEntity(pairRusEng.eng, pairRusEng.id, pairRusEng.rus, pairRusEng.dayOfLearning)

    fun mapFromLearnTableEntity(learnWordsTableEntity: List<LearnWordsTableEntity>) =
        mutableListOf<PairRusEng>().apply {
            learnWordsTableEntity.forEach {
                PairRusEng().run {
                    this.id = it.id
                    this.eng = it.eng
                    this.rus = it.rus
                    this.dayOfLearning = it.dayOfLearning
                    add(this)
                }
            }
        }


}