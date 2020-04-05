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

class OrdinaryCardViewModel @Inject constructor(
    private val getWordsBlockUseCase: GetWordsBlockUseCase,
    private val schedulers: RxSchedulers
) : ViewModel(), LifecycleObserver {

    val translateWordOpen = MutableLiveData<String>()
    val translateWordClose = MutableLiveData<String>()
    val translateWordCloseVisibility = MutableLiveData<Boolean>()
    val blockWords : MutableList<PairRusEng> = mutableListOf()
    var isTranslateFromEng = true

    private val disposables = CompositeDisposable()
    var indexWord = 0

//подумать мб добавить, что если заходим в setRusOrEng значит переходить на следующий блок
        //а нехт не надо.
    //также бааг когда с анг на рус перехожу слово на англ внизу уже открыто
    //getAllWords поменять на по sharedPreferences.numberOfLearningDay. И сначала последний блок по большей цифре и так далее вниз.
    //потом сделать функцию перемешивания???
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        translateWordCloseVisibility.value = false
        getAllWords()
    }

    fun setRusOrEng(isFromEng: Boolean){
        isTranslateFromEng = isFromEng
    }

    private fun loadBlock(index: Int) {
        if(isTranslateFromEng){
            translateWordOpen.value = blockWords[index].eng
            translateWordClose.value = blockWords[index].rus
        } else {
            translateWordOpen.value = blockWords[index].rus
            translateWordClose.value = blockWords[index].eng
        }
    }

    fun next() {
        if(blockWords.size-1>indexWord) {
            translateWordCloseVisibility.value = false
            indexWord++
            loadBlock(indexWord)
        } else {
            if(isTranslateFromEng) {
                isTranslateFromEng = false
                indexWord = 0
                loadBlock(indexWord)
            }
        }
    }

    fun openWord() {
        translateWordCloseVisibility.value = true
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