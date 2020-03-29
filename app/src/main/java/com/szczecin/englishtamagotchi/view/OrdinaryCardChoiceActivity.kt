package com.szczecin.englishtamagotchi.view

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.szczecin.englishtamagotchi.R
import com.szczecin.englishtamagotchi.common.ViewModelFactory
import com.szczecin.englishtamagotchi.common.extensions.lifecircle.observeLifecycleIn
import com.szczecin.englishtamagotchi.databinding.ActivityOrdinaryChoiceCardBinding
import com.szczecin.englishtamagotchi.viewmodel.OrdinaryCardChoiceViewModel
import com.szczecin.pointofinterest.common.extensions.viewModel
import dagger.android.AndroidInjection
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_ordinary_choice_card.*
import java.util.*
import javax.inject.Inject

class OrdinaryCardChoiceActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    @Inject
    lateinit var factory: ViewModelFactory<OrdinaryCardChoiceViewModel>

    private val ordinaryCardViewModel: OrdinaryCardChoiceViewModel by viewModel { factory }

    private lateinit var binding: ActivityOrdinaryChoiceCardBinding

    private var tts: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setBinding()
        observeViewModel()
        observeLifecycleIn(ordinaryCardViewModel)
        tts = TextToSpeech(this, this)
    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ordinary_choice_card)
        binding.ordinaryCardViewModel = ordinaryCardViewModel
        binding.lifecycleOwner = this@OrdinaryCardChoiceActivity
    }

    private fun observeViewModel() {
        ordinaryCardViewModel.openExercise.observe(this, Observer {
            startActivity(Intent(this, OrdinaryCardActivity::class.java))
        })
        ordinaryCardViewModel.engTextForListen.observe(this, Observer {
            speakOut(it)
        })
    }

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
            } else {
                button_listen?.isEnabled = true
            }

        } else {
            Log.e("TTS", "Initilization Failed!")
        }

    }

    private fun speakOut(eng: String) {
        tts?.speak(eng, TextToSpeech.QUEUE_FLUSH, null,"")
    }

}
