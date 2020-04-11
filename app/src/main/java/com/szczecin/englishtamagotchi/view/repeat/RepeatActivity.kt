package com.szczecin.englishtamagotchi.view.repeat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.szczecin.englishtamagotchi.R
import com.szczecin.englishtamagotchi.adapter.repeat.RepeatItemsAdapter
import com.szczecin.englishtamagotchi.common.ViewModelFactory
import com.szczecin.englishtamagotchi.common.extensions.lifecircle.observeLifecycleIn
import com.szczecin.englishtamagotchi.databinding.ActivityRepeatBinding
import com.szczecin.englishtamagotchi.viewmodel.repeat.RepeatViewModel
import com.szczecin.englishtamagotchi.viewmodel.repeat.RepeatingItemColor
import com.szczecin.pointofinterest.common.extensions.viewModel
import dagger.android.AndroidInjection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

const val SPAN_COUNT = 2

class RepeatActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory<RepeatViewModel>

    private val repeatViewModel: RepeatViewModel by viewModel { factory }

    private lateinit var binding: ActivityRepeatBinding
    private lateinit var repeatItemsAdapter: RepeatItemsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setBinding()
        observeLifecycleIn(repeatViewModel)
        initRecycler()
        observeViewModel()
    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_repeat)
        binding.repeatViewModel = repeatViewModel
        binding.lifecycleOwner = this@RepeatActivity

        repeatViewModel.finishLesson.observe(this, Observer {
            Snackbar.make(this.binding.root, "умничка! Задание сделано!", Snackbar.LENGTH_LONG)
                .show()
            GlobalScope.launch(Dispatchers.Main) {
                delay(2000)
                finish()
            }
        })
    }


    private fun initRecycler() {
        val recyclerPairEng = binding.recyclerRepeatWords
        recyclerPairEng.apply {
            layoutManager = GridLayoutManager(context, SPAN_COUNT)
            repeatItemsAdapter = RepeatItemsAdapter()
            this.adapter = repeatItemsAdapter
            repeatViewModel.subscribeForItemClick(repeatItemsAdapter.getClickItemObserver())
        }
    }

    private fun observeViewModel() {
        repeatViewModel.buttonEngColor.observe(this, Observer {
            repeatItemsAdapter.updateItem(it)
            if (it == RepeatingItemColor.RED) {
                GlobalScope.launch(Dispatchers.Main) {
                    delay(1000)
                    repeatItemsAdapter.updateItem(
                        RepeatingItemColor.DEFAULT
                    )
                }
            } else {
                GlobalScope.launch(Dispatchers.Main) {
                    delay(1000)
                    repeatViewModel.nextWords.postValue(Unit)
                    repeatItemsAdapter.updateItem(
                        RepeatingItemColor.DEFAULT
                    )
                }
            }
        })
    }

}
