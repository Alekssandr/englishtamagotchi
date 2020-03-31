package com.szczecin.englishtamagotchi.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.szczecin.englishtamagotchi.R
import com.szczecin.englishtamagotchi.common.ViewModelFactory
import com.szczecin.englishtamagotchi.common.extensions.lifecircle.observeLifecycleIn
import com.szczecin.englishtamagotchi.databinding.ActivityOrdinaryCardBinding
import com.szczecin.englishtamagotchi.databinding.ActivityWordsWritingBinding
import com.szczecin.englishtamagotchi.viewmodel.OrdinaryCardViewModel
import com.szczecin.englishtamagotchi.viewmodel.WordsWritingViewModel
import com.szczecin.pointofinterest.common.extensions.viewModel
import dagger.android.AndroidInjection
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_words_writing.*
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
    }

    private fun observeViewModel() {
        wordsWritingViewModel.clearEditText.observe(this, Observer {
            writing_words.text.clear()
        })
    }

}
