package com.szczecin.englishtamagotchi.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.szczecin.englishtamagotchi.common.rx.RxSchedulers
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.preferencies.SettingsPreferences
import com.szczecin.englishtamagotchi.usecase.learn.GetLearnWordsUseCase
import com.szczecin.englishtamagotchi.usecase.repeating.RepeatingInsertWordsUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject


class MainViewModel @Inject constructor(
    private val sharedPreferences: SettingsPreferences,
    private val getLearnWordsUseCase: GetLearnWordsUseCase,
    private val repeatingInsertWordsUseCase: RepeatingInsertWordsUseCase,
    private val schedulers: RxSchedulers

) : ViewModel(), LifecycleObserver {
    val perDay5Words = MutableLiveData<Int>()
    val perDay10Words = MutableLiveData<Int>()
    val perDay15Words = MutableLiveData<Int>()
    val numberOfLearningDay = MutableLiveData<Int>().apply { value = 1 }
    val updatedLearnedWords = MutableLiveData<Unit>()

    private val disposables = CompositeDisposable()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        perDay5Words.observeForever {
            sharedPreferences.newWordsPerDay = it
            sharedPreferences.dailyWords = it
        }
        perDay10Words.observeForever {
            sharedPreferences.newWordsPerDay = it
            sharedPreferences.dailyWords = it
        }
        perDay15Words.observeForever {
            sharedPreferences.newWordsPerDay = it
            sharedPreferences.dailyWords = it
        }
//        if (sharedPreferences.numberOfLearningDay > 0) {
//            getAllLearningWords()
//        }

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        sharedPreferences.isOpenRepeating = false
        val currentTime = System.currentTimeMillis()
        val differenceCurrentAndLastTime = currentTime - sharedPreferences.lastOpenDayInMls
        val seconds = differenceCurrentAndLastTime / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        if (days >= 1) {
            sharedPreferences.isOpenRepeating = true
            sharedPreferences.lastOpenDayInMls = currentTime
            sharedPreferences.dailyWords = sharedPreferences.newWordsPerDay
            sharedPreferences.numberOfLearningDay = sharedPreferences.numberOfLearningDay + 1
            Log.d("testtt", sharedPreferences.numberOfLearningDay.toString())
            numberOfLearningDay.value = sharedPreferences.numberOfLearningDay
            if (sharedPreferences.numberOfLearningDay > 1) {
                getAllLearningWords()
            }
        }

    }

    private fun getAllLearningWords() {
        disposables += getLearnWordsUseCase
            .execute()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onSuccess = { words ->
                words.forEach {

                    it.dayOfLearning = sharedPreferences.numberOfLearningDay
                }
                insertLearningWordsToRepeating(words)
            }, onError = {it ->
                Log.e("Error", it.message ?: "")
            })
    }


    private fun insertLearningWordsToRepeating(words: List<PairRusEng>) {
        disposables += repeatingInsertWordsUseCase
            .execute(words)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onComplete = {
                Log.d("test1111", words.toString())
                sharedPreferences.isOpenRepeating = true
                updatedLearnedWords.postValue(Unit)
            }, onError = {
                Log.e("Error", it.message ?: "")
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}