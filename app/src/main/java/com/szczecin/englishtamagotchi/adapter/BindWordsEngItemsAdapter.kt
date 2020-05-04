package com.szczecin.englishtamagotchi.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.szczecin.englishtamagotchi.R
import com.szczecin.englishtamagotchi.databinding.BindWordItemBinding
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.viewmodel.learning.WordsBindViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.bind_word_item.view.*

const val DEFAULT = -1
const val GREY = 0
const val CHOICE = 3
const val GREEN = 1
const val RED = 2

class BindWordsEngItemsAdapter : RecyclerView.Adapter<BindWordsEngItemsAdapter.ItemViewHolder>() {

    private var stepsList: List<PairRusEng> = emptyList()
    private var stepsListShuffled: List<PairRusEng> = emptyList()
    private val publishSubjectItem = PublishSubject.create<Int>()
    var previousPosition = DEFAULT

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: BindWordItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.bind_word_item, parent, false)
        return ItemViewHolder(binding)
    }

    fun getClickItemObserver(): Observable<Int> {
        return publishSubjectItem
    }

    override fun getItemCount(): Int {
        return stepsListShuffled.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(stepsListShuffled[position])
        if (holder.itemView.buttonEng.isClickable) {
            holder.itemView.buttonEng.setOnClickListener {
                if (previousPosition == DEFAULT) {
                    publishSubjectItem.onNext(stepsList.indexOf(stepsListShuffled[position]))
                    previousPosition = position
                }
            }
        }
    }

    fun update(items: List<PairRusEng>) {
        stepsList = items
        this.stepsListShuffled = items.shuffled()
        notifyDataSetChanged()
    }

    fun updateItem(pairRusEng: WordsBindViewModel.ButtonColorization) {
        if (pairRusEng.event != RED && pairRusEng.event != CHOICE) {
            previousPosition = DEFAULT
        }
        val correctIndex = stepsListShuffled.indexOf(stepsList[pairRusEng.index])
        stepsListShuffled[correctIndex].dayOfLearning = pairRusEng.event
        notifyItemChanged(stepsListShuffled.indexOf(stepsList[pairRusEng.index]))
    }

    class ItemViewHolder(private val binding: BindWordItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pairRusEng: PairRusEng) {
            this.binding.text = pairRusEng.eng
            when {
                pairRusEng.dayOfLearning == GREY -> {
                    this.binding.buttonEng.setBackgroundResource(R.drawable.btn_idle_default)
                    this.binding.buttonEng.isClickable = true
                }
                pairRusEng.dayOfLearning == GREEN -> {
                    this.binding.buttonEng.setBackgroundResource(R.drawable.audio_idle)
                    this.binding.buttonEng.isClickable = false
                }
                pairRusEng.dayOfLearning == RED -> {
                    this.binding.buttonEng.setBackgroundResource(R.drawable.btn_idle_not_correct)

                    this.binding.buttonEng.isClickable = false
                }
                else -> {
                    this.binding.buttonEng.setBackgroundResource(R.drawable.btn_idle_choose)
                    this.binding.buttonEng.isClickable = false
                }
            }
        }
    }
}
