package com.szczecin.englishtamagotchi.viewmodel.addOwnWord

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.szczecin.englishtamagotchi.common.rx.RxSchedulers
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.usecase.common.LoadDataInDBCommonUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class AddOwnWordViewModel @Inject constructor(
    private val loadDataInDBCommonUseCase: LoadDataInDBCommonUseCase,
    private val schedulers: RxSchedulers
) : ViewModel(), LifecycleObserver {

    val translateWordCloseVisibility = MutableLiveData<Boolean>()
    lateinit var pairRusEng: PairRusEng
    val eng = MutableLiveData<String>()
    val rus = MutableLiveData<String>()

    private val disposables = CompositeDisposable()

    private fun insertInCommon() {
        disposables += loadDataInDBCommonUseCase
            .execute(listOf(PairRusEng(eng = eng.value!!, rus = rus.value!!)))
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onComplete = {
                eng.value = ""
                rus.value = ""
            }, onError = {
                Log.e("Error", it.message ?: "")
            })
    }

    fun save(){
        insertInCommon()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}