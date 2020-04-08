package com.szczecin.englishtamagotchi.view.learn

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.szczecin.englishtamagotchi.R
import com.szczecin.englishtamagotchi.adapter.know.KnowLearnTableItemsAdapter
import com.szczecin.englishtamagotchi.common.ViewModelFactory
import com.szczecin.englishtamagotchi.common.extensions.lifecircle.observeLifecycleIn
import com.szczecin.englishtamagotchi.databinding.ActivityLearnTableBinding
import com.szczecin.englishtamagotchi.view.BaseActivity
import com.szczecin.englishtamagotchi.viewmodel.learn.LearnTableViewModel
import com.szczecin.pointofinterest.common.extensions.viewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class LearnTableActivity : BaseActivity() {

    @Inject
    lateinit var factory: ViewModelFactory<LearnTableViewModel>

    private val learnTableViewModel: LearnTableViewModel by viewModel { factory }

    private lateinit var binding: ActivityLearnTableBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setBinding()
        observeLifecycleIn(learnTableViewModel)
        initRecycler()
    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_learn_table)
        binding.learnTableViewModel = learnTableViewModel
        binding.lifecycleOwner = this@LearnTableActivity
    }

    private fun initRecycler() {
        binding.recyclerLearnKnowsWords.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            knowLearnTableItemsAdapter = KnowLearnTableItemsAdapter()
            this.adapter = knowLearnTableItemsAdapter
        }
        learnTableViewModel.subscribeForRemoveItem(knowLearnTableItemsAdapter.getClickRemoveItemObserver())
        learnTableViewModel.subscribeAddItem(knowLearnTableItemsAdapter.getAddItemObserver())
        val itemTouchHelperCallback = setTouchHelper()
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerLearnKnowsWords)
    }

}
