package com.szczecin.englishtamagotchi.model

data class AppPluginsConfig(
    val pairRusEng: List<PairRusEng>
)

data class PairRusEng(
    val eng: String,
    val rus: String
)