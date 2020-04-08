package com.szczecin.englishtamagotchi.view.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.szczecin.englishtamagotchi.R
import com.szczecin.englishtamagotchi.adapter.BindWordsEngItemsAdapter
import com.szczecin.englishtamagotchi.adapter.BindWordsRusItemsAdapter
import com.szczecin.englishtamagotchi.adapter.common.CommonWordsItemsAdapter
import com.szczecin.englishtamagotchi.common.ViewModelFactory
import com.szczecin.englishtamagotchi.common.extensions.lifecircle.observeLifecycleIn
import com.szczecin.englishtamagotchi.databinding.ActivityCommonWordsBinding
import com.szczecin.englishtamagotchi.viewmodel.CommonWordsListViewModel
import com.szczecin.pointofinterest.common.extensions.viewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class CommonWordsActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory<CommonWordsListViewModel>

    private val commonWordsListViewModel: CommonWordsListViewModel by viewModel { factory }

    private lateinit var binding: ActivityCommonWordsBinding

    private lateinit var commonWordsItemsAdapter: CommonWordsItemsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setBinding()
        observeLifecycleIn(commonWordsListViewModel)
        initRecycler()
    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_common_words)
        binding.commonWordsListViewModel = commonWordsListViewModel
        binding.lifecycleOwner = this@CommonWordsActivity
    }

    private fun initRecycler() {
        binding.recyclerCommonWords.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            commonWordsItemsAdapter = CommonWordsItemsAdapter()
            this.adapter = commonWordsItemsAdapter
            commonWordsListViewModel.subscribeForItemClick(commonWordsItemsAdapter.getClickItemObserver())
        }
    }

}
