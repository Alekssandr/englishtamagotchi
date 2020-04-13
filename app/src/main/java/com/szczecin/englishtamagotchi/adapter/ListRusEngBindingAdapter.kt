package com.szczecin.englishtamagotchi.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.szczecin.englishtamagotchi.adapter.common.CommonWordsItemsAdapter
import com.szczecin.englishtamagotchi.adapter.know.KnowLearnTableItemsAdapter
import com.szczecin.englishtamagotchi.adapter.repeat.ChooseCorrectWordsItemsAdapter
import com.szczecin.englishtamagotchi.model.PairRusEng

@BindingAdapter("pairEngList")
fun RecyclerView.bindEngItems(items: List<PairRusEng>?) {
    items?.let {
        val adapter = adapter as BindWordsEngItemsAdapter
        adapter.update(items)
    }
}

@BindingAdapter("pairRusList")
fun RecyclerView.bindRusItems(items: List<PairRusEng>?) {
    items?.let {
        val adapter = adapter as BindWordsRusItemsAdapter
        adapter.update(items)
    }
}

@BindingAdapter("commonWordsList")
fun RecyclerView.bindCommonWords(items: List<PairRusEng>?) {
    items?.let {
        val adapter = adapter as CommonWordsItemsAdapter
        adapter.update(items)
    }
}

@BindingAdapter("knowWordsList")
fun RecyclerView.bindKnowTable(items: MutableList<PairRusEng>?) {
    items?.let {
        val adapter = adapter as KnowLearnTableItemsAdapter
        adapter.update(items)
    }
}

@BindingAdapter("learnList")
fun RecyclerView.bindLearnTable(items: MutableList<PairRusEng>?) {
    items?.let {
        val adapter = adapter as KnowLearnTableItemsAdapter
        adapter.update(items)
    }
}

@BindingAdapter("chooseCorrectList")
fun RecyclerView.bindRepeatList(items: MutableList<PairRusEng>?) {
    items?.let {
        val adapter = adapter as ChooseCorrectWordsItemsAdapter
        adapter.update(items)
    }
}