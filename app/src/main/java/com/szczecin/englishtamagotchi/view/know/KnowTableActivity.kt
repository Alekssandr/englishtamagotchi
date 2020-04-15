package com.szczecin.englishtamagotchi.view.know

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.szczecin.englishtamagotchi.R
import com.szczecin.englishtamagotchi.adapter.know.KnowLearnTableItemsAdapter
import com.szczecin.englishtamagotchi.common.ViewModelFactory
import com.szczecin.englishtamagotchi.common.extensions.lifecircle.observeLifecycleIn
import com.szczecin.englishtamagotchi.databinding.ActivityKnowTableBinding
import com.szczecin.englishtamagotchi.view.BaseActivity
import com.szczecin.englishtamagotchi.viewmodel.know.KnowTableViewModel
import com.szczecin.pointofinterest.common.extensions.viewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class KnowTableActivity : BaseActivity() {

    @Inject
    lateinit var factory: ViewModelFactory<KnowTableViewModel>

    private val knowTableViewModel: KnowTableViewModel by viewModel { factory }

    private lateinit var binding: ActivityKnowTableBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setBinding()
        observeLifecycleIn(knowTableViewModel)
        initRecycler()
    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_know_table)
        binding.knowTableViewModel = knowTableViewModel
        binding.lifecycleOwner = this@KnowTableActivity
    }

    private fun initRecycler() {
        binding.recyclerLearnKnowsWords.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            knowLearnTableItemsAdapter = KnowLearnTableItemsAdapter()
            this.adapter = knowLearnTableItemsAdapter
        }
        knowTableViewModel.subscribeForRemoveItem(knowLearnTableItemsAdapter.getClickRemoveItemObserver())
        knowTableViewModel.subscribeAddItem(knowLearnTableItemsAdapter.getAddItemObserver())
        val itemTouchHelperCallback = setTouchHelper()
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerLearnKnowsWords)
    }

}
