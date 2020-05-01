package com.szczecin.englishtamagotchi.adapter

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.szczecin.englishtamagotchi.adapter.common.CommonGroupItemsAdapter
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

@BindingAdapter("allGroups")
fun RecyclerView.allGroups(items: Int?) {
    items?.let {
        val adapter = adapter as CommonGroupItemsAdapter
        var i = 0
        var listItems = mutableListOf<Int>()

        while (i <= items) {
            println("Line $i")
            listItems.add(i)
            i += 1

        }
        adapter.update(listItems)
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

@BindingAdapter("wordsWriting")
fun RecyclerView.bindWordsWriting(items: MutableList<Char>?) {
    items?.let {
        val adapter = adapter as WordsWritingAdapter
        adapter.update(items)
    }
}

@BindingAdapter("changeColor")
fun TextView.changeColor(writingTextEditText: Pair<String, List<Int>>?) {
    writingTextEditText?.let {
        val word = SpannableString(writingTextEditText.first)
        if (word.isNotEmpty()) {
            word.setSpan(
                ForegroundColorSpan(Color.DKGRAY),
                0,
                word.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

//            if(word.length<writingTextEditText.second.size){
//
//            }
            writingTextEditText.second.forEach {
                word.setSpan(
                    ForegroundColorSpan(Color.RED),
                    it,
                    it + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

//        if (writingTextEditText.second) {
//            word.setSpan(
//                ForegroundColorSpan(Color.DKGRAY),
//                0,
//                word.length,
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//            )
//        } else {
//            word.setSpan(
//                ForegroundColorSpan(Color.DKGRAY),
//                0,
//                word.length - 1,
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//            )
//            word.setSpan(
//                ForegroundColorSpan(Color.RED),
//                word.length - 1,
//                word.length,
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//            )
//        }
        text = word
    }
}