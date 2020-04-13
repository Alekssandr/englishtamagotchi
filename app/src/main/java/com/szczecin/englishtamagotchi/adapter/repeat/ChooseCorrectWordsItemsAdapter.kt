package com.szczecin.englishtamagotchi.adapter.repeat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.szczecin.englishtamagotchi.R
import com.szczecin.englishtamagotchi.adapter.*
import com.szczecin.englishtamagotchi.databinding.RepeatWordItemBinding
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.viewmodel.repeat.RepeatingItemColor
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.repeat_word_item.view.*

class ChooseCorrectWordsItemsAdapter :
    RecyclerView.Adapter<ChooseCorrectWordsItemsAdapter.ItemViewHolder>() {
    private var repeatItemsList: List<PairRusEng> = emptyList()
    private val publishSubjectItem = PublishSubject.create<PairRusEng>()
    var previousPosition = DEFAULT

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: RepeatWordItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.repeat_word_item, parent, false)
        return ItemViewHolder(binding)
    }

    fun getClickItemObserver(): Observable<PairRusEng> {
        return publishSubjectItem
    }

    override fun getItemCount(): Int {
        return repeatItemsList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(repeatItemsList[position])

        holder.itemView.buttonEngRepeat.setOnClickListener {
                publishSubjectItem.onNext(repeatItemsList[position])
                previousPosition = position
        }
    }

    fun update(items: List<PairRusEng>) {
        this.repeatItemsList = items
        notifyDataSetChanged()
    }

    fun updateItem(repeatingItemColor: RepeatingItemColor) {
        repeatItemsList[previousPosition].buttonColor = repeatingItemColor.color
        notifyItemChanged(previousPosition)
    }

    class ItemViewHolder(private val binding: RepeatWordItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pairRusEng: PairRusEng) {
            this.binding.text = pairRusEng.eng
            when {
                pairRusEng.buttonColor == RepeatingItemColor.GREEN.color -> {
                    this.binding.buttonEngRepeat.setBackgroundColor(
                        ContextCompat.getColor(this.binding.buttonEngRepeat.context, R.color.green)
                    )
                }
                pairRusEng.buttonColor == RepeatingItemColor.RED.color -> {
                    this.binding.buttonEngRepeat.setBackgroundColor(
                        ContextCompat.getColor(this.binding.buttonEngRepeat.context, R.color.red)
                    )
                }
                else -> {
                    this.binding.buttonEngRepeat.setBackgroundColor(
                        ContextCompat.getColor(this.binding.buttonEngRepeat.context, R.color.grey)
                    )
                }
            }
        }
    }
}
