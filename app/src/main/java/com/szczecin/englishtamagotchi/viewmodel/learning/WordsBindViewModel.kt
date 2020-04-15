package com.szczecin.englishtamagotchi.viewmodel.learning

import android.util.Log
import androidx.lifecycle.*
import com.szczecin.englishtamagotchi.adapter.CHOICE
import com.szczecin.englishtamagotchi.adapter.DEFAULT
import com.szczecin.englishtamagotchi.adapter.GREEN
import com.szczecin.englishtamagotchi.adapter.RED
import com.szczecin.englishtamagotchi.common.rx.RxSchedulers
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.preferencies.SettingsPreferences
import com.szczecin.englishtamagotchi.usecase.learn.GetLearnWordsByDayUseCase
import com.szczecin.englishtamagotchi.usecase.learn.GetLearnWordsUseCase
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import javax.inject.Inject


class WordsBindViewModel @Inject constructor(
    private val getLearnWordsUseCase: GetLearnWordsUseCase,
    private val getLearnWordsByDayUseCase: GetLearnWordsByDayUseCase,
    private val sharedPreferences: SettingsPreferences,
    private val schedulers: RxSchedulers
) : ViewModel(), LifecycleObserver {

    private val disposables = CompositeDisposable()
    val pairRusEngList: MutableLiveData<List<PairRusEng>> = MutableLiveData()
    val buttonEngColor = MutableLiveData<ButtonColorization>()
    val buttonRusColor = MutableLiveData<ButtonColorization>()
    val finishLesson = MutableLiveData<Unit>()
    var previousButton = DEFAULT
    var correctCount: Int = 0
    var sizeOfList: Int = 0

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        getAllWords()
    }

    private fun getAllWords() {
        disposables += getLearnWordsByDayUseCase
            .execute(sharedPreferences.newWordsPerDay)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribe({
                pairRusEngList.value = it
                sizeOfList = it.size
            },
                {
                    Log.e("Error", it.message ?: "")
                })
    }

    fun subscribeForItemClick(clickObserver: Observable<Int>) {
        disposables +=
            clickObserver
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.mainThread())
                .subscribe {
                    if (previousButton == DEFAULT) {
                        buttonEngColor.value =
                            ButtonColorization(
                                it,
                                CHOICE
                            )
                        previousButton = it
                    } else if (previousButton == it) {
                        correctCount += 1
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
                        isFinish()
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
                    if (previousButton == DEFAULT) {
                        buttonRusColor.value =
                            ButtonColorization(
                                it,
                                CHOICE
                            )
                        previousButton = it
                    } else if (previousButton == it) {
                        correctCount += 1
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
                        isFinish()
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

    private fun isFinish() {
        if (correctCount == sizeOfList) {
            finishLesson.postValue(Unit)
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
