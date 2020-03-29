package com.szczecin.englishtamagotchi.adapter

import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.szczecin.englishtamagotchi.R
import java.util.*
import kotlin.concurrent.schedule

@BindingAdapter("backgroundButtonColor")
fun Button.changeColor(eventButton: Int?) {
    eventButton?.let {
        setBackgroundColor(
            ContextCompat.getColor(
                this.context,
                it
            )
        )
        if (it == R.color.red) {
            Timer().schedule(2000) {
                setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.grey
                    )
                )
            }
        }
    }
}

//fun getColorValue(eventButton: Int): Int {
//  return when (eventButton) {
//        EventButton.RED -> Color.RED
//        EventButton.GREEN -> Color.GREEN
//        EventButton.CHOICE -> Color.YELLOW
//      else -> Color.GRAY
//    }
//}