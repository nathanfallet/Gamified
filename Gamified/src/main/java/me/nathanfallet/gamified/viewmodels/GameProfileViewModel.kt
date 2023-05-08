package me.nathanfallet.gamified.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.nathanfallet.gamified.models.Achievement
import me.nathanfallet.gamified.models.Experience
import me.nathanfallet.gamified.services.GamifiedService

class GameProfileViewModel(
    application: Application
) : AndroidViewModel(application) {

    // Configuration

    val username: String? = null
    val url: String? = null
    private val experience = MutableLiveData<Experience>()
    private val achievements = MutableLiveData<List<Achievement>>()

    // Getters

    fun getExperience(): LiveData<Experience> {
        return experience
    }

    fun getAchievements(): LiveData<List<Achievement>> {
        return achievements
    }

    // Initializer

    init {
        viewModelScope.launch {
            experience.value = GamifiedService.shared.getExperience()
            achievements.value = GamifiedService.shared.getAchievements()
        }
    }

}