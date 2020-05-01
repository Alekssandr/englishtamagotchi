package com.szczecin.englishtamagotchi.view.addNewWord

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.szczecin.englishtamagotchi.R
import com.szczecin.englishtamagotchi.adapter.common.CommonGroupItemsAdapter
import com.szczecin.englishtamagotchi.adapter.common.CommonWordsItemsAdapter
import com.szczecin.englishtamagotchi.common.ViewModelFactory
import com.szczecin.englishtamagotchi.common.extensions.lifecircle.observeLifecycleIn
import com.szczecin.englishtamagotchi.databinding.ActivityAddOwnWordBinding
import com.szczecin.englishtamagotchi.databinding.ActivityCommonWordsBinding
import com.szczecin.englishtamagotchi.viewmodel.CommonWordsListViewModel
import com.szczecin.englishtamagotchi.viewmodel.addOwnWord.AddOwnWordViewModel
import com.szczecin.pointofinterest.common.extensions.viewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_add_own_word.*
import javax.inject.Inject

class AddOwnWordActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory<AddOwnWordViewModel>

    private val addOwnWordViewModel: AddOwnWordViewModel by viewModel { factory }

    private lateinit var binding: ActivityAddOwnWordBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setBinding()
        observeLifecycleIn(addOwnWordViewModel)
    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_own_word)
        binding.addOwnWordViewModel = addOwnWordViewModel
        binding.lifecycleOwner = this@AddOwnWordActivity

//        commonWordsListViewModel.uiClosed.observe(this, Observer {
//            finish()
//        })
    }

}
