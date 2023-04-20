package me.nathanfallet.gamified.activities

import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.nathanfallet.gamified.R
import me.nathanfallet.gamified.extensions.asString
import me.nathanfallet.gamified.extensions.getDays
import me.nathanfallet.gamified.extensions.nextStartOfWeek
import me.nathanfallet.gamified.extensions.previous17Weeks
import me.nathanfallet.gamified.extensions.previous4Weeks
import me.nathanfallet.gamified.models.Counter
import me.nathanfallet.gamified.models.Graph
import me.nathanfallet.gamified.models.Stats
import me.nathanfallet.gamified.viewmodels.StatsViewModel
import me.nathanfallet.gamified.views.ExpandableHeightGridView
import me.nathanfallet.gamified.views.GridAutoFitLayoutManager
import java.util.Date

class StatsActivity : AppCompatActivity() {

    object Extras {

        const val counters = "counters"
        const val isGridEnabled = "isGridEnabled"

    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: GridAutoFitLayoutManager
    private lateinit var countersAdapter: CountersRecyclerViewAdapter
    private lateinit var gridsAdapter: GridsRecyclerViewAdapter
    private lateinit var graphsAdapter: GraphsRecyclerViewAdapter

    private lateinit var viewModel: StatsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View model instance
        val counters = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableArrayListExtra(Extras.counters, Counter::class.java)
        } else {
            intent.getParcelableArrayListExtra(Extras.counters)
        } ?: listOf()
        val isGridEnabled = intent.getBooleanExtra(Extras.isGridEnabled, true)
        viewModel = StatsViewModel(application, counters, isGridEnabled)

        // Content view
        setContentView(R.layout.activity_stats)
        setTitle(R.string.stats)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Configure recycler view
        recyclerView = findViewById(R.id.recyclerView)
        layoutManager = GridAutoFitLayoutManager(
            this, TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 150f,
                resources.displayMetrics
            ).toInt()
        )
        countersAdapter = CountersRecyclerViewAdapter(viewModel)
        gridsAdapter = GridsRecyclerViewAdapter(viewModel)
        graphsAdapter = GraphsRecyclerViewAdapter(viewModel)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = when (viewModel.isGridEnabled) {
            true -> ConcatAdapter(
                HeaderRecyclerViewAdapter(R.string.stats_counts),
                countersAdapter,
                HeaderRecyclerViewAdapter(R.string.stats_grid, R.string.stats_grid_description),
                gridsAdapter,
                HeaderRecyclerViewAdapter(R.string.stats_graphs),
                graphsAdapter
            )

            false -> ConcatAdapter(
                HeaderRecyclerViewAdapter(R.string.stats_counts),
                countersAdapter,
                HeaderRecyclerViewAdapter(R.string.stats_graphs),
                graphsAdapter
            )
        }
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int = when (position) {
                // Headers
                0 -> layoutManager.spanCount
                countersAdapter.itemCount + 1 -> layoutManager.spanCount
                countersAdapter.itemCount + gridsAdapter.itemCount + 2 -> layoutManager.spanCount

                // Elements
                in 1..countersAdapter.itemCount -> 1
                else -> 2.coerceAtMost(layoutManager.spanCount)
            }
        }

        // Create observers
        viewModel.getGraphs().observe(this) {
            gridsAdapter.notifyItemChanged(0)
            graphsAdapter.notifyDataSetChanged()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    inner class HeaderRecyclerViewAdapter(
        private val title: Int,
        private val subtitle: Int? = null
    ) : RecyclerView.Adapter<HeaderRecyclerViewAdapter.HeaderViewHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): HeaderViewHolder {
            val root =
                LayoutInflater.from(parent.context).inflate(R.layout.header_title, parent, false)
            return HeaderViewHolder(root)
        }

        override fun onBindViewHolder(
            holder: HeaderViewHolder,
            position: Int
        ) {
            holder.bind()
        }

        override fun getItemCount(): Int {
            return 1
        }

        inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            private val headerTitle: TextView = itemView.findViewById(R.id.title)
            private val headerSubtitle: TextView = itemView.findViewById(R.id.subtitle)

            fun bind() {
                headerTitle.setText(title)
                subtitle?.let { subtitle ->
                    headerSubtitle.setText(subtitle)
                    headerSubtitle.visibility = View.VISIBLE
                } ?: run {
                    headerSubtitle.visibility = View.GONE
                }
            }

        }

    }

    inner class CountersRecyclerViewAdapter(private val viewModel: StatsViewModel) :
        RecyclerView.Adapter<CountersRecyclerViewAdapter.CounterViewHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): CounterViewHolder {
            val root =
                LayoutInflater.from(parent.context).inflate(R.layout.stats_counter, parent, false)
            return CounterViewHolder(root)
        }

        override fun onBindViewHolder(
            holder: CounterViewHolder,
            position: Int
        ) {
            holder.bind(
                viewModel.counters[position].text,
                viewModel.counters[position].icon,
                viewModel.counters[position].count
            )
        }

        override fun getItemCount(): Int {
            return viewModel.counters.size
        }

        inner class CounterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            private val iconView: ImageView = itemView.findViewById(R.id.icon)
            private val titleView: TextView = itemView.findViewById(R.id.title)

            fun bind(text: String, icon: Int, value: Long) {
                iconView.setImageResource(icon)
                titleView.text = text.format(value)
            }

        }

    }

    inner class GridsRecyclerViewAdapter(private val viewModel: StatsViewModel) :
        RecyclerView.Adapter<GridsRecyclerViewAdapter.GridViewHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): GridViewHolder {
            val root =
                LayoutInflater.from(parent.context).inflate(R.layout.stats_grid, parent, false)
            return GridViewHolder(root)
        }

        override fun onBindViewHolder(
            holder: GridViewHolder,
            position: Int
        ) {
            holder.bind()
        }

        override fun getItemCount(): Int {
            return 1
        }

        inner class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            private val gridView: ExpandableHeightGridView = itemView.findViewById(R.id.gridView)

            fun bind() {
                gridView.isExpanded = true
                gridView.adapter = GridViewAdapter(viewModel)
            }

            inner class GridViewAdapter(viewModel: StatsViewModel) : BaseAdapter() {

                private val stats: List<Stats>

                private val rows = 7
                private val columns: Int
                    get() {
                        return ((stats.size - 1) / rows) + 1
                    }

                init {
                    val raw = viewModel.getGraphs().value?.flatMap { it.stats } ?: listOf()
                    stats = previous17Weeks.nextStartOfWeek.getDays(Date()).map { day ->
                        raw.filter { it.day.asString == day.asString }
                            .fold(Stats(day, 0)) { acc, stats ->
                                Stats(acc.day, acc.value + stats.value)
                            }
                    }
                }

                override fun getCount(): Int {
                    return rows * columns
                }

                override fun getItem(position: Int): Any? {
                    return null
                }

                override fun getItemId(position: Int): Long {
                    return position.toLong()
                }

                override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                    val view = convertView ?: LayoutInflater.from(parent?.context)
                        .inflate(R.layout.stats_widget_item, parent, false)

                    val row = position / columns
                    val column = position % columns
                    val color = ContextCompat.getColor(view.context, color(row, column))

                    val background = view.findViewById<ImageView>(R.id.background)
                    background.setColorFilter(color)
                    background.imageAlpha = color.shr(24)

                    return view
                }

                fun color(row: Int, column: Int): Int {
                    val index = row + rows * column
                    if (index >= stats.size) return android.R.color.transparent

                    val max = stats.maxOf { it.value }
                    if (max == 0L) return color(0f)

                    val value = stats[index].value.toFloat()
                    return color(value / max.toFloat())
                }

                fun color(level: Float): Int {
                    return when {
                        level == 0f -> R.color.accentDisabled
                        level <= 0.25 -> R.color.accentVeryLight
                        level <= 0.5 -> R.color.accentLight
                        level <= 0.75 -> R.color.accent
                        else -> R.color.accentDark
                    }
                }

            }

        }

    }

    inner class GraphsRecyclerViewAdapter(private val viewModel: StatsViewModel) :
        RecyclerView.Adapter<GraphsRecyclerViewAdapter.GraphViewHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): GraphViewHolder {
            val root =
                LayoutInflater.from(parent.context).inflate(R.layout.stats_graph, parent, false)
            return GraphViewHolder(root)
        }

        override fun onBindViewHolder(
            holder: GraphViewHolder,
            position: Int
        ) {
            holder.bind(viewModel.getGraphs().value?.get(position)!!)
        }

        override fun getItemCount(): Int {
            return viewModel.getGraphs().value?.size ?: 0
        }

        inner class GraphViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            private val title: TextView = itemView.findViewById(R.id.title)
            private val value: TextView = itemView.findViewById(R.id.value)
            private val gridView: GridView = itemView.findViewById(R.id.gridView)
            private val empty: TextView = itemView.findViewById(R.id.empty_view)

            var graphData: List<Triple<Date, Long, Float>> = listOf()
            var summedData = 0L

            fun bind(graph: Graph) {
                gridView.adapter = GraphViewAdapter()

                val start = previous4Weeks
                val end = Date()
                val fullGraph = Graph(
                    graph.key,
                    graph.title,
                    start.getDays(end).map { day ->
                        graph.stats.firstOrNull { it.day.asString == day.asString } ?: Stats(day, 0)
                    }
                )

                val max = fullGraph.stats.maxOf { it.value }
                if (max != 0L) {
                    graphData = fullGraph.stats.map {
                        Triple(
                            it.day,
                            it.value,
                            it.value.toFloat() / max.toFloat()
                        )
                    }
                    summedData = graphData.fold(0) { sum, data ->
                        sum + data.second
                    }
                } else {
                    graphData = listOf()
                    summedData = 0
                }

                (gridView.adapter as? GraphViewAdapter)?.notifyDataSetChanged()
                empty.visibility =
                    if (graphData.isEmpty()) LinearLayout.VISIBLE else LinearLayout.GONE
                title.text = graph.title
                value.text = summedData.toString()
            }

            inner class GraphViewAdapter : BaseAdapter() {

                override fun getCount(): Int {
                    return graphData.size
                }

                override fun getItem(position: Int): Any? {
                    return null
                }

                override fun getItemId(position: Int): Long {
                    return position.toLong()
                }

                override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                    val view = convertView ?: LayoutInflater.from(parent?.context)
                        .inflate(R.layout.stats_graph_item, parent, false)
                    val value = graphData[position].third
                    val color = ContextCompat.getColor(
                        view.context,
                        if (value > 0) R.color.accent else R.color.accentDisabled
                    )

                    gridView.post {
                        val rawHeight =
                            (if (value > 0) value.toDouble() else 0.02) * gridView.height

                        val background = view.findViewById<ImageView>(R.id.background)
                        background.setColorFilter(color)
                        background.imageAlpha = color.shr(24)
                        background.layoutParams = RelativeLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, rawHeight.toInt()
                        ).apply {
                            addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
                        }
                    }

                    return view
                }

            }

        }

    }

}