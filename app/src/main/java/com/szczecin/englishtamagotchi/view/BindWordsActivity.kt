package com.szczecin.englishtamagotchi.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.szczecin.englishtamagotchi.R
import com.szczecin.englishtamagotchi.common.ViewModelFactory
import com.szczecin.englishtamagotchi.common.extensions.lifecircle.observeLifecycleIn
import com.szczecin.englishtamagotchi.databinding.ActivityBindWordsBinding
import com.szczecin.englishtamagotchi.viewmodel.WordsBindViewModel
import com.szczecin.pointofinterest.common.extensions.viewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class BindWordsActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory<WordsBindViewModel>

    private val wordsBindViewModel: WordsBindViewModel by viewModel { factory }

    private lateinit var binding: ActivityBindWordsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setBinding()
        observeLifecycleIn(wordsBindViewModel)
        observeViewModel()
    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bind_words)
        binding.wordsBindViewModel = wordsBindViewModel
        binding.lifecycleOwner = this@BindWordsActivity
    }

    private fun observeViewModel() {

    }

}
