package com.szczecin.englishtamagotchi.viewmodel.learning

import android.util.Log
import androidx.lifecycle.*
import com.szczecin.englishtamagotchi.common.rx.RxSchedulers
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.preferencies.SettingsPreferences
import com.szczecin.englishtamagotchi.usecase.learn.GetLearnWordsByDayUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import javax.inject.Inject
import io.reactivex.Observable

class ChooseCorrectWordsViewModel @Inject constructor(
    private val sharedPreferences: SettingsPreferences,
    private val getLearnWordsByDayUseCase: GetLearnWordsByDayUseCase,

    private val schedulers: RxSchedulers
) : ViewModel(), LifecycleObserver {

    val pairRusEngList: MutableLiveData<List<PairRusEng>> = MutableLiveData()
    val openedWord: MutableLiveData<String> = MutableLiveData()
    val buttonEngColor = MutableLiveData<RepeatingItemColor>()

    private val disposables = CompositeDisposable()

    val finishLesson = MutableLiveData<Unit>()
    private val repeatWords: MutableList<PairRusEng> = mutableListOf()
    private var round = 0
    val nextWords = MutableLiveData<Unit>()


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        getAllWords()
        nextWords.observeForever {
            nextWords()
        }
    }

    private fun getAllWords() {
        disposables += getLearnWordsByDayUseCase
            .execute(sharedPreferences.newWordsPerDay)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribe({
                pairRusEngList.value = it
                repeatWords.addAll(it)
                nextWords()
            },
                {
                    Log.e("Error", it.message ?: "")
                })
    }

    private fun get5Words(
        pairRusEng: PairRusEng,
        repeatWords: MutableList<PairRusEng>
    ): MutableList<PairRusEng> {
        val repeatWordsBlockList: MutableList<PairRusEng> = repeatWords.shuffled().take(5).toMutableList()
        if (!repeatWordsBlockList.contains(pairRusEng)) {
            repeatWordsBlockList.removeAt(0)
            repeatWordsBlockList.add(pairRusEng)
            repeatWordsBlockList.shuffle()
        }
        return repeatWordsBlockList
    }


    private fun nextWords() {
        if (round < repeatWords.size) {
            openedWord.value = repeatWords[round].rus
            pairRusEngList.value = get5Words(repeatWords[round], repeatWords)
            round++
        } else {
            finishLesson.postValue(Unit)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    fun subscribeForItemClick(clickItemObserver: Observable<PairRusEng>) {
        disposables +=
            clickItemObserver
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.mainThread())
                .subscribe {
                    buttonEngColor.value =
                        if (it.rus == openedWord.value) RepeatingItemColor.GREEN else RepeatingItemColor.RED
                }
    }
}

enum class RepeatingItemColor(val color: String) {
    GREEN("green"), RED("red"), DEFAULT("gray")
}