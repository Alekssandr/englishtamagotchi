package com.szczecin.englishtamagotchi.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.szczecin.englishtamagotchi.preferencies.SettingsPreferences
import javax.inject.Inject


class MainViewModel @Inject constructor(
    private val sharedPreferences: SettingsPreferences
) : ViewModel(), LifecycleObserver {
    val perDay5Words = MutableLiveData<Int>()
    val perDay10Words = MutableLiveData<Int>()
    val perDay15Words = MutableLiveData<Int>()
    val numberOfLearningDay = MutableLiveData<Int>().apply { value = 1 }

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
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        val currentTime = System.currentTimeMillis()
        val differenceCurrentAndLastTime = currentTime - sharedPreferences.lastOpenDayInMls
        val seconds = differenceCurrentAndLastTime / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        if (days >= 1) {
            sharedPreferences.lastOpenDayInMls = currentTime
            sharedPreferences.dailyWords = sharedPreferences.newWordsPerDay
            sharedPreferences.numberOfLearningDay = sharedPreferences.numberOfLearningDay +1
            Log.d("testtt", sharedPreferences.numberOfLearningDay.toString())
            numberOfLearningDay.value = sharedPreferences.numberOfLearningDay
        }
    }
}