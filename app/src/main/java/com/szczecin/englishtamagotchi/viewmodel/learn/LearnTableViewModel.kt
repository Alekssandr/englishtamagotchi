package com.szczecin.englishtamagotchi.viewmodel.learn

import android.util.Log
import androidx.lifecycle.*
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.common.rx.RxSchedulers
import com.szczecin.englishtamagotchi.usecase.know.AddWordKnowUseCase
import com.szczecin.englishtamagotchi.usecase.know.RemoveKnowItemUseCase
import com.szczecin.englishtamagotchi.usecase.learn.AddLearnWordUseCase
import com.szczecin.englishtamagotchi.usecase.learn.GetLearnWordsUseCase
import com.szczecin.englishtamagotchi.usecase.learn.RemoveLearnItemUseCase
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class LearnTableViewModel @Inject constructor(
    private val getLearnWordsUseCase: GetLearnWordsUseCase,
    private val removeLeanItemUseCase: RemoveLearnItemUseCase,
    private val addLearnWordUseCase: AddLearnWordUseCase,
    private val addWordKnowUseCase: AddWordKnowUseCase,
    private val removeKnowItemUseCase: RemoveKnowItemUseCase,
    private val schedulers: RxSchedulers
) : ViewModel(), LifecycleObserver {

    val translateWordCloseVisibility = MutableLiveData<Boolean>()
    val blockWords: MutableList<PairRusEng> = mutableListOf()
    val learnList: MutableLiveData<List<PairRusEng>> = MutableLiveData()
    private val viewSubscriptions = CompositeDisposable()
    lateinit var pairRusEng: PairRusEng


    private val disposables = CompositeDisposable()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        translateWordCloseVisibility.value = false
        getAllWords()
    }


    fun getAllWords() {
        disposables += getLearnWordsUseCase
            .execute()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onSuccess = {
                learnList.value = it
            }, onError = {
                Log.e("Error", it.message ?: "")
            })
    }

    fun subscribeForRemoveItem(clickObserver: Observable<PairRusEng>) {
        viewSubscriptions.add(
            clickObserver
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.mainThread())
                .subscribe {
                    pairRusEng = it
                    removeWord(it.eng)
                })
    }

    fun subscribeAddItem(clickObserver: Observable<PairRusEng>) {
        viewSubscriptions.add(
            clickObserver
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.mainThread())
                .subscribe {
                    pairRusEng = it
                    removeWordKnow(it.eng)
                    addWordToLearn()
                }
        )
    }

    private fun removeWord(eng: String) {
        disposables += removeLeanItemUseCase
            .execute(eng)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onComplete = {
                addWordToKnow()
                Log.d("test111", eng)
            }, onError = {
                Log.e("Error", it.message ?: "")
            })
    }

    fun removeWordKnow(eng: String) {
        disposables += removeKnowItemUseCase
            .execute(eng)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onComplete = {
                addWordToLearn()
            }, onError = {
                Log.e("Error", it.message ?: "")
            })
    }

    fun addWordToLearn() {
        disposables += addLearnWordUseCase
            .execute(pairRusEng)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onComplete = {
                val a = 0
            }, onError = {
                Log.e("Error", it.message ?: "")
            })
    }

    fun addWordToKnow() {
        disposables += addWordKnowUseCase
            .execute(pairRusEng)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onComplete = {
                val a = 0
            }, onError = {
                Log.e("Error", it.message ?: "")
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}