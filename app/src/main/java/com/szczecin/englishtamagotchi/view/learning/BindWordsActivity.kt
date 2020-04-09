package com.szczecin.englishtamagotchi.view.learning

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.szczecin.englishtamagotchi.R
import com.szczecin.englishtamagotchi.adapter.BindWordsEngItemsAdapter
import com.szczecin.englishtamagotchi.common.ViewModelFactory
import com.szczecin.englishtamagotchi.common.extensions.lifecircle.observeLifecycleIn
import com.szczecin.englishtamagotchi.databinding.ActivityBindWordsBinding
import com.szczecin.englishtamagotchi.viewmodel.learning.WordsBindViewModel
import com.szczecin.pointofinterest.common.extensions.viewModel
import dagger.android.AndroidInjection
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.szczecin.englishtamagotchi.adapter.BindWordsRusItemsAdapter
import com.szczecin.englishtamagotchi.adapter.RED
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class BindWordsActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory<WordsBindViewModel>

    private val wordsBindViewModel: WordsBindViewModel by viewModel { factory }

    private lateinit var binding: ActivityBindWordsBinding

    private lateinit var bindWordsEngItemsAdapter: BindWordsEngItemsAdapter
    private lateinit var bindWordsRusItemsAdapter: BindWordsRusItemsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setBinding()
        observeLifecycleIn(wordsBindViewModel)
        initRecycler()
        observeViewModel()
    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bind_words)
        binding.wordsBindViewModel = wordsBindViewModel
        binding.lifecycleOwner = this@BindWordsActivity

        wordsBindViewModel.finishLesson.observe(this, Observer {
            Snackbar.make(this.binding.root, "умничка! Задание сделано!", Snackbar.LENGTH_LONG).show()
            GlobalScope.launch(Dispatchers.Main) {
                delay(2000)
                finish()
            }
        })

    }

    private fun initRecycler() {
        val recyclerPairEng = binding.recyclerPairEng
        recyclerPairEng.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            bindWordsEngItemsAdapter = BindWordsEngItemsAdapter()
            this.adapter = bindWordsEngItemsAdapter
            wordsBindViewModel.subscribeForItemClick(bindWordsEngItemsAdapter.getClickItemObserver())
        }
        val recyclerPairRus = binding.recyclerPairRus
        recyclerPairRus.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            bindWordsRusItemsAdapter = BindWordsRusItemsAdapter()
            this.adapter = bindWordsRusItemsAdapter
            wordsBindViewModel.subscribeForRusItemClick(bindWordsRusItemsAdapter.getClickItemObserver())
        }
    }

    private fun observeViewModel() {
        wordsBindViewModel.buttonEngColor.observe(this, Observer {
            bindWordsEngItemsAdapter.updateItem(it)
            if(it.event == RED){
                GlobalScope.launch(Dispatchers.Main) {
                    delay(1000)
                    bindWordsEngItemsAdapter.updateItem(WordsBindViewModel.ButtonColorization(it.index, 0))
                }
            }
        })
        wordsBindViewModel.buttonRusColor.observe(this, Observer {
            bindWordsRusItemsAdapter.updateItem(it)
            if(it.event == RED){
                GlobalScope.launch(Dispatchers.Main) {
                    delay(1000)
                    bindWordsRusItemsAdapter.updateItem(WordsBindViewModel.ButtonColorization(it.index, 0))
                }
            }
        })
    }

}
