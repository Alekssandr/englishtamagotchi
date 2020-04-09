package com.szczecin.englishtamagotchi.view.learning

import android.os.Bundle
import android.widget.Toast
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
import com.google.android.material.snackbar.Snackbar
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


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setBinding()
        observeLifecycleIn(wordsWritingViewModel)
        observeViewModel()
    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_words_writing)
        binding.wordsWritingViewModel = wordsWritingViewModel
        binding.lifecycleOwner = this@WordsWritingActivity

        wordsWritingViewModel.uiClosed.observe(this, Observer {
            Snackbar.make(this.binding.root, "умничка! Задание сделано!", Snackbar.LENGTH_LONG).show()
            GlobalScope.launch(Dispatchers.Main) {
                delay(2000)
                finish()
            }
        })
    }

    private fun observeViewModel() {
        wordsWritingViewModel.clearEditText.observe(this, Observer {
            writing_words.text.clear()
        })
    }

}
