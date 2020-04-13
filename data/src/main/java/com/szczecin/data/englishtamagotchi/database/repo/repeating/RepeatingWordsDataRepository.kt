package com.szczecin.data.englishtamagotchi.database.repo.repeating


import com.szczecin.data.englishtamagotchi.database.storage.RepeatingWordsStorage
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.repo.RepeatingWordsRepository
import io.reactivex.Completable
import io.reactivex.Single

class RepeatingWordsDataRepository(
    private val repeatingWordsStorage: RepeatingWordsStorage
) : RepeatingWordsRepository {
    override fun updateRepeatingWordsIn3Or5Days(word: PairRusEng, dayOfRepeating: Int): Completable =
        repeatingWordsStorage.updateRepeatingWordsIn3Or5Days(word, dayOfRepeating)

    override fun insertLearnedWords(pairRusEng: List<PairRusEng>): Completable =
        repeatingWordsStorage.insertRepeatingWords(pairRusEng)

    override fun getRepeatingListToday(currentDay: Int): Single<List<PairRusEng>> =
        repeatingWordsStorage.getRepeatingBy(currentDay)

    override fun removeWordFromRepeatingBy(eng: String): Completable =
        repeatingWordsStorage.removeRepeatingWordsBy(eng)
}