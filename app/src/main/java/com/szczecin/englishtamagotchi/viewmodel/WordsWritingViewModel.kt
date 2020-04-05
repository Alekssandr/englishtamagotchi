package com.szczecin.englishtamagotchi.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.usecase.GetDataFromJSONUseCase
import com.szczecin.englishtamagotchi.usecase.GetWordsBlockUseCase
import com.szczecin.englishtamagotchi.usecase.LoadDataInDBUseCase
import com.szczecin.englishtamagotchi.common.rx.RxSchedulers
import com.szczecin.englishtamagotchi.usecase.RemoveWordsBlockUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.databinding.BindingAdapter


class WordsWritingViewModel @Inject constructor(
    private val getWordsBlockUseCase: GetWordsBlockUseCase,
    private val schedulers: RxSchedulers
) : ViewModel(), LifecycleObserver {

    val translateWordOpen = MutableLiveData<String>()
    val translateWordClose = MutableLiveData<String>()
    val translateWordCloseVisibility = MutableLiveData<Boolean>()
    val translateWordCorrectVisibility = MutableLiveData<Boolean>()
    val translateWordInCorrectVisibility = MutableLiveData<Boolean>()
    val clearEditText = MutableLiveData<Boolean>()
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
        }
    }

    fun afterUserNameChange(s: CharSequence) {
        if(s.length>=blockWords[indexWord].eng.length){
            if(s.toString().equals(blockWords[indexWord].eng)){
                isTranslateFromEng = true
            }
        }
    }

    fun check() {
        if(isTranslateFromEng){
            translateWordCorrectVisibility.value = true
        } else {
            translateWordCloseVisibility.value = true
            translateWordInCorrectVisibility.value = true
        }
    }

    fun getAllWords() {
        disposables += getWordsBlockUseCase
            .execute()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onSuccess = {
                blockWords.clear()
                blockWords.addAll(it)
                loadBlock(indexWord)
            }, onError = {
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