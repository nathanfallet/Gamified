package me.nathanfallet.gamified.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.nathanfallet.gamified.extensions.previous17Weeks
import me.nathanfallet.gamified.models.Counter
import me.nathanfallet.gamified.models.Graph
import me.nathanfallet.gamified.services.GamifiedService
import java.util.Date

class StatsViewModel(
    application: Application,
    val counters: List<Counter>,
    val isGridEnabled: Boolean
) : AndroidViewModel(application) {

    // Configuration

    private val graphs = MutableLiveData<List<Graph>>()

    // Getters

    fun getGraphs(): LiveData<List<Graph>> {
        return graphs
    }

    // Initializer

    init {
        viewModelScope.launch {
            graphs.value = GamifiedService.shared.getGraphs(previous17Weeks, Date())
        }
    }


}