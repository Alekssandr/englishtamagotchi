package com.szczecin.englishtamagotchi.preferencies

import android.content.SharedPreferences

class SettingsPreferences(sharedPreferences: SharedPreferences) {

    var numberStart: Int by IntPreference(sharedPreferences, COMMON_BLOCK)
    var newWordsPerDay: Int by IntPreference(sharedPreferences, NEW_WORDS_PER_DAY)
    var lastOpenDayInMls: Long by LongPreference(sharedPreferences, LAST_OPEN_DAY_IN_MLS)
    var dailyWords: Int by IntPreference(sharedPreferences, DAILY_WORDS)
    var numberOfLearningDay: Int by IntPreference(sharedPreferences, NUMBER_OF_DAY)
    var isEngToRus: Boolean by BoolPreference(sharedPreferences, IS_ENG_TO_RUS)

    private companion object Key {
        const val COMMON_BLOCK = "COMMON_BLOCK"
        const val NEW_WORDS_PER_DAY = "NEW_WORDS_PER_DAY"
        const val LAST_OPEN_DAY_IN_MLS = "LAST_OPEN_DAY_IN_MLS"
        const val DAILY_WORDS = "DAILY_WORDS"
        const val NUMBER_OF_DAY = "NUMBER_OF_DAY"
        const val IS_ENG_TO_RUS = "IS_ENG_TO_RUS"
    }
}