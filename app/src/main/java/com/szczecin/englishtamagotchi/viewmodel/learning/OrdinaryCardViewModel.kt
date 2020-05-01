package com.szczecin.englishtamagotchi.viewmodel.learning

import android.util.Log
import androidx.lifecycle.*
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.usecase.GetWordsBlockUseCase
import com.szczecin.englishtamagotchi.common.rx.RxSchedulers
import com.szczecin.englishtamagotchi.preferencies.SettingsPreferences
import com.szczecin.englishtamagotchi.usecase.RemoveWordsBlockUseCase
import com.szczecin.englishtamagotchi.usecase.know.AddWordKnowUseCase
import com.szczecin.englishtamagotchi.usecase.learn.*
import com.szczecin.englishtamagotchi.usecase.learn.table.RemoveTableLearnItemUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class OrdinaryCardViewModel @Inject constructor(
    private val getLearnWordsByDayUseCase: GetLearnWordsByDayUseCase,
    private val removeLearnItemUseCase: RemoveLearnItemUseCase,
    private val addWordKnowUseCase: AddWordKnowUseCase,
    private val getListFromTableWordUseCase: GetListFromTableWordUseCase,
    private val addLearnWordUseCase: AddLearnWordUseCase,
    private val removeTableLearnItemUseCase: RemoveTableLearnItemUseCase,
    private val addDifferenceToLearnUseCase: AddDifferenceToLearnUseCase,
    private val insertToToLearnUseCase: InsertToToLearnUseCase,
    private val sharedPreferences: SettingsPreferences,
    private val schedulers: RxSchedulers
) : ViewModel(), LifecycleObserver {

    val translateWordOpen = MutableLiveData<String>()
    val translateWordClose = MutableLiveData<String>()
    val translateWordCloseVisibility = MutableLiveData<Boolean>()
    val listen = MutableLiveData<String>()
    val isRusHideVisibility = MutableLiveData<Boolean>()
    val uiClosed = MutableLiveData<Unit>()
    val blockWords: MutableList<PairRusEng> = mutableListOf()
    var isTranslateFromEng = true
    var addNewData = sharedPreferences.newWordsPerDay

    private val disposables = CompositeDisposable()
    var indexWord = 0

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        translateWordCloseVisibility.value = false
        getAllWords()
    }

    fun setRusOrEng(isFromEng: Boolean) {
        isTranslateFromEng = isFromEng
        if (isFromEng) {
            isRusHideVisibility.value = true
        }
    }

    private fun loadBlock(index: Int) {
        if (isTranslateFromEng) {
            translateWordOpen.value = blockWords[index].eng
            translateWordClose.value = blockWords[index].rus
        } else {
            translateWordOpen.value = blockWords[index].rus
            translateWordClose.value = blockWords[index].eng
        }
    }

    fun openWord() {
        translateWordCloseVisibility.value = true
    }

    fun next() {
        Log.d("test1111", "next: ${blockWords.size} && $indexWord")

        when {
            blockWords.size - 1 > indexWord -> {
                Log.d("test1111", "blockWords.size - 1 > indexWord")

                translateWordCloseVisibility.value = false
                indexWord++
                loadBlock(indexWord)

            }
            //осталось одно слово в blockWords лернинг тейбли и лернинг
            blockWords.size < sharedPreferences.newWordsPerDay -> {
//                indexWord = sharedPreferences.newWordsPerDay - 1
                Log.d("test1111", "blockWords.size < sharedPreferences.newWordsPerDa")

                addNewData = sharedPreferences.newWordsPerDay - blockWords.size
                addWordsFromTableToLearning()
            }
            else -> uiClosed.postValue(Unit)
        }

    }

    fun nextRemove() {
        Log.d("test1111", "next: ${blockWords.size} && $indexWord")

        when {
            blockWords.size - 1 > indexWord -> {
                Log.d("test1111", "blockWords.size - 1 > indexWord")

                translateWordCloseVisibility.value = false
//                indexWord++
                loadBlock(indexWord)

            }
            //осталось одно слово в blockWords лернинг тейбли и лернинг
            blockWords.size < sharedPreferences.newWordsPerDay -> {
//                indexWord = sharedPreferences.newWordsPerDay - 1
                Log.d("test1111", "blockWords.size < sharedPreferences.newWordsPerDa")

                addNewData = sharedPreferences.newWordsPerDay - blockWords.size
                addWordsFromTableToLearning()
            }
            else -> uiClosed.postValue(Unit)
        }

    }


    private fun addWordsFromTableToLearning() {
        disposables += getListFromTableWordUseCase
            .execute(sharedPreferences.newWordsPerDay)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribe( {
                Log.d("test1111", "addWordsFromTableToLearning it: $it")
                insertToToLearn(it)
            },{
                Log.e("test1111", "BUUUUG:${it.message}")
            })
    }

    private fun insertToToLearn(wordsForLearning: List<PairRusEng>) {
        disposables += insertToToLearnUseCase
            .execute(wordsForLearning)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onComplete = {
                Log.d("test111", "insertToToLearn")
                getAllWords()
            }, onError = {
                Log.e("test1111", it.message ?: "")
            })
    }

    fun getAllWords() {
        disposables += getLearnWordsByDayUseCase
            .execute(addNewData)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribe({
//                blockWords.clear()
                blockWords.addAll(it)
                Log.d("test1111", "getAllWords: ${blockWords.size}")
                loadBlock(indexWord)
            }, {
                Log.e("test1111", it.message ?: "")
            })
    }

    fun removeWord() {
//        addWordToKnow()
        removeWordFromLearn()
//        removeWordFromLearnTable(blockWords[indexWord].eng)
    }

    //удаляем из лернинг
    private fun removeWordFromLearn() {
        disposables += removeLearnItemUseCase
            .execute(blockWords[indexWord].eng)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onComplete = {
                Log.d("test111", "removeWordFromLearn^: ${blockWords[indexWord].eng} $indexWord")
                removeWordFromLearnTable(blockWords[indexWord].eng)
            }, onError = {
                Log.e("test1111", it.message ?: "")
            })
    }

    //удаляем из лернинг тейбли

    private fun removeWordFromLearnTable(eng: String) {
        disposables += removeTableLearnItemUseCase
            .execute(eng)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(onComplete = {
                //                Log.d("test111", eng)
                blockWords.removeAt(indexWord)
                Log.d("test111", "removeWordFromLearn before: ${indexWord}")

                if (indexWord < sharedPreferences.newWordsPerDay - 1 && indexWord != 0) indexWord--
                Log.d("test111", "removeWordFromLearn^: ${indexWord}")
                nextRemove()
            }, onError = {
                Log.e("Error 11", it.message ?: "")
            })
    }

//    fun addWordToKnow() {
//        Log.d("test111", "addWordToKnow${blockWords.size} and ${indexWord}")
//        disposables += addWordKnowUseCase
//            .execute(blockWords[indexWord])
//            .subscribeOn(schedulers.io())
//            .observeOn(schedulers.mainThread())
//            .subscribeBy(onComplete = {
////                removeWordFromLearn()
//            }, onError = {
//                Log.e("test1111 11", it.message ?: "")
//            })
//    }


//    fun getTranslate() {
//        disposables += translateWordUseCase
//            .execute()
//            .subscribeOn(schedulers.io())
//            .observeOn(schedulers.mainThread())
//            .subscribeBy(onSuccess = {
//                val a = it
//            }, onError = {
//                Log.e("Error", it.message ?: "")
//            })
//    }

    fun listenWord() {
        disposables += getLearnWordsByDayUseCase
            .execute(sharedPreferences.newWordsPerDay)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribe({
                //                blockWords.clear()
//                blockWords.addAll(it)
                val a = it
                Log.d("test1111", "listenWord: ${blockWords.size}")
//                loadBlock(indexWord)
            }, {
                Log.e("test1111", it.message ?: "")
            })
//        listen.value = blockWords[indexWord].eng
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}