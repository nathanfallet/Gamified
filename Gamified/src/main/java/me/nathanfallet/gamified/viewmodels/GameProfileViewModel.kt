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

    private val _experience = MutableLiveData<Experience>()
    val experience: LiveData<Experience> = _experience

    private val _achievements = MutableLiveData<List<Achievement>>()
    val achievements: LiveData<List<Achievement>> = _achievements

    // Initializer

    init {
        viewModelScope.launch {
            _experience.value = GamifiedService.shared.getExperience()
            _achievements.value = GamifiedService.shared.getAchievements()
        }
    }

}