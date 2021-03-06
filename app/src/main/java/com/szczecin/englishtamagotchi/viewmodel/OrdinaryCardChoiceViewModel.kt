package com.szczecin.englishtamagotchi.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.common.rx.RxSchedulers
import com.szczecin.englishtamagotchi.model.WordsFilterParams
import com.szczecin.englishtamagotchi.preferencies.SettingsPreferences
import com.szczecin.englishtamagotchi.usecase.LoadDataInDBUseCase
import com.szczecin.englishtamagotchi.usecase.RemoveWordsBlockUseCase
import com.szczecin.englishtamagotchi.usecase.common.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject
//maybe remove?
class OrdinaryCardChoiceViewModel @Inject constructor(
    private val getDataFromJSONUseCase: GetDataFromJSONCommonUseCase,
    private val getCommonWordsUseCase: GetCommonWordsUseCase,
    private val getCommonWordsByDayUseCase: GetCommonWordsByDayUseCase,
    private val loadDataInDBUseCase: LoadDataInDBCommonUseCase,
    private val loadDataInBlockDBUseCase: LoadDataInDBUseCase,
    private val removeRowCommonUseCase: RemoveRowCommonUseCase,
    private val removeWordsBlockUseCase: RemoveWordsBlockUseCase,
    private val sharedPreferences: SettingsPreferences,
    private val schedulers: RxSchedulers
) : ViewModel(), LifecycleObserver {

    val translateWordEng = MutableLiveData<String>()
    val translateWordRus = MutableLiveData<String>()
    val blockNumber = MutableLiveData<String>()
    val blockWords: MutableList<PairRusEng> = mutableListOf()
    val openExercise = MutableLiveData<Boolean>()
    val engTextForListen = MutableLiveData<String>()

    private val disposables = CompositeDisposable()
    var indexWord = 0
    var addNewData = sharedPreferences.dailyWords


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        loadCardsFromJSON()
    }

    //показать лист всех слов, которые учу
    //не уверен что нужна вторая таблица которая полностью дублирует данные комана
    private fun loadCardsFromJSON() {
        disposables += getDataFromJSONUseCase
            .fetchDataFromJSON(
                WordsFilterParams(
                    sharedPreferences.numberStart,
                    addNewData,
                    sharedPreferences.dailyWords,
                    sharedPreferences.numberOfLearningDay
                )
            )
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onSuccess = {
                Log.d("shared", sharedPreferences.numberStart.toString())
                blockNumber.value = sharedPreferences.numberStart.toString()
                loadCardsToRoom(it)
                sharedPreferences.numberStart = sharedPreferences.numberStart + addNewData
            }, onComplete = {
                getAllWordsByDayCommon()
            }, onError = {
                Log.e("Error", it.message ?: "")
            })
    }

    private fun setNumberOfRepeating(pairRusEng: List<PairRusEng>): List<PairRusEng> =
        pairRusEng.apply {
            forEach {
                it.dayOfLearning = sharedPreferences.numberOfLearningDay
            }
        }


    private fun loadCardsToRoom(pairRusEng: List<PairRusEng>) {
        disposables += loadDataInDBUseCase
            .execute(setNumberOfRepeating(pairRusEng))
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onComplete = {
                getAllWordsByDayCommon()
            }, onError = {
                Log.e("Error", it.message ?: "")
            })
    }

    private fun loadBlock(index: Int) {
        translateWordEng.value = blockWords[index].eng
        translateWordRus.value = blockWords[index].rus
    }

    fun next() {
        when {
            blockWords.size - 1 > indexWord -> {
                indexWord++
                loadBlock(indexWord)
            }
            blockWords.size < sharedPreferences.newWordsPerDay -> {
                indexWord = sharedPreferences.newWordsPerDay - 1
                addNewData = sharedPreferences.newWordsPerDay - blockWords.size
                loadCardsFromJSON()
            }
            else -> removeAllFromBlock()
        }
    }

    private fun getAllWordsByDayCommon() {
        disposables += getCommonWordsByDayUseCase
            .execute(sharedPreferences.numberOfLearningDay)
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

//    private fun getAllWords() {
//        disposables += getCommonWordsUseCase
//            .execute()
//            .subscribeOn(schedulers.io())
//            .observeOn(schedulers.mainThread())
//            .subscribeBy(onSuccess = {
//                blockWords.clear()
//
//                blockWords.addAll(it)
//
//                loadBlock(indexWord)
//            }, onError = {
//                Log.e("Error", it.message ?: "")
//            })
//    }

    fun removeWord() {
        disposables += removeRowCommonUseCase
            .execute(blockWords[indexWord].eng)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onComplete = {
                blockWords.removeAt(indexWord)
                if (indexWord < sharedPreferences.newWordsPerDay - 1) indexWord--
                next()
            }, onError = {
                Log.e("Error", it.message ?: "")
            })
    }

    fun removeAllFromBlock() {
        disposables += removeWordsBlockUseCase
            .execute()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onComplete = {
                addWordsToBlockDB()
            }, onError = {
                Log.e("Error", it.message ?: "")
            })
    }

    fun addWordsToBlockDB() {
        sharedPreferences.dailyWords = 0
        disposables += loadDataInBlockDBUseCase
            .execute(blockWords)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onComplete = {
                openExercise.value = true
            }, onError = {
                Log.e("Error", it.message ?: "")
            })
    }

    fun listen() {
        engTextForListen.value = blockWords[indexWord].eng
    }


    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}