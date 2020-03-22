package com.szczecin.data.englishtamagotchi.plugins

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.szczecin.englishtamagotchi.TranslateRepository
import com.szczecin.englishtamagotchi.data.BuildConfig
import com.szczecin.englishtamagotchi.model.AppPluginsConfig
import com.szczecin.englishtamagotchi.model.PairRusEng
import com.szczecin.englishtamagotchi.model.Tutorial
import io.reactivex.Single
import java.util.*

class LocalCommonTranslateRepository(private val context: Context): TranslateRepository {

    private val gson = Gson()

//    init {
//        loadTranslate(context)
//    }

    override fun loadTranslate() : AppPluginsConfig {
        val jsonResponse = getJson(context, BuildConfig.RU_ENG_TRANSLATE)
        val appPluginsConfig: AppPluginsConfig = gson.fromJson(jsonResponse, AppPluginsConfig::class.java)

        return appPluginsConfig
    }

    private fun getJson(context: Context, filePath: String): String {
        return context.assets.open(filePath).bufferedReader().use { it.readText() }
    }
}


