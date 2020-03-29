package com.szczecin.englishtamagotchi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.szczecin.englishtamagotchi.R
import com.szczecin.englishtamagotchi.databinding.BindWordItemBinding
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.viewmodel.WordsBindViewModel
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
        return stepsList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(stepsList[position])

        holder.itemView.buttonEng.setOnClickListener {
            if (previousPosition == DEFAULT) {
                publishSubjectItem.onNext(position)
                previousPosition = position
            }
        }
    }

    fun update(items: List<PairRusEng>) {
        this.stepsList = items
        notifyDataSetChanged()
    }

    fun updateItem(index: WordsBindViewModel.ButtonColorization) {
        if (index.event != RED && index.event != CHOICE) {
            previousPosition = DEFAULT
        }
        stepsList[index.index].event = index.event
        notifyItemChanged(index.index)
    }

    class ItemViewHolder(private val binding: BindWordItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pairRusEng: PairRusEng) {
            this.binding.text = pairRusEng.eng
            when {
                pairRusEng.event == GREY -> {
                    this.binding.buttonEng.setBackgroundColor(
                        ContextCompat.getColor(this.binding.buttonEng.context, R.color.grey)
                    )
                    this.binding.buttonEng.isEnabled = true
                }
                pairRusEng.event == GREEN -> {
                    this.binding.buttonEng.setBackgroundColor(
                        ContextCompat.getColor(this.binding.buttonEng.context, R.color.green)
                    )
                    this.binding.buttonEng.isEnabled = false
                }
                pairRusEng.event == RED -> {
                    this.binding.buttonEng.setBackgroundColor(
                        ContextCompat.getColor(this.binding.buttonEng.context, R.color.red)
                    )
                    this.binding.buttonEng.isEnabled = false
                }
                else -> {
                    this.binding.buttonEng.setBackgroundColor(
                        ContextCompat.getColor(this.binding.buttonEng.context, R.color.choice)
                    )
                    this.binding.buttonEng.isEnabled = false
                }
            }
        }
    }
}
