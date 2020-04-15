package com.szczecin.data.englishtamagotchi.database.repo.learn


import com.szczecin.data.englishtamagotchi.database.storage.WordsBlockStorage
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.repo.LearnWordsTableRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class LearnWordsTableDataRepository(
    private val wordsBlockStorage: WordsBlockStorage
) : LearnWordsTableRepository {
    override fun getLearnTableListToday(maxLearningWords: Int): Observable<List<PairRusEng>>  =
        wordsBlockStorage.getLearnTableListToday(maxLearningWords)

    override fun insertLearnTableWords(pairRusEng: List<PairRusEng>): Completable =
        wordsBlockStorage.insertTableLearnWords(pairRusEng)

    override fun removeAllFromLearnTable(): Completable =
        wordsBlockStorage.removeAllFromTableLearn()

    override fun updateLearningTableBy(eng: String, dayOfLearning: Int): Completable =
        wordsBlockStorage.updateLearningTableBy(eng, dayOfLearning)


    override fun getLearnTableList(): Single<List<PairRusEng>> =
        wordsBlockStorage.getLearnTableList()

    override fun insertLearnTableWord(pairRusEng: PairRusEng): Completable =
        wordsBlockStorage.insertLearnTableWord(pairRusEng)

    override fun removeItemFromLearnTable(eng: String): Completable =
        wordsBlockStorage.removeItemFromLearnTable(eng)
}