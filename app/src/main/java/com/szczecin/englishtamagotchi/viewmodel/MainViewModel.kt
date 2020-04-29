package com.szczecin.englishtamagotchi.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.szczecin.englishtamagotchi.common.rx.RxSchedulers
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.preferencies.SettingsPreferences
import com.szczecin.englishtamagotchi.usecase.learn.GetLearnWordsUseCase
import com.szczecin.englishtamagotchi.usecase.repeating.RepeatingInsertWordsUseCase
import com.szczecin.englishtamagotchi.utils.LEVEL_A_1
import com.szczecin.englishtamagotchi.utils.LEVEL_A_2
import com.szczecin.englishtamagotchi.utils.LEVEL_B_1
import com.szczecin.englishtamagotchi.utils.LEVEL_B_2
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.util.*


class MainViewModel @Inject constructor(
    private val sharedPreferences: SettingsPreferences,
    private val getLearnWordsUseCase: GetLearnWordsUseCase,
    private val repeatingInsertWordsUseCase: RepeatingInsertWordsUseCase,
    private val schedulers: RxSchedulers

) : ViewModel(), LifecycleObserver {
    val perDay5Words = MutableLiveData<Int>()
    val perDay10Words = MutableLiveData<Int>()
    val perDay15Words = MutableLiveData<Int>()
    val level_a1 = MutableLiveData<Int>()
    val level_a2 = MutableLiveData<Int>()
    val level_b1 = MutableLiveData<Int>()
    val level_b2 = MutableLiveData<Int>()
    val numberOfLearningDay = MutableLiveData<Int>().apply { value = 1 }
    val updatedLearnedWords = MutableLiveData<Unit>()
    val updatedRepeating = MutableLiveData<Unit>()

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
        level_a1.observeForever {
            sharedPreferences.level = it
        }
        level_a2.observeForever {
            sharedPreferences.level = it
        }
        level_b1.observeForever {
            sharedPreferences.level = it
        }
        level_b2.observeForever {
            sharedPreferences.level = it
        }

//        if (sharedPreferences.numberOfLearningDay > 0) {
//            getAllLearningWords()
//        }
        updatedRepeating.observeForever {
            repeating()
        }

    }

    fun repeating() {
        getAllLearningWords()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        sharedPreferences.isOpenRepeating = false
        val currentTime = System.currentTimeMillis()
        val c = Calendar.getInstance()
        val dayToday = c.get(Calendar.DAY_OF_MONTH)
        c.timeInMillis = sharedPreferences.lastOpenDayInMls
        val day = c.get(Calendar.DAY_OF_MONTH)
        if (day != dayToday) {
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
                if (words.isNotEmpty()) {
                    words.forEach {

                        it.dayOfLearning = sharedPreferences.numberOfLearningDay
                    }
                    insertLearningWordsToRepeating(words)
                }
            }, onError = { it ->
                Log.e("Error", it.message ?: "")
            })
    }


    private fun insertLearningWordsToRepeating(words: List<PairRusEng>) {
        disposables += repeatingInsertWordsUseCase
            .execute(words)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onComplete = {
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