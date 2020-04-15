package com.szczecin.englishtamagotchi.viewmodel.learning

import android.util.Log
import androidx.lifecycle.*
import com.szczecin.englishtamagotchi.common.rx.RxSchedulers
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.preferencies.SettingsPreferences
import com.szczecin.englishtamagotchi.usecase.learn.AddLearnWordUseCase
import com.szczecin.englishtamagotchi.usecase.learn.GetLearnWordsUseCase
import com.szczecin.englishtamagotchi.usecase.learn.UpdateLearnWordUseCase
import com.szczecin.englishtamagotchi.usecase.repeating.RepeatingInsertWordsUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class LearningViewModel @Inject constructor(
    private val updateLearnWordUseCase: UpdateLearnWordUseCase,
    private val getLearnWordsUseCase: GetLearnWordsUseCase,
    private val repeatingInsertWordsUseCase: RepeatingInsertWordsUseCase,
    private val addLearnWordUseCase: AddLearnWordUseCase,
    private val sharedPreferences: SettingsPreferences,
    private val schedulers: RxSchedulers
) : ViewModel(), LifecycleObserver {

    val pairRusEngList: MutableLiveData<List<PairRusEng>> = MutableLiveData()

    private val disposables = CompositeDisposable()

//    val updatedLearnedWords = MutableLiveData<Unit>()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        addWordsFromTableToLearning()

//        updatedLearnedWords.observeForever {
//            getAllLearningWords()
//        }
    }

    //check what day if 0 hide open all, otherwise open repeat

    //add into L from LT only where day learning  = default(0)
    //add inside L only difference between newWordsPerDay and counts of L items

    private fun addWordsFromTableToLearning() {
        disposables += addLearnWordUseCase
            .execute(sharedPreferences.newWordsPerDay)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onComplete = {
                Log.d("test1111", "addWordsFromTableToLearning")
            }, onError = {
                Log.e("test1111", "BUUUUG:${it.message}" )
            })
    }

    //every time insert default learning words in repeating ttable
    //we need to add new, but not update previous one.
    //maybe we need to delete words,what we press 3/5 in repeating from learning?
//    private fun getAllLearningWords() {
//        disposables += getLearnWordsUseCase
//            .execute()
//            .subscribeOn(schedulers.io())
//            .observeOn(schedulers.mainThread())
//            .subscribeBy(onSuccess = { words ->
//                words.forEach {
//
//                    it.dayOfLearning = sharedPreferences.numberOfLearningDay
//                }
//                insertLearningWordsToRepeating(words)
//            }, onError = {
//                Log.e("Error", it.message ?: "")
//            })
//    }
//
//
//    private fun insertLearningWordsToRepeating(words: List<PairRusEng>) {
//        disposables += repeatingInsertWordsUseCase
//            .execute(words)
//            .subscribeOn(schedulers.io())
//            .observeOn(schedulers.mainThread())
//            .subscribeBy(onComplete = {
//                Log.d("test1111", words.toString())
//                updatedLearnedWords.postValue(Unit)
//            }, onError = {
//                Log.e("Error", it.message ?: "")
//            })
//    }


//    private fun getAllWords() {
//        disposables += getLearnWordsUseCase
//            .execute()
//            .subscribeOn(schedulers.io())
//            .observeOn(schedulers.mainThread())
//            .subscribeBy(onSuccess = { words ->
//                val wordsListUpdated =
//                    addNumberOfLesson(words.filter { it.dayOfLearning == 0 }.take(sharedPreferences.newWordsPerDay))
//                updateWords(wordsListUpdated)
//            }, onError = {
//                Log.e("Error", it.message ?: "")
//            })
//    }

//    private fun addNumberOfLesson(todayLearnedWords: List<PairRusEng>): List<PairRusEng> =
//        todayLearnedWords.apply {
//            this.forEach {
//                it.dayOfLearning = sharedPreferences.numberOfLearningDay
//            }
//        }
//
//
//    private fun updateWords(it: List<PairRusEng>) {
//        disposables += updateLearnWordUseCase
//            .execute(it)
//            .subscribeOn(schedulers.io())
//            .observeOn(schedulers.mainThread())
//            .subscribeBy(onComplete = {
//                Log.d("test1111", it.toString())
//                repeatButtonVisibility.value = true
//            }, onError = {
//                Log.e("Error", it.message ?: "")
//            })
//    }


    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}