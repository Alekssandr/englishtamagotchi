package com.szczecin.englishtamagotchi.adapter.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.szczecin.englishtamagotchi.R
import com.szczecin.englishtamagotchi.databinding.CommonListGroupItemBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.common_list_group_item.view.*
import kotlinx.android.synthetic.main.common_list_item.view.*

class CommonGroupItemsAdapter :
    RecyclerView.Adapter<CommonGroupItemsAdapter.ItemViewHolder>() {

    private var stepsList: List<Int> = emptyList()
    private val publishSubjectItemGroup = PublishSubject.create<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: CommonListGroupItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.common_list_group_item, parent, false)
        return ItemViewHolder(
            binding
        )
    }

    fun getClickItemGroupObserver(): Observable<Int> {
        return publishSubjectItemGroup
    }

    override fun getItemCount(): Int {
        return stepsList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(stepsList[position])

        holder.itemView.buttonGroup.setOnClickListener {
            publishSubjectItemGroup.onNext(position)
        }
    }

    fun update(items: MutableList<Int>) {
        this.stepsList = items
        notifyDataSetChanged()
    }

    class ItemViewHolder(private val binding: CommonListGroupItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(group: Int) {
            var groupText = "0-100"
            if (group != 0) {
                groupText = "${group}00-${group}99"
            }
            this.binding.text = groupText
        }
    }
}
