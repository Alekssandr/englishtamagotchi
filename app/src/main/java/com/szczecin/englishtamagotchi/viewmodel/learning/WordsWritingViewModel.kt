package com.szczecin.englishtamagotchi.viewmodel.learning

import android.util.Log
import androidx.lifecycle.*
import com.szczecin.englishtamagotchi.common.rx.RxSchedulers
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.preferencies.SettingsPreferences
import com.szczecin.englishtamagotchi.usecase.learn.GetLearnWordsByDayUseCase
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import javax.inject.Inject


class WordsWritingViewModel @Inject constructor(
    private val getLearnWordsByDayUseCase: GetLearnWordsByDayUseCase,
    private val sharedPreferences: SettingsPreferences,
    private val schedulers: RxSchedulers
) : ViewModel(), LifecycleObserver {

    val translateWordOpen = MutableLiveData<String>()
    val translateWordClose = MutableLiveData<String>()
    val translateWordCloseVisibility = MutableLiveData<Boolean>()
    val translateWordCorrectVisibility = MutableLiveData<Boolean>()
    val translateWordInCorrectVisibility = MutableLiveData<Boolean>()
    val clearEditText = MutableLiveData<Boolean>()
    val uiClosed = MutableLiveData<Unit>()
    val letters: MutableLiveData<List<Char>> = MutableLiveData()
    val writingText = MutableLiveData<Pair<String, Boolean>>()
    private val blockWords: MutableList<PairRusEng> = mutableListOf()
    private var writingTextEditText: StringBuilder = StringBuilder()
    var countNotCorrect = 0

    private val disposables = CompositeDisposable()
    var indexWord = 0

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        translateWordCloseVisibility.value = false
        getAllWords()
    }

    private fun loadBlock(index: Int) {
        translateWordOpen.value = blockWords[index].rus
        translateWordClose.value = blockWords[index].eng
        fillLetters()
    }

    private fun fillLetters() {
        val lettersList = blockWords[indexWord].eng.toCharArray()
        letters.value = lettersList.distinct().toList().shuffled()
    }

    fun next() {
        if (blockWords.size - 1 > indexWord) {
            translateWordCloseVisibility.value = false
            translateWordInCorrectVisibility.value = false
            translateWordCorrectVisibility.value = false
            indexWord++
            loadBlock(indexWord)
            writingTextEditText.clear()
            clearEditText.value = true
            countNotCorrect = 0
        } else {
            uiClosed.postValue(Unit)
        }
    }

    private fun checkWritingWords() =
        writingTextEditText.toString().toLowerCase() == blockWords[indexWord].eng

    fun check() {
        if (checkWritingWords()) {
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

    fun subscribeForRusItemClick(clickItemObserver: Observable<Char>) {
        disposables +=
            clickItemObserver
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.mainThread())
                .subscribe {
                    writingTextEditText.append(it)
                    refactorSpace()
                    writingText.value = Pair(
                        writingTextEditText.toString(),
                        isCorrectLetter(writingTextEditText.toString())
                    )
                    if (!isCorrectLetter(writingTextEditText.toString())) countNotCorrect++
                    if (countNotCorrect > 2) {
                        check()
                    }
                }
    }

    fun addListInccorrectIndexes(){

    }

    fun refactorSpace() {
        if (!isCorrectLetter(writingTextEditText.toString())) {
            if (writingTextEditText[writingTextEditText.length - 1] == ' ') {
                writingTextEditText[writingTextEditText.length - 1] = '_'
            }
        }
    }

    private fun isCorrectLetter(writingTextEditText: String) =
        if (writingTextEditText.length <= blockWords[indexWord].eng.length - 1)
            writingTextEditText[writingTextEditText.length - 1] ==
                    blockWords[indexWord].eng[writingTextEditText.length - 1] else false


    fun removeLastLetter() {
        if (writingTextEditText.isNotEmpty()) {
            writingTextEditText.deleteCharAt(writingTextEditText.length - 1)
            writingText.value = Pair(
                writingTextEditText.toString(), true
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}