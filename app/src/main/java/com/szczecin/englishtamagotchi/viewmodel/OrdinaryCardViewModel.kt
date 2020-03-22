package com.szczecin.englishtamagotchi.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.usecase.DownloadInitialDataUseCase
import com.szczecin.englishtamagotchi.usecase.GetWordsBlockUseCase
import com.szczecin.englishtamagotchi.usecase.LoadDataInDBUseCase
import com.szczecin.englishtamagotchi.usecase.TranslateWordUseCase
import com.szczecin.pointofinterest.common.rx.RxSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class OrdinaryCardViewModel @Inject constructor(
    private val downloadInitialDataUseCase: DownloadInitialDataUseCase,
    private val getWordsBlockUseCase: GetWordsBlockUseCase,
    private val loadDataInDBUseCase: LoadDataInDBUseCase,
    private val translateWordUseCase: TranslateWordUseCase,
    private val schedulers: RxSchedulers
) : ViewModel(), LifecycleObserver {

    val translateWordOpen = MutableLiveData<String>()
    val translateWordClose = MutableLiveData<String>()
    val translateWordCloseVisibility = MutableLiveData<Boolean>()
    lateinit var blockWords : List<PairRusEng>

    private val disposables = CompositeDisposable()
    var indexWord = 0


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        translateWordCloseVisibility.value = false
        loadCards()
    }

    private fun loadCards() {
        loadCardsToRoom(downloadInitialDataUseCase
            .execute().pairRusEng)
    }

    private fun loadCardsToRoom(pairRusEng: List<PairRusEng>) {
        disposables += loadDataInDBUseCase
            .execute(pairRusEng)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onComplete = {
                getAllWords()
            }, onError = {
                Log.e("Error", it.message ?: "")
            })
    }

    fun loadBlock(index: Int){
        translateWordOpen.value = blockWords[index].eng
        translateWordClose.value = blockWords[index].rus
        indexWord++
    }

    fun next(){
        translateWordCloseVisibility.value = false
        loadBlock(indexWord)
    }

    fun openWord(){
        translateWordCloseVisibility.value = true
    }

    fun getAllWords(){
        disposables += getWordsBlockUseCase
            .execute()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onSuccess = {
                blockWords = it
                loadBlock(indexWord)
            }, onError = {
                Log.e("Error", it.message ?: "")
            })
    }

    fun getTranslate(){
        disposables += translateWordUseCase
            .execute()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onSuccess = {
                val a = it
            }, onError = {
                Log.e("Error", it.message ?: "")
            })
    }



    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}