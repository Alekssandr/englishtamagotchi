package com.szczecin.englishtamagotchi.viewmodel.learning

import android.util.Log
import androidx.lifecycle.*
import com.szczecin.englishtamagotchi.common.rx.RxSchedulers
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.preferencies.SettingsPreferences
import com.szczecin.englishtamagotchi.usecase.learn.GetLearnWordsUseCase
import com.szczecin.englishtamagotchi.usecase.learn.UpdateLearnWordUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

//add get learning words by days! and filter it depends on the real lesson/day follow logic of shown
//add quiz with this words and create special button for it
class LearningViewModel @Inject constructor(
    private val updateLearnWordUseCase: UpdateLearnWordUseCase,
    private val getLearnWordsUseCase: GetLearnWordsUseCase,
    private val sharedPreferences: SettingsPreferences,
    private val schedulers: RxSchedulers
) : ViewModel(), LifecycleObserver {

    val pairRusEngList: MutableLiveData<List<PairRusEng>> = MutableLiveData()

    private val disposables = CompositeDisposable()
    val repeatButtonVisibility = MutableLiveData<Boolean>().apply { value = true }//false

    private val allWords: MutableList<PairRusEng> = mutableListOf()

    val updatedLearnedWords = MutableLiveData<Unit>()
//    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
//    fun onCreate() {
//        getAllWords()
//    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        updatedLearnedWords.observeForever {
            getAllWords()
        }
    }

    private fun getAllWords() {
        disposables += getLearnWordsUseCase
            .execute()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onSuccess = { words ->
                val wordsListUpdated =
                    addNumberOfLesson(words.filter { it.dayOfLearning == 0 }.take(sharedPreferences.newWordsPerDay))
                updateWords(wordsListUpdated)
            }, onError = {
                Log.e("Error", it.message ?: "")
            })
    }

    private fun addNumberOfLesson(todayLearnedWords: List<PairRusEng>): List<PairRusEng> =
        todayLearnedWords.apply {
            this.forEach {
                it.dayOfLearning = sharedPreferences.numberOfLearningDay
            }
        }


    private fun updateWords(it: List<PairRusEng>) {
        disposables += updateLearnWordUseCase
            .execute(it)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onComplete = {
                Log.d("test1111", it.toString())
                repeatButtonVisibility.value = true
            }, onError = {
                Log.e("Error", it.message ?: "")
            })
    }


    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}