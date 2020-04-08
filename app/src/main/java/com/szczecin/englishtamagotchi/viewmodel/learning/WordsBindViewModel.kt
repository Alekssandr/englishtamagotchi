package com.szczecin.englishtamagotchi.viewmodel.learning

import android.util.Log
import androidx.lifecycle.*
import com.szczecin.englishtamagotchi.adapter.CHOICE
import com.szczecin.englishtamagotchi.adapter.DEFAULT
import com.szczecin.englishtamagotchi.adapter.GREEN
import com.szczecin.englishtamagotchi.adapter.RED
import com.szczecin.englishtamagotchi.common.rx.RxSchedulers
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.usecase.GetWordsBlockUseCase
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject


class WordsBindViewModel @Inject constructor(
    private val getWordsBlockUseCase: GetWordsBlockUseCase,
    private val schedulers: RxSchedulers
) : ViewModel(), LifecycleObserver {

    private val disposables = CompositeDisposable()
    val pairRusEngList: MutableLiveData<List<PairRusEng>> = MutableLiveData()
    val buttonEngColor = MutableLiveData<ButtonColorization>()
    val buttonRusColor = MutableLiveData<ButtonColorization>()
    val uiClosed = MutableLiveData<Unit>()
    var previousButton = DEFAULT
    var correctCount: Int = 0


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        getAllWords()
    }

    private fun getAllWords() {
        disposables += getWordsBlockUseCase
            .execute()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onSuccess = {
                pairRusEngList.value = it
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
                    if(previousButton == DEFAULT){
                        buttonEngColor.value =
                            ButtonColorization(
                                it,
                                CHOICE
                            )
                        previousButton = it
                    } else if(previousButton == it){
                        buttonEngColor.value =
                            ButtonColorization(
                                it,
                                GREEN
                            )
                        buttonRusColor.value =
                            ButtonColorization(
                                it,
                                GREEN
                            )
                        previousButton = DEFAULT
                    } else {
                        buttonEngColor.value =
                            ButtonColorization(
                                it,
                                RED
                            )
                        buttonRusColor.value =
                            ButtonColorization(
                                previousButton,
                                RED
                            )
                        previousButton = DEFAULT
                    }
                }
    }

    fun subscribeForRusItemClick(clickObserver: Observable<Int>) {
        disposables +=
            clickObserver
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.mainThread())
                .subscribe {
                    if(previousButton == DEFAULT){
                        buttonRusColor.value =
                            ButtonColorization(
                                it,
                                CHOICE
                            )
                        previousButton = it
                    } else if(previousButton == it){
                        correctCount = correctCount++
                        buttonEngColor.value =
                            ButtonColorization(
                                it,
                                GREEN
                            )
                        buttonRusColor.value =
                            ButtonColorization(
                                it,
                                GREEN
                            )
                        previousButton = DEFAULT
                    } else {
                        buttonRusColor.value =
                            ButtonColorization(
                                it,
                                RED
                            )
                        buttonEngColor.value =
                            ButtonColorization(
                                previousButton,
                                RED
                            )
                        previousButton = DEFAULT
                    }
                }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    data class ButtonColorization(
        val index: Int,
        val event: Int
    )
}
