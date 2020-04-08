package com.szczecin.englishtamagotchi.view.learning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.szczecin.englishtamagotchi.R
import com.szczecin.englishtamagotchi.view.ENG_TO_RUS
import dagger.android.AndroidInjection


class LearningActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learning)
    }


    fun chooseCorrectExercise(view: View) {
        when(view.id){
//            R.id.btn_choose_card -> startActivity(Intent(this, OrdinaryCardChoiceActivity::class.java))
            R.id.btn_eng_rus -> {
                openExerciseOneWord(true)
            }
            R.id.btn_rus_eng -> {
                openExerciseOneWord(false)
            }
            R.id.btn_writing -> startActivity(Intent(this, WordsWritingActivity::class.java))
            R.id.btn_binding -> startActivity(Intent(this, BindWordsActivity::class.java))
        }
    }

    private fun openExerciseOneWord(isEngToRus: Boolean) {
        startActivity(Intent(this, OrdinaryCardActivity::class.java).apply {
           putExtra(ENG_TO_RUS, isEngToRus)
        })
    }
}
