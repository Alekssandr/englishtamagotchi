package com.szczecin.englishtamagotchi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.szczecin.englishtamagotchi.R
import com.szczecin.englishtamagotchi.databinding.BindWordItemBinding
import com.szczecin.englishtamagotchi.databinding.WritingLetterBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.bind_word_item.view.*

class WordsWritingAdapter : RecyclerView.Adapter<WordsWritingAdapter.ItemViewHolder>() {

    private var lettersShuffled: List<Char> = emptyList()
    private val publishSubjectItem = PublishSubject.create<Char>()
    var previousPosition = DEFAULT

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: WritingLetterBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.writing_letter, parent, false)
        return ItemViewHolder(binding)
    }

    fun getClickItemObserver(): Observable<Char> {
        return publishSubjectItem
    }

    override fun getItemCount(): Int {
        return lettersShuffled.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(lettersShuffled[position])

        holder.itemView.buttonEng.setOnClickListener {
            publishSubjectItem.onNext(lettersShuffled[position])
        }
    }

    fun update(items: MutableList<Char>?) {
        this.lettersShuffled = items!!.toList()
        notifyDataSetChanged()
    }

    class ItemViewHolder(private val binding: WritingLetterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pairRusEng: Char) {

            this.binding.text = pairRusEng.toString()
            if(pairRusEng == ' '){
                this.binding.text =   "___"
            }

        }
    }
}
