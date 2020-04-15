package com.szczecin.englishtamagotchi.view.learning

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.szczecin.englishtamagotchi.R
import com.szczecin.englishtamagotchi.adapter.repeat.ChooseCorrectWordsItemsAdapter
import com.szczecin.englishtamagotchi.common.ViewModelFactory
import com.szczecin.englishtamagotchi.common.extensions.lifecircle.observeLifecycleIn
import com.szczecin.englishtamagotchi.databinding.ActivityChooseCorrectWordBinding
import com.szczecin.englishtamagotchi.viewmodel.learning.ChooseCorrectWordsViewModel
import com.szczecin.englishtamagotchi.viewmodel.learning.RepeatingItemColor
import com.szczecin.englishtamagotchi.viewmodel.repeat.RepeatViewModel
import com.szczecin.pointofinterest.common.extensions.viewModel
import dagger.android.AndroidInjection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

const val SPAN_COUNT = 2

class ChooseCorrectWordsActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory<ChooseCorrectWordsViewModel>

    private val chooseCorrectWordsViewModel: ChooseCorrectWordsViewModel by viewModel { factory }

    private lateinit var binding: ActivityChooseCorrectWordBinding
    private lateinit var chooseCorrectWordsItemsAdapter: ChooseCorrectWordsItemsAdapter
    private val intentChooseCorrectWords = Intent()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setBinding()
        observeLifecycleIn(chooseCorrectWordsViewModel)
        initRecycler()
        observeViewModel()
        intentChooseCorrectWords.putExtra("activity_status", CHOOSE)
    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_correct_word)
        binding.chooseCorrectWordsViewModel = chooseCorrectWordsViewModel
        binding.lifecycleOwner = this@ChooseCorrectWordsActivity

        chooseCorrectWordsViewModel.finishLesson.observe(this, Observer {
            Snackbar.make(this.binding.root, "умничка! Задание сделано!", Snackbar.LENGTH_LONG)
                .show()
            GlobalScope.launch(Dispatchers.Main) {
                delay(2000)
                setResult(Activity.RESULT_OK, intentChooseCorrectWords)
                finish()
            }
        })
    }


    private fun initRecycler() {
        val recyclerPairEng = binding.recyclerRepeatWords
        recyclerPairEng.apply {
            layoutManager = GridLayoutManager(context, SPAN_COUNT)
            chooseCorrectWordsItemsAdapter = ChooseCorrectWordsItemsAdapter()
            this.adapter = chooseCorrectWordsItemsAdapter
            chooseCorrectWordsViewModel.subscribeForItemClick(chooseCorrectWordsItemsAdapter.getClickItemObserver())
        }
    }

    private fun observeViewModel() {
        chooseCorrectWordsViewModel.buttonEngColor.observe(this, Observer {
            chooseCorrectWordsItemsAdapter.updateItem(it)
            if (it == RepeatingItemColor.RED) {
                GlobalScope.launch(Dispatchers.Main) {
                    delay(1000)
                    chooseCorrectWordsItemsAdapter.updateItem(
                        RepeatingItemColor.DEFAULT
                    )
                }
            } else {
                GlobalScope.launch(Dispatchers.Main) {
                    delay(1000)
                    chooseCorrectWordsViewModel.nextWords.postValue(Unit)
                    chooseCorrectWordsItemsAdapter.updateItem(
                        RepeatingItemColor.DEFAULT
                    )
                }
            }
        })
    }

}
