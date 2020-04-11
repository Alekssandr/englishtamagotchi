package com.szczecin.englishtamagotchi.model

data class AppPluginsConfig(
    val pairRusEng: List<PairRusEng>
)

data class PairRusEng(
    var id: Long = 0,
    var eng: String = "",
    var rus: String = "",
    var isChecked: Boolean = false,
    var dayOfLearning: Int = 0,
    var buttonColor: String = ""
)