package com.szczecin.englishtamagotchi.preferencies

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class LongPreference(
    private val sharedPreferences: SharedPreferences,
    private val key: String,
    private val defaultValue: Long = 0
) : ReadWriteProperty<Any, Long> {

    override fun getValue(thisRef: Any, property: KProperty<*>): Long =
        sharedPreferences.getLong(key, defaultValue)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Long) =
        sharedPreferences.edit()
            .putLong(key, value)
            .apply()
}