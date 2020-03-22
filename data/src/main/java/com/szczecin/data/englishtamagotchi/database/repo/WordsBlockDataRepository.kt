package com.szczecin.data.englishtamagotchi.database.repo


import com.szczecin.data.englishtamagotchi.database.storage.WordsBlockStorage
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.repo.WordsBlockRepository
import io.reactivex.Completable
import io.reactivex.Single

class WordsBlockDataRepository(
    private val wordsBlockStorage: WordsBlockStorage
) : WordsBlockRepository {
    override fun getSizeOfWordsBlock(): Single<Int> =
        wordsBlockStorage.getSizeOfWordsBlock()

    override fun getTranslate(): Single<String> {
        return wordsBlockStorage.getTranslate()
    }

    override fun insert(pairRusEng: List<PairRusEng>): Completable {
        return  wordsBlockStorage.insertPairRusEng(pairRusEng)
    }
//    override fun insert(pairRusEng: PairRusEng): Completable {
//       return  wordsBlockStorage.insertPairRusEng(pairRusEng)
//    }

    override fun getAll(): Single<List<PairRusEng>> =
        wordsBlockStorage.getWordsBlockList()



//    override fun deleteById(voiceRecorderId: Int): Completable =
//        wordsBlockStorage.deleteById(voiceRecorderId)
}