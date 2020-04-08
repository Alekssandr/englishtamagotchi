package com.szczecin.data.englishtamagotchi.database.repo.learn


import com.szczecin.data.englishtamagotchi.database.storage.WordsBlockStorage
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.repo.LearnWordsRepository
import com.szczecin.englishtamagotchi.repo.WordsCommonRepository
import io.reactivex.Completable
import io.reactivex.Single

class LearnWordsDataRepository(
    private val wordsBlockStorage: WordsBlockStorage
) : LearnWordsRepository {
    override fun insertLearnWord(pairRusEng: PairRusEng): Completable = wordsBlockStorage.insertLearnWord(pairRusEng)

    override fun getWordsBlockListBy(dayOfLearning: Int): Single<List<PairRusEng>> =
        wordsBlockStorage.getWordsCommonListBy(dayOfLearning)

    override fun getLearnList(): Single<List<PairRusEng>> =
        wordsBlockStorage.getLearnList()

    override fun getSizeOfCommonBy(dayOfLearning: Int): Single<Int> =
        wordsBlockStorage.getSizeOfCommonBy(dayOfLearning)

    override fun removeLearnPairRusEng(eng: String): Completable =
        wordsBlockStorage.removeLearnPairRusEng(eng)

    //new
    override fun getSizeOfCommon(): Single<Int> = wordsBlockStorage.getSizeOfCommon()
    override fun updateItemForCommon(eng: String, isCheckbox: Boolean): Completable =
        wordsBlockStorage.updateItemForCommon(eng, isCheckbox)


    //learn
    override fun insertLearnWords(pairRusEng: List<PairRusEng>): Completable =
        wordsBlockStorage.insertLearnWords(pairRusEng)

    override fun removeAllFromLearn(): Completable =
        wordsBlockStorage.removeAllFromLearn()

}