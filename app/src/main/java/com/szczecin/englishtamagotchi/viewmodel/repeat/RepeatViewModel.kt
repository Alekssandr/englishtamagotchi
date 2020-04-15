package com.szczecin.englishtamagotchi.viewmodel.repeat

import android.util.Log
import androidx.lifecycle.*
import com.szczecin.englishtamagotchi.common.rx.RxSchedulers
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.preferencies.SettingsPreferences
import com.szczecin.englishtamagotchi.repo.LearnWordsRepository
import com.szczecin.englishtamagotchi.usecase.know.AddWordKnowUseCase
import com.szczecin.englishtamagotchi.usecase.learn.RemoveLearnItemUseCase
import com.szczecin.englishtamagotchi.usecase.learn.table.RemoveTableLearnItemUseCase
import com.szczecin.englishtamagotchi.usecase.learn.table.UpdateLearnTableWordUseCase
import com.szczecin.englishtamagotchi.usecase.repeating.RepeatingGetListWordsUseCase
import com.szczecin.englishtamagotchi.usecase.repeating.RepeatingInsertWordsUseCase
import com.szczecin.englishtamagotchi.usecase.repeating.RepeatingRemoveItemUseCase
import com.szczecin.englishtamagotchi.usecase.repeating.RepeatingUpdateWordsUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class RepeatViewModel @Inject constructor(
    private val sharedPreferences: SettingsPreferences,
    private val repeatingGetListWordsUseCase: RepeatingGetListWordsUseCase,
    private val repeatingRemoveItemUseCase: RepeatingRemoveItemUseCase,
    private val removeLearnItemUseCase: RemoveLearnItemUseCase,
    private val repeatingUpdateWordsUseCase: RepeatingUpdateWordsUseCase,
    private val learnWordsRepository: LearnWordsRepository,
    private val updateLearnTableWordUseCase: UpdateLearnTableWordUseCase,
    private val removeTableLearnItemUseCase: RemoveTableLearnItemUseCase,
    private val addWordKnowUseCase: AddWordKnowUseCase,
    private val schedulers: RxSchedulers
) : ViewModel(), LifecycleObserver {
    //
    val pairRusEngList: MutableLiveData<List<PairRusEng>> = MutableLiveData()
    val translateWordEng: MutableLiveData<String> = MutableLiveData()
    val translateWordRus: MutableLiveData<String> = MutableLiveData()
    val translateWordCloseVisibility = MutableLiveData<Boolean>().apply { value = false }
    private val disposables = CompositeDisposable()
    val finishLesson = MutableLiveData<Unit>()
    private val repeatWords: MutableList<PairRusEng> = mutableListOf()
    private var round = 0


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        getRepeatingWords()
    }

    private fun getRepeatingWords() {
        disposables += repeatingGetListWordsUseCase
            .execute(sharedPreferences.numberOfLearningDay)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribe({
                pairRusEngList.value = it
                repeatWords.addAll(it)
                translateWordCloseVisibility.value = false
                if (round < repeatWords.size) {
                    translateWordEng.value = repeatWords[round].eng
                    translateWordRus.value = repeatWords[round].rus
                }
            },
                {
                    Log.e("Error", it.message ?: "")
                })
    }

    fun repeatWordIn3days() {
        updateLearnTable(repeatWords[round], -1)
        updateWord(3)
    }


    fun repeatWordIn5days() {
        updateLearnTable(repeatWords[round], -1)
        repeatWords[round].countIn5daysRepeating++
        updateWord(5)
    }

    fun moveWordFromRepeatingToLearn() {
//        repeatWords[round].dayOfLearning = sharedPreferences.numberOfLearningDay + 1//?
//        repeatWords[round].dayOfLearning = 0//?
        disposables += repeatingRemoveItemUseCase
            .execute(repeatWords[round])
//            .andThen(
//                learnWordsRepository.updateLearnWords(listOf(repeatWords[round]))//remove
//            )
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onComplete = {
                Log.d(
                    "test1111",
                    "moved ${repeatWords[round].eng} to learn"
                )
                removeWordFromLearning(repeatWords[round])
//                updateLearnTable(repeatWords[round], 0)
                nextWords()
            }, onError = {
                Log.e("Error", it.message ?: "")
            })
    }

    fun translateCloseWord() {
        translateWordCloseVisibility.value = true
    }

    private fun updateWord(interval: Int) {
        disposables += repeatingUpdateWordsUseCase
            .execute(repeatWords[round], sharedPreferences.numberOfLearningDay + interval)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onComplete = {
                Log.d(
                    "test1111",
                    "repeat ${repeatWords[round].eng} in ${sharedPreferences.numberOfLearningDay}"
                )
                removeOldWordsFromList()
            }, onError = {
                Log.e("Error", it.message ?: "")
            })
    }

    private fun removeOldWordsFromList() {
        removeWordFromLearning(repeatWords[round])
        if (round < repeatWords.size) {
            if (repeatWords[round].countIn5daysRepeating >= 2) {
                removeWord(repeatWords[round])
            }
        }
        nextWords()
    }

    //3 and 5 move to don't know ->removed from repeating move to learning?
    private fun removeWord(pairRusEng: PairRusEng) {
        disposables += repeatingRemoveItemUseCase
            .execute(pairRusEng)
            .andThen(
                removeTableLearnItemUseCase.execute(pairRusEng.eng)
            ).andThen(
                addWordKnowUseCase.execute(pairRusEng)
            )
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onComplete = {
                Log.d(
                    "test1111",
                    "moved ${pairRusEng.eng} to learn"
                )
            }, onError = {
                Log.e("Error", it.message ?: "")
            })
    }


    private fun removeWordFromLearning(pairRusEng: PairRusEng) {
        disposables += removeLearnItemUseCase
            .execute(pairRusEng.eng)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onComplete = {
                Log.d(
                    "test1111",
                    "removed from learning"
                )
            }, onError = {
                Log.e("Error", it.message ?: "")
            })
    }

    private fun updateLearnTable(pairRusEng: PairRusEng, repeatingInDay: Int) {
        disposables += updateLearnTableWordUseCase
            .execute(pairRusEng.eng, repeatingInDay)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onComplete = {
                Log.d(
                    "test1111",
                    "removed from learning"
                )
            }, onError = {
                Log.e("Error", it.message ?: "")
            })
    }

    private fun nextWords() {
        translateWordCloseVisibility.value = false
        if (round < repeatWords.size - 1) {
            round++
            translateWordEng.value = repeatWords[round].eng
            translateWordRus.value = repeatWords[round].rus
        } else {
            finishLesson.postValue(Unit)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}