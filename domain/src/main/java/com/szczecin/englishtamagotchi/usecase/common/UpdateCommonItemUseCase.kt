package com.szczecin.englishtamagotchi.usecase.common

import com.szczecin.englishtamagotchi.repo.WordsCommonRepository
import io.reactivex.Completable
import javax.inject.Inject

class UpdateCommonItemUseCase @Inject constructor(
    private val wordsBlockRepository: WordsCommonRepository
) {
    fun updateItemBy(eng: String, isCheckbox: Boolean): Completable =
        wordsBlockRepository.updateItemForCommon(eng, isCheckbox)
}
