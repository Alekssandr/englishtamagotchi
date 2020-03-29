package com.szczecin.englishtamagotchi.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.szczecin.englishtamagotchi.model.PairRusEng

@BindingAdapter("pairEngList")
fun RecyclerView.bindEngItems(items: List<PairRusEng>?) {

    items?.let { val adapter = adapter as BindWordsEngItemsAdapter
        adapter.update(items) }
}

@BindingAdapter("pairRusList")
fun RecyclerView.bindRusItems(items: List<PairRusEng>?) {

    items?.let { val adapter = adapter as BindWordsRusItemsAdapter
        adapter.update(items) }
}