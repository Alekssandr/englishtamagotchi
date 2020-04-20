package com.szczecin.englishtamagotchi.view.learning

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.szczecin.englishtamagotchi.R
import com.szczecin.englishtamagotchi.common.ViewModelFactory
import com.szczecin.englishtamagotchi.common.extensions.lifecircle.observeLifecycleIn
import com.szczecin.englishtamagotchi.databinding.ActivityLearningBinding
import com.szczecin.englishtamagotchi.view.ENG_TO_RUS
import com.szczecin.englishtamagotchi.view.repeat.RepeatingActivity
import com.szczecin.englishtamagotchi.viewmodel.learning.LearningViewModel
import com.szczecin.pointofinterest.common.extensions.viewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_learning.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

const val DEFAULT = -1
const val ENG_RUS = 0
const val RUS_ENG = 1
const val WRITING = 2
const val BIND = 3
const val CHOOSE = 4
const val REPEATING = 5
const val LEARNING = 6

class LearningActivity : AppCompatActivity() {

    private val SECOND_ACTIVITY_REQUEST_CODE = 0
    var allTaskCount = 0

    @Inject
    lateinit var factory: ViewModelFactory<LearningViewModel>

    private val learningViewModel: LearningViewModel by viewModel { factory }

    private lateinit var binding: ActivityLearningBinding

    private val intentLearning = Intent()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setBinding()
        observeLifecycleIn(learningViewModel)
        openRepeatExercise()
        intentLearning.putExtra("activity_status", LEARNING)
    }

    private fun openRepeatExercise() {
//        learningViewModel.updatedLearnedWords.observe(this, Observer {
//            startActivity(Intent(this, RepeatingActivity::class.java))
//        })
    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_learning)
        binding.learningViewModel = learningViewModel
        binding.lifecycleOwner = this@LearningActivity
//        learningViewModel.updatedLearnedWords.postValue(Unit)
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
            R.id.btn_choose -> startActivityForResult(
                Intent(
                    this,
                    ChooseCorrectWordsActivity::class.java
                ), SECOND_ACTIVITY_REQUEST_CODE
            )
        }
    }

//    fun repeatExercise(view: View) {
//        startActivity(Intent(this, RepeatingActivity::class.java))
//    }

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
                    CHOOSE -> btn_binding.setBackgroundColor(
                        ContextCompat.getColor(this, R.color.green)
                    )
                }
                intentLearning.putExtra("completed_tasks", allTaskCount)
                setResult(Activity.RESULT_OK, intentLearning)

//                if (allTaskCount == 0) {
//                    learningViewModel.updatedLearnedWords.postValue(Unit)
//                }
            }
        }


    }
}
