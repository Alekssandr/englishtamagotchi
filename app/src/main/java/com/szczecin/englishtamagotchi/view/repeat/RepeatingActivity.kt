package com.szczecin.englishtamagotchi.view.repeat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.szczecin.englishtamagotchi.R
import com.szczecin.englishtamagotchi.common.ViewModelFactory
import com.szczecin.englishtamagotchi.common.extensions.lifecircle.observeLifecycleIn
import com.szczecin.englishtamagotchi.databinding.ActivityRepeatingBinding
import com.szczecin.englishtamagotchi.view.learning.CHOOSE
import com.szczecin.englishtamagotchi.view.learning.REPEATING
import com.szczecin.englishtamagotchi.viewmodel.OrdinaryCardChoiceViewModel
import com.szczecin.englishtamagotchi.viewmodel.repeat.RepeatViewModel
import com.szczecin.pointofinterest.common.extensions.viewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_repeating.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class RepeatingActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    @Inject
    lateinit var factory: ViewModelFactory<RepeatViewModel>

    private val repeatViewModel: RepeatViewModel by viewModel { factory }

    private lateinit var binding: ActivityRepeatingBinding

    private var tts: TextToSpeech? = null

    private val intentRepeating = Intent()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setBinding()
        observeViewModel()
        observeLifecycleIn(repeatViewModel)
        tts = TextToSpeech(this, this)
        intentRepeating.putExtra("activity_status", REPEATING)

    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_repeating)
        binding.repeatViewModel = repeatViewModel
        binding.lifecycleOwner = this@RepeatingActivity
    }

    private fun observeViewModel() {
        repeatViewModel.finishLesson.observe(this, Observer {
            Snackbar.make(this.binding.root, "умничка! Задание сделано!", Snackbar.LENGTH_LONG)
                .show()
            GlobalScope.launch(Dispatchers.Main) {
                delay(2000)
                setResult(Activity.RESULT_OK, intentRepeating)
                finish()
            }
        })
//        repeatViewModel.engTextForListen.observe(this, Observer {
//            speakOut(it)
//        })
    }

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            } else {
                button_listen?.isEnabled = true
            }

        } else {
            Log.e("TTS", "Initilization Failed!")
        }

    }

    private fun speakOut(eng: String) {
        tts?.speak(eng, TextToSpeech.QUEUE_FLUSH, null, "")
    }

}
