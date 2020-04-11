package com.szczecin.englishtamagotchi.view.learning

import android.app.Activity
import android.content.Intent
import android.os.Bundle
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class OrdinaryCardActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory<OrdinaryCardViewModel>

    private val ordinaryCardViewModel: OrdinaryCardViewModel by viewModel { factory }

    private lateinit var binding: ActivityOrdinaryCardBinding

    private val intentOrdinaryCard = Intent()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setBinding()
        observeLifecycleIn(ordinaryCardViewModel)
        val isEngToRus = intent.getBooleanExtra(ENG_TO_RUS, true)
        ordinaryCardViewModel.setRusOrEng(isEngToRus)
        intentOrdinaryCard.putExtra("activity_status", if (isEngToRus) ENG_RUS else RUS_ENG)
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

}
