package me.nathanfallet.gamified.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private var graphs = MutableLiveData<List<Graph>>()

    // Getters

    fun getGraphs(): LiveData<List<Graph>> {
        return graphs
    }

    // Initializer

    init {
        graphs.value = GamifiedService.shared.getGraphs(previous17Weeks, Date())
    }


}