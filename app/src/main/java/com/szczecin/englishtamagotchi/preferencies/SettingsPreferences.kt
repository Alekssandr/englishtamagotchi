package com.szczecin.englishtamagotchi.preferencies

import android.content.SharedPreferences

class SettingsPreferences(sharedPreferences: SharedPreferences) {

    var numberStart: Int by IntPreference(sharedPreferences, COMMON_BLOCK)
    var isEngToRus: Boolean by BoolPreference(sharedPreferences, IS_ENG_TO_RUS)

    private companion object Key {
        const val COMMON_BLOCK = "COMMON_BLOCK"
        const val IS_ENG_TO_RUS = "IS_ENG_TO_RUS"
    }
}