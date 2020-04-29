package com.szczecin.englishtamagotchi.adapter.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.szczecin.englishtamagotchi.R
import com.szczecin.englishtamagotchi.databinding.CommonListItemBinding
import com.szczecin.englishtamagotchi.model.PairRusEng
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.common_list_item.view.*

class CommonWordsItemsAdapter :
    RecyclerView.Adapter<CommonWordsItemsAdapter.ItemViewHolder>() {

    private var stepsList: List<PairRusEng> = emptyList()
    private val publishSubjectItem = PublishSubject.create<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: CommonListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.common_list_item, parent, false)
        return ItemViewHolder(
            binding
        )
    }

    fun getClickItemObserver(): Observable<Int> {
        return publishSubjectItem
    }

    override fun getItemCount(): Int {
        return stepsList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(stepsList[position])

        holder.itemView.item.setOnClickListener {
            if (stepsList[position].isChecked) {
                it.setBackgroundColor(ContextCompat.getColor(it.context, R.color.white))
            } else {
                it.setBackgroundColor(ContextCompat.getColor(it.context, R.color.green))
            }
            publishSubjectItem.onNext(position)
        }
    }

    fun update(items: List<PairRusEng>) {
        this.stepsList = items
        notifyDataSetChanged()
    }

    class ItemViewHolder(private val binding: CommonListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pairRusEng: PairRusEng) {
            this.binding.textEng = pairRusEng.eng
            this.binding.textRus = pairRusEng.rus
            if (pairRusEng.isChecked) {
                this.binding.item.setBackgroundColor(
                    ContextCompat.getColor(this.binding.item.context, R.color.green)
                )
            } else {
                this.binding.item.setBackgroundColor(
                    ContextCompat.getColor(this.binding.item.context, R.color.white)
                )
            }
        }
    }
}
