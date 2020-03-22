package com.szczecin.data.englishtamagotchi.database.repo

class WordsBlockDatabase(private val providerDataBase: ProviderDataBase) {
    fun build(): WordsBlockRoomDatabase = providerDataBase.createBuilder().build()
}