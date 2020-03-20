package com.szczecin.englishtamagotchi.common.extensions.lifecircle

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner

fun LifecycleOwner.observeLifecycleIn(observer: LifecycleObserver) =
    lifecycle.addObserver(observer)