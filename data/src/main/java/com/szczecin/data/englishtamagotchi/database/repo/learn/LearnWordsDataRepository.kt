package com.szczecin.data.englishtamagotchi.database.repo.learn


import com.szczecin.data.englishtamagotchi.database.storage.WordsBlockStorage
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.repo.LearnWordsRepository
import com.szczecin.englishtamagotchi.repo.WordsCommonRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class LearnWordsDataRepository(
    private val wordsBlockStorage: WordsBlockStorage
) : LearnWordsRepository {
    override fun getLearnListToday(newWordsPerDay: Int): Single<List<PairRusEng>>
    = wordsBlockStorage.getLearnListToday(newWordsPerDay)

    override fun getLearnListBy(dayOfLearning: Int): Single<List<PairRusEng>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateLearnWords(pairRusEng: List<PairRusEng>): Completable =
    wordsBlockStorage.updateLearnWords(pairRusEng)

    override fun insertLearnWord(pairRusEng: PairRusEng): Completable = wordsBlockStorage.insertLearnWord(pairRusEng)

    override fun getWordsBlockListBy(dayOfLearning: Int): Single<List<PairRusEng>> =
        wordsBlockStorage.getWordsCommonListBy(dayOfLearning)

    override fun getLearnList(): Single<List<PairRusEng>> =
        wordsBlockStorage.getLearnList()

    override fun getSizeOfCommonBy(dayOfLearning: Int): Single<Int> =
        wordsBlockStorage.getSizeOfCommonBy(dayOfLearning)

    override fun removeLearnPairRusEng(eng: String): Completable =
        wordsBlockStorage.removeLearnPairRusEng(eng)


    //learn
    override fun insertLearnWords(pairRusEng: List<PairRusEng>): Completable =
        wordsBlockStorage.insertLearnWords(pairRusEng)

    override fun getSizeOfLearning(): Single<Int> = wordsBlockStorage.getSizeOfLearning()

}