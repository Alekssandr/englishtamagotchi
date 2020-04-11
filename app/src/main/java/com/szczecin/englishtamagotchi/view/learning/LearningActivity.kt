package com.szczecin.englishtamagotchi.view.learning

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.szczecin.englishtamagotchi.R
import com.szczecin.englishtamagotchi.common.ViewModelFactory
import com.szczecin.englishtamagotchi.common.extensions.lifecircle.observeLifecycleIn
import com.szczecin.englishtamagotchi.databinding.ActivityLearningBinding
import com.szczecin.englishtamagotchi.databinding.ActivityOrdinaryCardBinding
import com.szczecin.englishtamagotchi.view.ENG_TO_RUS
import com.szczecin.englishtamagotchi.view.repeat.RepeatActivity
import com.szczecin.englishtamagotchi.viewmodel.learning.LearningViewModel
import com.szczecin.englishtamagotchi.viewmodel.learning.WordsBindViewModel
import com.szczecin.pointofinterest.common.extensions.viewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_learning.*
import kotlinx.android.synthetic.main.activity_words_writing.*
import javax.inject.Inject

const val DEFAULT = -1
const val ENG_RUS = 0
const val RUS_ENG = 1
const val WRITING = 2
const val BIND = 3

class LearningActivity : AppCompatActivity() {

    private val SECOND_ACTIVITY_REQUEST_CODE = 0
    var allTaskCount = 0

    @Inject
    lateinit var factory: ViewModelFactory<LearningViewModel>

    private val learningViewModel: LearningViewModel by viewModel { factory }

    private lateinit var binding: ActivityLearningBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setBinding()
        observeLifecycleIn(learningViewModel)
    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_learning)
        binding.learningViewModel = learningViewModel
        binding.lifecycleOwner = this@LearningActivity

    }

    fun chooseCorrectExercise(view: View) {
        when (view.id) {
//            R.id.btn_choose_card -> startActivity(Intent(this, OrdinaryCardChoiceActivity::class.java))
            R.id.btn_eng_rus -> {
                openExerciseOneWord(true)
            }
            R.id.btn_rus_eng -> {
                openExerciseOneWord(false)
            }
            R.id.btn_writing -> startActivityForResult(
                Intent(
                    this,
                    WordsWritingActivity::class.java
                ), SECOND_ACTIVITY_REQUEST_CODE
            )
            R.id.btn_binding -> startActivityForResult(
                Intent(this, BindWordsActivity::class.java),
                SECOND_ACTIVITY_REQUEST_CODE
            )
        }
    }

    fun repeatExercise(view: View) {
        startActivity(Intent(this, RepeatActivity::class.java))
    }

    private fun openExerciseOneWord(isEngToRus: Boolean) {
        startActivityForResult(Intent(this, OrdinaryCardActivity::class.java).apply {
            putExtra(ENG_TO_RUS, isEngToRus)
        }, SECOND_ACTIVITY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                allTaskCount += 1
                when (data!!.getIntExtra("activity_status", DEFAULT)) {
                    ENG_RUS -> btn_eng_rus.setBackgroundColor(
                        ContextCompat.getColor(this, R.color.green)
                    )
                    RUS_ENG -> btn_rus_eng.setBackgroundColor(
                        ContextCompat.getColor(this, R.color.green)
                    )
                    WRITING -> btn_writing.setBackgroundColor(
                        ContextCompat.getColor(this, R.color.green)
                    )
                    BIND -> btn_binding.setBackgroundColor(
                        ContextCompat.getColor(this, R.color.green)
                    )
                }
                if (allTaskCount == 2) {
                    learningViewModel.updatedLearnedWords.postValue(Unit)
                }
            }
        }
    }
}
