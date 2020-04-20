package com.szczecin.data.englishtamagotchi.database.repo.common


import com.szczecin.data.englishtamagotchi.database.storage.WordsBlockStorage
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.repo.WordsCommonRepository
import io.reactivex.Completable
import io.reactivex.Single

class WordsCommonDataRepository(
    private val wordsBlockStorage: WordsBlockStorage
) : WordsCommonRepository {
    override fun removeDeleteAllCommon(): Completable = wordsBlockStorage.removeDeleteAllCommon()

    override fun getWordsBlockListBy(dayOfLearning: Int): Single<List<PairRusEng>> =
        wordsBlockStorage.getWordsCommonListBy(dayOfLearning)

    override fun insertCommon(pairRusEng: List<PairRusEng>): Completable =
        wordsBlockStorage.insertCommon(pairRusEng)

    override fun getWordsCommonList(): Single<List<PairRusEng>> =
        wordsBlockStorage.getWordsCommonList()

    override fun getSizeOfCommonBy(dayOfLearning: Int): Single<Int> =
        wordsBlockStorage.getSizeOfCommonBy(dayOfLearning)

    override fun removePairRusEngFromCommon(eng: String): Completable =
        wordsBlockStorage.removePairRusEngFromCommon(eng)

    //new
    override fun getSizeOfCommon(): Single<Int> = wordsBlockStorage.getSizeOfCommon()
    override fun updateItemForCommon(eng: String, isCheckbox: Boolean): Completable =
        wordsBlockStorage.updateItemForCommon(eng, isCheckbox)

}