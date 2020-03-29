package com.szczecin.englishtamagotchi.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.szczecin.englishtamagotchi.R
import com.szczecin.englishtamagotchi.common.ViewModelFactory
import com.szczecin.englishtamagotchi.common.extensions.lifecircle.observeLifecycleIn
import com.szczecin.englishtamagotchi.databinding.ActivityOrdinaryCardBinding
import com.szczecin.englishtamagotchi.viewmodel.OrdinaryCardViewModel
import com.szczecin.pointofinterest.common.extensions.viewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class OrdinaryCardActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory<OrdinaryCardViewModel>

    private val ordinaryCardViewModel: OrdinaryCardViewModel by viewModel { factory }

    private lateinit var binding: ActivityOrdinaryCardBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setBinding()
        observeLifecycleIn(ordinaryCardViewModel)
        val isEngToRus =intent.getBooleanExtra(ENG_TO_RUS, true)
        ordinaryCardViewModel.setRusOrEng(isEngToRus)
    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ordinary_card)
        binding.ordinaryCardViewModel = ordinaryCardViewModel
        binding.lifecycleOwner = this@OrdinaryCardActivity


    }

}
