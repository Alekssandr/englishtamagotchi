package com.szczecin.englishtamagotchi.view.learning

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.szczecin.englishtamagotchi.R
import com.szczecin.englishtamagotchi.common.ViewModelFactory
import com.szczecin.englishtamagotchi.common.extensions.lifecircle.observeLifecycleIn
import com.szczecin.englishtamagotchi.databinding.ActivityWordsWritingBinding
import com.szczecin.englishtamagotchi.viewmodel.learning.WordsWritingViewModel
import com.szczecin.pointofinterest.common.extensions.viewModel
import dagger.android.AndroidInjection
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.szczecin.englishtamagotchi.adapter.WordsWritingAdapter
import kotlinx.android.synthetic.main.activity_words_writing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class WordsWritingActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory<WordsWritingViewModel>

    private val wordsWritingViewModel: WordsWritingViewModel by viewModel { factory }

    private lateinit var binding: ActivityWordsWritingBinding
    private val intentWordsWriting = Intent()

    private lateinit var bindWordsWritingAdapter: WordsWritingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setBinding()
        observeLifecycleIn(wordsWritingViewModel)
        observeViewModel()
        intentWordsWriting.putExtra("activity_status", WRITING)
        initRecycler()
    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_words_writing)
        binding.wordsWritingViewModel = wordsWritingViewModel
        binding.lifecycleOwner = this@WordsWritingActivity

        wordsWritingViewModel.uiClosed.observe(this, Observer {
            Snackbar.make(this.binding.root, "умничка! Задание сделано!", Snackbar.LENGTH_LONG).show()
            GlobalScope.launch(Dispatchers.Main) {
                delay(2000)
                setResult(Activity.RESULT_OK, intentWordsWriting)
                finish()
            }
        })
    }

    private fun observeViewModel() {
        wordsWritingViewModel.clearEditText.observe(this, Observer {
            writing_words.text = ""
        })
    }

    private fun initRecycler() {
        val recyclerPairRus = binding.recyclerWordsWriting
        recyclerPairRus.apply {
            layoutManager =
                GridLayoutManager(this.context, SPAN_COUNT, RecyclerView.HORIZONTAL, true)

//            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            bindWordsWritingAdapter = WordsWritingAdapter()
            this.adapter = bindWordsWritingAdapter
            wordsWritingViewModel.subscribeForRusItemClick(bindWordsWritingAdapter.getClickItemObserver())
        }
    }

}
