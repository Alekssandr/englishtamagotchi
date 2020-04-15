package com.szczecin.englishtamagotchi.viewmodel.learning

import android.util.Log
import androidx.lifecycle.*
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.usecase.GetWordsBlockUseCase
import com.szczecin.englishtamagotchi.common.rx.RxSchedulers
import com.szczecin.englishtamagotchi.preferencies.SettingsPreferences
import com.szczecin.englishtamagotchi.usecase.learn.GetLearnWordsByDayUseCase
import com.szczecin.englishtamagotchi.usecase.learn.GetLearnWordsUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject


class WordsWritingViewModel @Inject constructor(
    private val getLearnWordsUseCase: GetLearnWordsUseCase,
    private val sharedPreferences: SettingsPreferences,
    private val getLearnWordsByDayUseCase: GetLearnWordsByDayUseCase,
    private val schedulers: RxSchedulers
) : ViewModel(), LifecycleObserver {

    val translateWordOpen = MutableLiveData<String>()
    val translateWordClose = MutableLiveData<String>()
    val translateWordCloseVisibility = MutableLiveData<Boolean>()
    val translateWordCorrectVisibility = MutableLiveData<Boolean>()
    val translateWordInCorrectVisibility = MutableLiveData<Boolean>()
    val clearEditText = MutableLiveData<Boolean>()
    val uiClosed = MutableLiveData<Unit>()
    val blockWords: MutableList<PairRusEng> = mutableListOf()
    var isTranslateFromEng = false

    private val disposables = CompositeDisposable()
    var indexWord = 0

    //подумать мб добавить, что если заходим в setRusOrEng значит переходить на следующий блок
    //а нехт не надо.
    //также бааг когда с анг на рус перехожу слово на англ внизу уже открыто
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        translateWordCloseVisibility.value = false
        getAllWords()
    }

    fun setRusOrEng(isFromEng: Boolean) {
        isTranslateFromEng = isFromEng
    }

    private fun loadBlock(index: Int) {
        translateWordOpen.value = blockWords[index].rus
        translateWordClose.value = blockWords[index].eng
    }

    fun next() {
        if (blockWords.size - 1 > indexWord) {
            translateWordCloseVisibility.value = false
            translateWordInCorrectVisibility.value = false
            translateWordCorrectVisibility.value = false
            indexWord++
            loadBlock(indexWord)
            clearEditText.value = true
        } else {
            uiClosed.postValue(Unit)
        }
    }

    fun afterUserNameChange(s: CharSequence) {
        if (s.length >= blockWords[indexWord].eng.length) {
            if (s.toString().equals(blockWords[indexWord].eng)) {
                isTranslateFromEng = true
            }
        }
    }

    fun check() {
        if (isTranslateFromEng) {
            translateWordCorrectVisibility.value = true
        } else {
            translateWordCloseVisibility.value = true
            translateWordInCorrectVisibility.value = true
        }
    }

    fun getAllWords() {
        disposables += getLearnWordsByDayUseCase
            .execute(sharedPreferences.newWordsPerDay)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribe({
                blockWords.clear()
                blockWords.addAll(it)
                loadBlock(indexWord)
            }, {
                Log.e("Error", it.message ?: "")
            })
    }

//    fun removeWord() {
//        disposables += removeWordsBlockUseCase
//            .execute(blockWords[indexWord].eng)
//            .subscribeOn(schedulers.io())
//            .observeOn(schedulers.mainThread())
//            .subscribeBy(onComplete = {
//                blockWords.drop(indexWord)
//                next()
//            }, onError = {
//                Log.e("Error", it.message ?: "")
//            })
//    }

//    fun getTranslate() {
//        disposables += translateWordUseCase
//            .execute()
//            .subscribeOn(schedulers.io())
//            .observeOn(schedulers.mainThread())
//            .subscribeBy(onSuccess = {
//                val a = it
//            }, onError = {
//                Log.e("Error", it.message ?: "")
//            })
//    }


    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}