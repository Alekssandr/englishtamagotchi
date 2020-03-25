package com.szczecin.englishtamagotchi.view

import android.content.Intent
import android.os.Bundle
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
import javax.inject.Inject

class OrdinaryCardChoiceActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory<OrdinaryCardChoiceViewModel>

    private val ordinaryCardViewModel: OrdinaryCardChoiceViewModel by viewModel { factory }

    private lateinit var binding: ActivityOrdinaryChoiceCardBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setBinding()
        observeViewModel()
        observeLifecycleIn(ordinaryCardViewModel)
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
    }

}
