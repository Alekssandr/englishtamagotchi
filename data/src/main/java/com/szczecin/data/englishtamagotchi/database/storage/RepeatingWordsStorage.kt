package com.szczecin.data.englishtamagotchi.database.storage

import com.szczecin.data.englishtamagotchi.Mapper
import com.szczecin.data.englishtamagotchi.database.dao.WordsBlockDao
import com.szczecin.englishtamagotchi.model.PairRusEng
import io.reactivex.Completable
import io.reactivex.Single

class RepeatingWordsStorage(private val wordsBlockDao: WordsBlockDao) {

    fun insertRepeatingWords(pairRusEng: List<PairRusEng>): Completable =
        Completable.fromCallable {
            pairRusEng.forEach {
                wordsBlockDao.insert(
                    Mapper().mapToRepeatingEntity(it)
                )
            }
        }

    fun getRepeatingBy(dayOfLearning: Int): Single<List<PairRusEng>> {
        val repeatingList: MutableList<Int> = mutableListOf(1)
        if (dayOfLearning % 3 == 0) {
            repeatingList.add(3)
        }
        if (dayOfLearning % 5 == 0) {
            repeatingList.add(5)
        }
        return wordsBlockDao.getListRepeating(repeatingList)
            .map { Mapper().mapFromLearnEntity(it) }
    }

    //remove old 5(3 times repeating) and 1
    fun removeRepeatingWordsBy(eng: String): Completable =
        wordsBlockDao.deleteRepeatingWordBy(eng)

    //update word - count of repeating in 5 days and day of repeating for 3 and 5 days repeating
    fun updateRepeatingWordsIn3Or5Days(word: PairRusEng, dayOfRepeating: Int): Completable =
        wordsBlockDao.updateIn5DaysRepeatingBy(word.eng, word.countIn5daysRepeating).andThen(
            wordsBlockDao.updateDayOfRepeating(word.eng, dayOfRepeating)
        )
}