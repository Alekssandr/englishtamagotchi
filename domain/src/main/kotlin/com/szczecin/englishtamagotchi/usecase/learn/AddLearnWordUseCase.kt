package com.szczecin.englishtamagotchi.usecase.learn

import com.szczecin.englishtamagotchi.repo.LearnWordsRepository
import com.szczecin.englishtamagotchi.repo.LearnWordsTableRepository
import io.reactivex.Completable
import javax.inject.Inject

class AddLearnWordUseCase @Inject constructor(
    private val learnWordsRepository: LearnWordsRepository,
    private val learnWordsTableRepository: LearnWordsTableRepository

) {
    //get from LT items equals wordsForLearning - get size of learning and put it into L
    fun execute(wordsForLearning: Int): Completable {
        return learnWordsRepository.getSizeOfLearning().flatMapCompletable { size ->
            learnWordsTableRepository.getLearnTableListToday(wordsForLearning - size)
                .flatMapCompletable {
                    learnWordsRepository.insertLearnWords(it)
                }.doOnError {
                    val a =it
                }
        }.doOnError {
            val a = it
        }
    }
}
