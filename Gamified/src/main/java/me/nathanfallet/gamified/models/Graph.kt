package me.nathanfallet.gamified.models

data class Graph(
    /**
     * Key of the graph
     */
    val key: String,

    /**
     * Title of the graph
     */
    val title: String,

    /**
     * Values of the graph
     */
    val values: List<Stats>
)
