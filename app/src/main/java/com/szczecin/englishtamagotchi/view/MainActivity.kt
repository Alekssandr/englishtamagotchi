package com.szczecin.englishtamagotchi.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.szczecin.englishtamagotchi.R
import com.szczecin.englishtamagotchi.common.ViewModelFactory
import com.szczecin.englishtamagotchi.common.extensions.lifecircle.observeLifecycleIn
import com.szczecin.englishtamagotchi.databinding.ActivityMainBinding
import com.szczecin.englishtamagotchi.utils.LEVEL_A_1
import com.szczecin.englishtamagotchi.utils.LEVEL_A_2
import com.szczecin.englishtamagotchi.utils.LEVEL_B_1
import com.szczecin.englishtamagotchi.utils.LEVEL_B_2
import com.szczecin.englishtamagotchi.view.common.CommonWordsActivity
import com.szczecin.englishtamagotchi.view.know.KnowTableActivity
import com.szczecin.englishtamagotchi.view.learn.LearnTableActivity
import com.szczecin.englishtamagotchi.view.learning.*
import com.szczecin.englishtamagotchi.view.repeat.RepeatingActivity
import com.szczecin.englishtamagotchi.viewmodel.MainViewModel
import com.szczecin.pointofinterest.common.extensions.viewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_learning.*
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

const val ENG_TO_RUS = "ENG_TO_RUS"
const val PER_DAY_5_WORDS = 5
const val PER_DAY_10_WORDS = 10
const val PER_DAY_15_WORDS = 15
const val SECOND_ACTIVITY_REQUEST_CODE = 0

class MainActivity : AppCompatActivity() {


    @Inject
    lateinit var factory: ViewModelFactory<MainViewModel>

    private val mainViewModel: MainViewModel by viewModel { factory }

    private lateinit var binding: ActivityMainBinding
    var isOpenRepeat: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learning)
        observeLifecycleIn(mainViewModel)
        setBinding()
        openRepeatExercise()
    }

//    override fun onStart() {
//        super.onStart()
//        isOpenRepeat = false
//
//    }
//    override fun OnStart() {
//        super.onResume()
//        isOpenRepeat = false
//    }

    private fun openRepeatExercise() {
        mainViewModel.updatedLearnedWords.observe(this, Observer {
            isOpenRepeat = true
            btn_start_learning.text = resources.getText(R.string.exam)
        })
    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainViewModel = mainViewModel
        binding.lifecycleOwner = this@MainActivity

    }

    fun chooseCorrectExercise(view: View) {
        when (view.id) {
            R.id.btn_1000_words -> startActivity(Intent(this, CommonWordsActivity::class.java))
            R.id.btn_know -> startActivity(Intent(this, KnowTableActivity::class.java))
            R.id.btn_learn -> startActivity(Intent(this, LearnTableActivity::class.java))
            R.id.btn_start_learning -> startActivityForResult(
                Intent(
                    this,
                    if (isOpenRepeat) RepeatingActivity::class.java else LearningActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP),
                SECOND_ACTIVITY_REQUEST_CODE
            )
            R.id.new_words_per_day_5 -> mainViewModel.perDay5Words.value = PER_DAY_5_WORDS
            R.id.new_words_per_day_10 -> mainViewModel.perDay10Words.value = PER_DAY_10_WORDS
            R.id.new_words_per_day_15 -> mainViewModel.perDay15Words.value = PER_DAY_15_WORDS
            R.id.btn_lvl_a1 -> {
                mainViewModel.level_a1.value = LEVEL_A_1
                startActivity(Intent(this, CommonWordsActivity::class.java))
            }
            R.id.btn_lvl_a2 -> {
                mainViewModel.level_a1.value = LEVEL_A_2
                startActivity(Intent(this, CommonWordsActivity::class.java))
            }
            R.id.btn_lvl_b1 -> {
                mainViewModel.level_a1.value = LEVEL_B_1
                startActivity(Intent(this, CommonWordsActivity::class.java))
            }
            R.id.btn_lvl_b2 -> {
                mainViewModel.level_a1.value = LEVEL_B_2
                startActivity(Intent(this, CommonWordsActivity::class.java))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                when (data!!.getIntExtra("activity_status", DEFAULT)) {
                    REPEATING -> {
                        isOpenRepeat = false
                        btn_start_learning.text = resources.getText(R.string.learning)
                    }

//                        startActivity(
//                            Intent(
//                                this,
//                                LearningActivity::class.java
//                            ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                        )
                    LEARNING -> {
                        val completedTasks = (data.getIntExtra("completed_tasks", DEFAULT))
                        if (completedTasks > 0) {
                            mainViewModel.updatedRepeating.postValue(Unit)
                            isOpenRepeat = true
                            btn_start_learning.text = resources.getText(R.string.exam)
                        }
                    }

                }
            }
        }


    }
}
