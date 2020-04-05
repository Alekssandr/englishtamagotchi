package com.szczecin.englishtamagotchi.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import com.szczecin.englishtamagotchi.R
import com.szczecin.englishtamagotchi.common.ViewModelFactory
import com.szczecin.englishtamagotchi.common.extensions.lifecircle.observeLifecycleIn
import com.szczecin.englishtamagotchi.databinding.ActivityMainBinding
import com.szczecin.englishtamagotchi.databinding.ActivityOrdinaryCardBinding
import com.szczecin.englishtamagotchi.preferencies.SettingsPreferences
import com.szczecin.englishtamagotchi.viewmodel.MainViewModel
import com.szczecin.englishtamagotchi.viewmodel.OrdinaryCardViewModel
import com.szczecin.pointofinterest.common.extensions.viewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

const val ENG_TO_RUS = "ENG_TO_RUS"
const val PER_DAY_5_WORDS = 5
const val PER_DAY_10_WORDS = 10
const val PER_DAY_15_WORDS = 15

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory<MainViewModel>

    private val mainViewModel: MainViewModel by viewModel { factory }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeLifecycleIn(mainViewModel)
        setBinding()
    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainViewModel = mainViewModel
        binding.lifecycleOwner = this@MainActivity
    }

    fun chooseCorrectExercise(view: View) {
        when(view.id){
            R.id.btn_choose_card -> startActivity(Intent(this, OrdinaryCardChoiceActivity::class.java))
            R.id.btn_eng_rus -> {
                openExerciseOneWord(true)
            }
            R.id.btn_rus_eng -> {
                openExerciseOneWord(false)
            }
            R.id.btn_writing -> startActivity(Intent(this, WordsWritingActivity::class.java))
            R.id.btn_binding -> startActivity(Intent(this, BindWordsActivity::class.java))
            R.id.new_words_per_day_5 -> mainViewModel.perDay5Words.value = PER_DAY_5_WORDS
            R.id.new_words_per_day_10 -> mainViewModel.perDay10Words.value = PER_DAY_10_WORDS
            R.id.new_words_per_day_15 -> mainViewModel.perDay15Words.value = PER_DAY_15_WORDS
        }
    }

    private fun openExerciseOneWord(isEngToRus: Boolean) {
        startActivity(Intent(this, OrdinaryCardActivity::class.java).apply {
           putExtra(ENG_TO_RUS, isEngToRus)
        })
    }
}
