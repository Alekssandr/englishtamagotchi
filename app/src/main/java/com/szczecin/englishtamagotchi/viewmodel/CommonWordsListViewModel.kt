package com.szczecin.englishtamagotchi.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.szczecin.englishtamagotchi.common.rx.RxSchedulers
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.usecase.GetWordsBlockUseCase
import com.szczecin.englishtamagotchi.usecase.LoadDataInDBUseCase
import com.szczecin.englishtamagotchi.usecase.common.GetDataFromJSONUseCase
import com.szczecin.englishtamagotchi.usecase.common.GetCommonWordsUseCase
import com.szczecin.englishtamagotchi.usecase.common.LoadDataInDBCommonUseCase
import com.szczecin.englishtamagotchi.usecase.common.UpdateCommonItemUseCase
import com.szczecin.englishtamagotchi.usecase.learn.FillLearnTableUseCase
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class CommonWordsListViewModel @Inject constructor(
    private val getDataFromJSONUseCase: GetDataFromJSONUseCase,
    private val getCommonWordsUseCase: GetCommonWordsUseCase,
    private val updateCommonItemUseCase: UpdateCommonItemUseCase,
    private val loadDataInBlockDBUseCase: LoadDataInDBUseCase,
    private val fillLearnTableUseCase: FillLearnTableUseCase,
    private val loadDataInDBCommonUseCase: LoadDataInDBCommonUseCase,

    private val schedulers: RxSchedulers
) : ViewModel(), LifecycleObserver {

    val pairRusEngList: MutableLiveData<List<PairRusEng>> = MutableLiveData()

    private val disposables = CompositeDisposable()

    private val allWords: MutableList<PairRusEng> = mutableListOf()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        loadCardsFromJSON()
    }

    private fun loadCardsFromJSON() {
        disposables += getDataFromJSONUseCase.fetchDataFromJSON()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onSuccess = {
                insertInCommon(it)
            }, onComplete = {
                getAllWords()
            }, onError = {
                Log.e("Error", it.message ?: "")
            })
    }

    private fun insertInCommon(pairRusEng: List<PairRusEng>) {
        disposables += loadDataInDBCommonUseCase
            .execute(pairRusEng)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onComplete = {
                getAllWords()
            }, onError = {
                Log.e("Error", it.message ?: "")
            })
    }

    private fun getAllWords() {
        disposables += getCommonWordsUseCase
            .execute()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onSuccess = {
                pairRusEngList.value = it
                allWords.addAll(it)
            }, onError = {
                Log.e("Error", it.message ?: "")
            })
    }

    fun subscribeForItemClick(clickObserver: Observable<Int>) {
        disposables +=
            clickObserver
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.mainThread())
                .subscribe {
                    if (allWords[it].isChecked) {
                        updateListItem(it, false)
                    } else {
                        updateListItem(it, true)
                    }
                }
    }

    private fun updateItem(eng: String, isChecked: Boolean) {
        disposables +=
            updateCommonItemUseCase.updateItemBy(eng, isChecked)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.mainThread())
                .subscribeBy(onComplete = {
                    Log.d("Test111", "updated:${eng} and ${isChecked}")
                }, onError = {
                    Log.e("Error", it.message ?: "")
                })
    }

    fun fillAllFolders() {
        allWords.filter { it.isChecked }
        fillKnowTable(allWords.filter { it.isChecked })
        fillLearnTable(allWords.filter { !it.isChecked })
    }

    private fun fillKnowTable(knowWords: List<PairRusEng>) {
        disposables += loadDataInBlockDBUseCase
            .execute(knowWords)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onComplete = {
                Log.d("Test111", "knowWords: ${knowWords.size}")
                //progressbar
            }, onError = {
                Log.e("Error", it.message ?: "")
            })
    }

    private fun fillLearnTable(learnWords: List<PairRusEng>) {
        disposables += fillLearnTableUseCase
            .execute(learnWords)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onComplete = {
                Log.d("Test111", "learnWords: ${learnWords.size}")
                //progressbar
            }, onError = {
                Log.e("Error", it.message ?: "")
            })
    }

    private fun updateListItem(it: Int, isChecked: Boolean) {
        allWords[it].isChecked = isChecked
        updateItem(allWords[it].eng, isChecked)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}