package com.szczecin.englishtamagotchi.view.learning

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.szczecin.englishtamagotchi.R
import com.szczecin.englishtamagotchi.common.ViewModelFactory
import com.szczecin.englishtamagotchi.common.extensions.lifecircle.observeLifecycleIn
import com.szczecin.englishtamagotchi.databinding.ActivityOrdinaryCardBinding
import com.szczecin.englishtamagotchi.view.ENG_TO_RUS
import com.szczecin.englishtamagotchi.viewmodel.learning.OrdinaryCardViewModel
import com.szczecin.pointofinterest.common.extensions.viewModel
import dagger.android.AndroidInjection
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_repeating.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class OrdinaryCardActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    @Inject
    lateinit var factory: ViewModelFactory<OrdinaryCardViewModel>

    private val ordinaryCardViewModel: OrdinaryCardViewModel by viewModel { factory }

    private lateinit var binding: ActivityOrdinaryCardBinding

    private val intentOrdinaryCard = Intent()

    private var tts: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setBinding()
        observeLifecycleIn(ordinaryCardViewModel)
        tts = TextToSpeech(this, this)
        val isEngToRus = intent.getBooleanExtra(ENG_TO_RUS, true)
        ordinaryCardViewModel.setRusOrEng(isEngToRus)
        intentOrdinaryCard.putExtra("activity_status", if (isEngToRus) ENG_RUS else RUS_ENG)
        ordinaryCardViewModel.listen.observe(this, Observer {
            speakOut(it)
        })
    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ordinary_card)
        binding.ordinaryCardViewModel = ordinaryCardViewModel
        binding.lifecycleOwner = this@OrdinaryCardActivity
        ordinaryCardViewModel.uiClosed.observe(this, Observer {
            Snackbar.make(this.binding.root, "умничка! Задание сделано!", Snackbar.LENGTH_LONG)
                .show()
            GlobalScope.launch(Dispatchers.Main) {
                delay(2000)
                setResult(Activity.RESULT_OK, intentOrdinaryCard)
                finish()
            }
        })
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
