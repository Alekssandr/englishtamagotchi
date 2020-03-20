package com.szczecin.englishtamagotchi.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.szczecin.englishtamagotchi.model.AppPluginsConfig
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.usecase.GetTranslateUseCase
import com.szczecin.pointofinterest.common.rx.RxSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class OrdinaryCardViewModel @Inject constructor(
    private val getTranslateUseCase: GetTranslateUseCase,
    private val schedulers: RxSchedulers
) : ViewModel(), LifecycleObserver {

    val translateWordOpen = MutableLiveData<String>()
    val translateWordClose = MutableLiveData<String>()
    val translateWordCloseVisibility = MutableLiveData<Boolean>()
    lateinit var blockWords : List<PairRusEng>

    private val disposables = CompositeDisposable()


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        translateWordCloseVisibility.value = false
        loadCards()

    }

    private fun loadCards() {
        disposables += getTranslateUseCase
            .execute()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onSuccess = {
                blockWords = it.pairRusEng
            }, onError = {
               Log.e("Error", it.message ?: "")
            })
    }

    fun loadBlock(index: Int){
        translateWordOpen.value = blockWords[index].eng
        translateWordClose.value = blockWords[index].rus
    }

    fun openWord(){
        loadCards()
        translateWordCloseVisibility.value = true
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}