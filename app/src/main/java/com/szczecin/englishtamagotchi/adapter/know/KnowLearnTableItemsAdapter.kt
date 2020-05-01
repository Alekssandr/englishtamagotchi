package com.szczecin.englishtamagotchi.adapter.know

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.szczecin.englishtamagotchi.R
import com.szczecin.englishtamagotchi.databinding.CommonListItemBinding
import com.szczecin.englishtamagotchi.model.PairRusEng
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.common_list_item.view.*

class KnowLearnTableItemsAdapter :
    RecyclerView.Adapter<KnowLearnTableItemsAdapter.ItemViewHolder>() {

    private var stepsList: MutableList<PairRusEng> = mutableListOf()
    private val addItem = PublishSubject.create<PairRusEng>()
    lateinit var removedItem: PairRusEng
    private var removedPosition: Int = 0
    private val publishSubjectRemovedItem = PublishSubject.create<PairRusEng>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: CommonListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.common_list_item, parent, false)
        return ItemViewHolder(
            binding
        )
    }

    fun getAddItemObserver(): Observable<PairRusEng> {
        return addItem
    }

    override fun getItemCount(): Int {
        return stepsList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(stepsList[position])
    }

    fun getClickRemoveItemObserver(): Observable<PairRusEng> {
        return publishSubjectRemovedItem
    }

    fun removeItem(position: Int, viewHolder: RecyclerView.ViewHolder) {
        removedItem = stepsList[position]
        removedPosition = position
        Log.d("test111", "removeItem:${removedItem.eng}")
        publishSubjectRemovedItem.onNext(removedItem)
        stepsList.removeAt(position)
        notifyItemRemoved(position)
        var isUndo = false
        Snackbar.make(viewHolder.itemView, "${removedItem.eng} removed", Snackbar.LENGTH_LONG)
            .setAction("UNDO") {
                stepsList.add(removedPosition, removedItem)

                notifyItemInserted(removedPosition)
                isUndo = true
            }.addCallback(object : Snackbar.Callback() {
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    super.onDismissed(transientBottomBar, event)
                    if (isUndo) {
                        addItem.onNext(stepsList[position])
                    }
                }
            }).show()
    }

    fun update(items: MutableList<PairRusEng>) {
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
