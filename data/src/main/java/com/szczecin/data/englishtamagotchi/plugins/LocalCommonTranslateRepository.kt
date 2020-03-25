package com.szczecin.data.englishtamagotchi.plugins

import android.content.Context
import com.google.gson.Gson
import com.szczecin.englishtamagotchi.TranslateRepository
import com.szczecin.englishtamagotchi.data.BuildConfig
import com.szczecin.englishtamagotchi.model.AppPluginsConfig
import com.szczecin.englishtamagotchi.model.PairRusEng

class LocalCommonTranslateRepository(private val context: Context): TranslateRepository {

    private val gson = Gson()

//    init {
//        loadTranslate(context)
//    }

    override fun loadTranslate20InCommon(numberStart: Int, addNewData: Int) : List<PairRusEng> {//
        val jsonResponse = getJson(context, BuildConfig.RU_ENG_TRANSLATE)
        val appPluginsConfig: AppPluginsConfig = gson.fromJson(jsonResponse, AppPluginsConfig::class.java)
//        val startIndex = numberBlock * 10 * 2 - 20
//        val startIndex = numberBlock * 5 - 5
        return  appPluginsConfig.pairRusEng.subList(numberStart, numberStart + addNewData)//10
    }

    override fun loadTranslate() : AppPluginsConfig {
        val jsonResponse = getJson(context, BuildConfig.RU_ENG_TRANSLATE)
        val appPluginsConfig: AppPluginsConfig = gson.fromJson(jsonResponse, AppPluginsConfig::class.java)

        return appPluginsConfig
    }

    private fun getJson(context: Context, filePath: String): String {
        return context.assets.open(filePath).bufferedReader().use { it.readText() }
    }
}


