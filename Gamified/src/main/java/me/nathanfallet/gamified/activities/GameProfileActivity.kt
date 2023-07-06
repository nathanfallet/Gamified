package me.nathanfallet.gamified.activities

import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.Paint
import android.graphics.Shader
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import me.nathanfallet.gamified.R
import me.nathanfallet.gamified.models.Achievement
import me.nathanfallet.gamified.viewmodels.GameProfileViewModel
import me.nathanfallet.gamified.views.GridAutoFitLayoutManager
import me.nathanfallet.gamified.views.HeaderRecyclerViewAdapter

class GameProfileActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: GridAutoFitLayoutManager
    private lateinit var experienceAdapter: ExperienceRecyclerViewAdapter
    private lateinit var achievementsAdapter: AchievementsRecyclerViewAdapter

    private val viewModel: GameProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Content view
        setContentView(R.layout.activity_game_profile)
        setTitle(R.string.profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Configure recycler view
        recyclerView = findViewById(R.id.recyclerView)
        layoutManager = GridAutoFitLayoutManager(
            this, TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 150f,
                resources.displayMetrics
            ).toInt()
        )
        experienceAdapter = ExperienceRecyclerViewAdapter(viewModel)
        achievementsAdapter = AchievementsRecyclerViewAdapter(viewModel)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = ConcatAdapter(
            experienceAdapter,
            HeaderRecyclerViewAdapter(R.string.profile_achievements),
            achievementsAdapter
        )
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int = when (position) {
                0, 1 -> layoutManager.spanCount
                else -> 1
            }
        }

        // Create observers
        viewModel.experience.observe(this) {
            experienceAdapter.notifyItemChanged(0)
        }
        viewModel.achievements.observe(this) {
            achievementsAdapter.notifyDataSetChanged()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    inner class ExperienceRecyclerViewAdapter(private val viewModel: GameProfileViewModel) :
        RecyclerView.Adapter<ExperienceRecyclerViewAdapter.ExperienceViewHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ExperienceViewHolder {
            val root =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.profile_experience, parent, false)
            return ExperienceViewHolder(root)
        }

        override fun onBindViewHolder(
            holder: ExperienceViewHolder,
            position: Int
        ) {
            holder.bind()
        }

        override fun getItemCount(): Int {
            return 1
        }

        inner class ExperienceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            private val iconView: ImageView = itemView.findViewById(R.id.icon)
            private val usernameView: TextView = itemView.findViewById(R.id.username)
            private val levelView: TextView = itemView.findViewById(R.id.level)
            private val progressBar: ProgressBar = itemView.findViewById(R.id.progress)

            fun bind() {
                // Username & profile picture
                if (viewModel.username != null) {
                    usernameView.text = viewModel.username
                    usernameView.isVisible = true
                    levelView.textAlignment = View.TEXT_ALIGNMENT_VIEW_END
                } else {
                    usernameView.isVisible = false
                    levelView.textAlignment = View.TEXT_ALIGNMENT_CENTER
                }
                if (viewModel.url != null) {
                    Picasso.get()
                        .load(viewModel.url)
                        .transform(CircleTransform())
                        .noPlaceholder()
                        .into(iconView)
                    iconView.isVisible = true
                } else {
                    iconView.isVisible = false
                }

                // Get experience
                val experience = viewModel.experience.value ?: return
                levelView.text =
                    getString(R.string.banner_experience_gained_level, experience.level)
                progressBar.progress = experience.current.toInt()
                progressBar.max = experience.toNextLevel.toInt()
            }

            inner class CircleTransform : Transformation {
                override fun transform(source: Bitmap): Bitmap {
                    val size = source.width.coerceAtMost(source.height)
                    val x = (source.width - size) / 2
                    val y = (source.height - size) / 2
                    val squaredBitmap = Bitmap.createBitmap(source, x, y, size, size)
                    if (squaredBitmap != source) {
                        source.recycle()
                    }
                    val bitmap = Bitmap.createBitmap(size, size, source.config)
                    val canvas = Canvas(bitmap)
                    val paint = Paint()
                    paint.shader = BitmapShader(
                        squaredBitmap,
                        Shader.TileMode.CLAMP,
                        Shader.TileMode.CLAMP
                    )
                    paint.isAntiAlias = true
                    val r = size / 2f
                    canvas.drawCircle(r, r, r, paint)
                    squaredBitmap.recycle()
                    return bitmap
                }

                override fun key(): String {
                    return "circle"
                }
            }

        }

    }

    inner class AchievementsRecyclerViewAdapter(private val viewModel: GameProfileViewModel) :
        RecyclerView.Adapter<AchievementsRecyclerViewAdapter.AchievementViewHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): AchievementViewHolder {
            val root =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.profile_achievement, parent, false)
            return AchievementViewHolder(root)
        }

        override fun onBindViewHolder(
            holder: AchievementViewHolder,
            position: Int
        ) {
            holder.bind(
                viewModel.achievements.value!![position]
            )
        }

        override fun getItemCount(): Int {
            return viewModel.achievements.value?.size ?: 0
        }

        inner class AchievementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            private val iconView: ImageView = itemView.findViewById(R.id.icon)
            private val titleView: TextView = itemView.findViewById(R.id.title)
            private val progressBar: ProgressBar = itemView.findViewById(R.id.progress)

            fun bind(achievement: Achievement) {
                iconView.setImageResource(achievement.icon)
                titleView.text = achievement.text
                progressBar.isVisible = achievement.target > 1
                progressBar.progress = achievement.value.toInt()
                progressBar.max = achievement.target.toInt()

                ColorMatrix().apply {
                    setSaturation(if (achievement.unlocked) 1f else 0f)
                    iconView.colorFilter = android.graphics.ColorMatrixColorFilter(this)
                }
            }

        }

    }

}