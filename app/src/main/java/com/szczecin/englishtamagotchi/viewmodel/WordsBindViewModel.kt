package com.szczecin.englishtamagotchi.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.szczecin.englishtamagotchi.R
import com.szczecin.englishtamagotchi.common.rx.RxSchedulers
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.usecase.GetWordsBlockUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject


class WordsBindViewModel @Inject constructor(
    private val getWordsBlockUseCase: GetWordsBlockUseCase,
    private val schedulers: RxSchedulers
) : ViewModel(), LifecycleObserver {

    val wordEngFirst = MutableLiveData<String>()
    val wordEngSecond = MutableLiveData<String>()
    val wordEngThird = MutableLiveData<String>()
    val wordEngFourth = MutableLiveData<String>()
    val wordEngFifth = MutableLiveData<String>()
    val wordRusFirst = MutableLiveData<String>()
    val wordRusSecond = MutableLiveData<String>()
    val wordRusThird = MutableLiveData<String>()
    val wordRusFourth = MutableLiveData<String>()
    val wordRusFifth = MutableLiveData<String>()

    val wordEngFirstColor = MutableLiveData<Int>().apply { value = R.color.grey }
    val wordEngSecondColor = MutableLiveData<Int>().apply { value = R.color.grey }
    val wordEngThirdColor = MutableLiveData<Int>().apply { value = R.color.grey }
    val wordEngFourthColor = MutableLiveData<Int>().apply { value = R.color.grey }
    val wordEngFifthColor = MutableLiveData<Int>().apply { value = R.color.grey }
    val wordRusFirstColor = MutableLiveData<Int>().apply { value = R.color.grey }
    val wordRusSecondColor = MutableLiveData<Int>().apply { value = R.color.grey }
    val wordRusThirdColor = MutableLiveData<Int>().apply { value = R.color.grey }
    val wordRusFourthColor = MutableLiveData<Int>().apply { value = R.color.grey }
    val wordRusFifthColor = MutableLiveData<Int>().apply { value = R.color.grey }

    private var translateFinishEngEvent = BehaviorSubject.create<EngRusPairCheck>()
    private var translateFinishRusEvent = BehaviorSubject.create<EngRusPairCheck>()

    val blockWords: MutableList<PairRusEng> = mutableListOf()
    var isEngClick = MutableLiveData<Boolean>().apply { value = false }
    var isRusClick = MutableLiveData<Boolean>().apply { value = false }
    lateinit var engButton: PairRusEng

    private val disposables = CompositeDisposable()


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        getAllWords()
        disposables += translateFinishEngEvent
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onNext = {
                if (engButton.eng == it.pairRusEngFirst.eng) {
                    checkTrue(engButton)
                } else {
                    checkFalseEngRus(it.pairRusEngSecond, it.pairRusEngFirst)
                }
                isEngClick.value = false
                isRusClick.value = false
            }, onError = {
                Log.e("Recording", "insert onError: ${it.message}")
            })

        disposables += translateFinishRusEvent
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onNext = {
                if (engButton.eng == it.pairRusEngFirst.eng) {
                    checkTrue(engButton)
                } else {
                    checkFalseEngRus(it.pairRusEngFirst, it.pairRusEngSecond)
                }
                isEngClick.value = false
                isRusClick.value = false
            }, onError = {
                Log.e("Recording", "insert onError: ${it.message}")
            })
    }

    private fun checkFalseEngRus(
        pairFirst: PairRusEng,
        pairSecond: PairRusEng
    ) {
        when (blockWords.indexOf(pairFirst)) {
            0 -> {
                wordEngFirstColor.value = R.color.red
            }
            1 -> {
                wordEngSecondColor.value = R.color.red
            }
            2 -> {
                wordEngThirdColor.value = R.color.red
            }
            3 -> {
                wordEngFourthColor.value = R.color.red
            }
            4 -> {
                wordEngFifthColor.value = R.color.red
            }
        }
        when (blockWords.indexOf(pairSecond)) {
            0 -> {
                wordRusFirstColor.value = R.color.red
            }
            1 -> {
                wordRusSecondColor.value = R.color.red
            }
            2 -> {
                wordRusThirdColor.value = R.color.red
            }
            3 -> {
                wordRusFourthColor.value = R.color.red
            }
            4 -> {
                wordRusFifthColor.value = R.color.red
            }
        }
    }

    private fun getAllWords() {
        disposables += getWordsBlockUseCase
            .execute()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onSuccess = {
                blockWords.addAll(it)
                if (it.size == 5) {
                    wordEngFirst.value = it[0].eng
                    wordRusFirst.value = it[0].rus
                    wordEngSecond.value = it[1].eng
                    wordRusSecond.value = it[1].rus
                    wordEngThird.value = it[2].eng
                    wordRusThird.value = it[2].rus
                    wordEngFourth.value = it[3].eng
                    wordRusFourth.value = it[3].rus
                    wordEngFifth.value = it[4].eng
                    wordRusFifth.value = it[4].rus
                }

            }, onError = {
                Log.e("Error", it.message ?: "")
            })
    }

    fun buttonEngClick(view: View) {
        if (isEngClick.value == false) {
            when (view.id) {
                R.id.buttonWordEngFirst -> {
                    wordEngFirstColor.value = R.color.choice
                    if (isRusClick.value == true) {
                        translateFinishRusEvent.onNext(EngRusPairCheck(blockWords[0], engButton))
                    } else {
                        engButton = blockWords[0]
                    }
                }
                R.id.buttonWordEngSecond -> {
                    wordEngSecondColor.value = R.color.choice
                    if (isRusClick.value == true) {
                        translateFinishRusEvent.onNext(EngRusPairCheck(blockWords[1], engButton))
                    } else {
                        engButton = blockWords[1]
                    }
                }
                R.id.buttonWordEngThird -> {
                    wordEngThirdColor.value = R.color.choice
                    if (isRusClick.value == true) {
                        translateFinishRusEvent.onNext(EngRusPairCheck(blockWords[2], engButton))
                    } else {
                        engButton = blockWords[2]
                    }
                }
                R.id.buttonWordEngFourth -> {
                    wordEngFourthColor.value = R.color.choice
                    if (isRusClick.value == true) {
                        translateFinishRusEvent.onNext(EngRusPairCheck(blockWords[3], engButton))
                    } else {
                        engButton = blockWords[3]
                    }
                }
                R.id.buttonwWordEngFifth -> {
                    wordEngFifthColor.value = R.color.choice
                    if (isRusClick.value == true) {
                        translateFinishRusEvent.onNext(EngRusPairCheck(blockWords[4], engButton))
                    } else {
                        engButton = blockWords[4]
                    }
                }
            }
            isEngClick.value = true
        }

    }

    fun buttonRusClick(view: View) {
        if (isRusClick.value == false) {
            when (view.id) {
                R.id.buttonWordRusFirst -> {
                    wordRusFirstColor.value = R.color.choice
                    if (isEngClick.value == true) {
                        translateFinishEngEvent.onNext(EngRusPairCheck(blockWords[0], engButton))
                    } else {
                        engButton = blockWords[0]
                    }
                }
                R.id.buttonWordRusSecond -> {
                    wordRusSecondColor.value = R.color.choice
                    if (isEngClick.value == true) {
                        translateFinishEngEvent.onNext(EngRusPairCheck(blockWords[1], engButton))
                    } else {
                        engButton = blockWords[1]
                    }
                }
                R.id.buttonWordRusThird -> {
                    wordRusThirdColor.value = R.color.choice
                    if (isEngClick.value == true) {
                        translateFinishEngEvent.onNext(EngRusPairCheck(blockWords[2], engButton))
                    } else {
                        engButton = blockWords[2]
                    }
                }
                R.id.buttonWordRusFourth -> {
                    wordRusFourthColor.value = R.color.choice
                    if (isEngClick.value == true) {
                        translateFinishEngEvent.onNext(EngRusPairCheck(blockWords[3], engButton))
                    } else {
                        engButton = blockWords[3]
                    }
                }
                R.id.buttonWordRusFifth -> {
                    wordRusFifthColor.value = R.color.choice
                    if (isEngClick.value == true) {
                        translateFinishEngEvent.onNext(EngRusPairCheck(blockWords[4], engButton))
                    } else {
                        engButton = blockWords[4]
                    }
                }
            }
            isRusClick.value = true


        }

    }

    private fun checkTrue(engButton: PairRusEng) {
        when (blockWords.indexOf(engButton)) {
            0 -> {
                wordEngFirstColor.value = R.color.green
                wordRusFirstColor.value = R.color.green
            }
            1 -> {
                wordEngSecondColor.value = R.color.green
                wordRusSecondColor.value = R.color.green
            }
            2 -> {
                wordEngThirdColor.value = R.color.green
                wordRusThirdColor.value = R.color.green
            }
            3 -> {
                wordEngFourthColor.value = R.color.green
                wordRusFourthColor.value = R.color.green
            }
            4 -> {
                wordEngFifthColor.value = R.color.green
                wordRusFifthColor.value = R.color.green
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    data class EngRusPairCheck(
        val pairRusEngFirst: PairRusEng,
        val pairRusEngSecond: PairRusEng
    )

}
