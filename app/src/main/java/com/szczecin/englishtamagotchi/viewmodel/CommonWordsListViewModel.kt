package com.szczecin.englishtamagotchi.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.szczecin.englishtamagotchi.common.rx.RxSchedulers
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.preferencies.SettingsPreferences
import com.szczecin.englishtamagotchi.usecase.LoadDataInDBUseCase
import com.szczecin.englishtamagotchi.usecase.common.*
import com.szczecin.englishtamagotchi.usecase.learn.table.AddLearnTableListUseCase
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class CommonWordsListViewModel @Inject constructor(
    private val getDataFromJSONUseCase: GetDataFromJSONUseCase,
    private val getCommonWordsUseCase: GetCommonWordsUseCase,
    private val getCommonWordsRangeUseCase: GetCommonWordsRangeUseCase,
    private val updateCommonItemUseCase: UpdateCommonItemUseCase,
    private val loadDataInBlockDBUseCase: LoadDataInDBUseCase,
    private val loadDataInDBCommonUseCase: LoadDataInDBCommonUseCase,
    private val addLearnTableWordUseCase: AddLearnTableListUseCase,
    private val removeAllCommonUseCase: RemoveAllCommonUseCase,
    private val getSizeCommonUseCase: GetSizeCommonUseCase,
    private val sharedPreferences: SettingsPreferences,
    private val schedulers: RxSchedulers
) : ViewModel(), LifecycleObserver {

    val pairRusEngList: MutableLiveData<List<PairRusEng>> = MutableLiveData()

    private val disposables = CompositeDisposable()

    private val allWords: MutableList<PairRusEng> = mutableListOf()

    val uiClosed = MutableLiveData<Unit>()

    var numberGroup = 0

    val allGroups: MutableLiveData<Int> = MutableLiveData<Int>()

    val progressBarVisibility = MutableLiveData<Boolean>()


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        if(sharedPreferences.lastlevel!=sharedPreferences.level){
            loadCardsFromJSON()
        } else {
            getSizeOfCommon()
        }

    }

    private fun loadCardsFromJSON() {
        progressBarVisibility.value = true
        disposables += getDataFromJSONUseCase.fetchDataFromJSON(sharedPreferences.level)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onSuccess = {
                allGroups.value = it.size / 100
                removeFromCommon(it)
                sharedPreferences.lastlevel = sharedPreferences.level
//                insertInCommon(it)
            }, onComplete = {
//                getSizeOfCommon()
            }, onError = {
                Log.e("Error", it.message ?: "")
            })
    }

    private fun getSizeOfCommon() {
        disposables += getSizeCommonUseCase
            .getSizeOfCommon()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onSuccess = {
                allGroups.value = it / 100
                allWords.clear()
                getAllWords(0)
            }, onError = {
                Log.e("Error", it.message ?: "")
            })
    }

    private fun removeFromCommon(it: List<PairRusEng>) {
        disposables += removeAllCommonUseCase
            .execute()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onComplete = {
                insertInCommon(it)
            }, onError = {
                //                Log.e("Error", it.message ?: "")
            })
    }

    private fun insertInCommon(pairRusEng: List<PairRusEng>) {
        disposables += loadDataInDBCommonUseCase
            .execute(pairRusEng)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onComplete = {
                getAllWords(0)
            }, onError = {
                Log.e("Error", it.message ?: "")
            })
    }

    private fun getAllWords(group: Int) {
        disposables += getCommonWordsRangeUseCase
            .execute(group)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onSuccess = {
                allWords.clear()
                pairRusEngList.value = it
                allWords.addAll(it)
                progressBarVisibility.value = false
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

    fun subscribeForItemGroupClick(clickObserver: Observable<Int>) {
        disposables +=
            clickObserver
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.mainThread())
                .subscribe {
                    getAllWords(it)
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
        fillLearnTable(allWords.filter { !it.isChecked }.shuffled())
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
        disposables += addLearnTableWordUseCase
            .execute(learnWords)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onComplete = {
                Log.d("Test111", "learnWords: ${learnWords.size}")
                uiClosed.postValue(Unit)
                //progressbar
            }, onError = {
                Log.e("Error", it.message ?: "")
            })
    }

    private fun updateListItem(it: Int, isChecked: Boolean) {
        allWords[it].isChecked = isChecked
        Log.d("Test", "lo : " + allWords[it].toString())
        updateItem(allWords[it].eng, isChecked)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}