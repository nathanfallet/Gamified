package me.nathanfallet.gamified.views

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.nathanfallet.gamified.R
import me.nathanfallet.gamified.events.AchievementUnlockedEvent
import me.nathanfallet.gamified.events.ExperienceGainedEvent
import me.nathanfallet.gamified.models.Experience
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

data class BannerView(
    val activity: Activity
) {

    // Properties

    private val handler = Handler(Looper.getMainLooper())
    private var popup: PopupWindow? = null

    // Lifecycle

    fun register() {
        EventBus.getDefault().register(this)
    }

    fun unregister() {
        EventBus.getDefault().unregister(this)
    }

    // Helpers

    private fun <T : Any> checkForNotificationInProgress(
        action: (T) -> Unit,
        notification: T
    ): Boolean {
        if (popup != null) {
            CoroutineScope(Job()).launch {
                delay(4000L)
                action(notification)
            }
            return true
        }
        return false
    }

    private fun generateBannerView(layout: Int, customize: (View) -> Unit) {
        val view = LayoutInflater.from(activity).inflate(layout, null)
        customize(view)
        popup = PopupWindow(
            view,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT,
            false
        )
        handler.post {
            popup?.showAsDropDown(activity.findViewById(android.R.id.content), 0, 0)
        }
        handler.postDelayed({
            popup?.dismiss()
            popup = null
        }, 3000L)
    }

    // Notifications

    @Subscribe
    fun experienceGained(notification: ExperienceGainedEvent) {
        // Check that no notification is already shown. Else, delay
        if (checkForNotificationInProgress(this::experienceGained, notification)) {
            return
        }

        generateBannerView(R.layout.notification_experience_gained) {
            it.findViewById<TextView>(R.id.description).text = activity.getString(
                R.string.banner_experience_gained_level,
                notification.previousExperience.level
            )
            it.findViewById<ProgressBar>(R.id.progress).apply {
                progress = notification.previousExperience.current.toInt() * 100
                max = notification.previousExperience.toNextLevel.toInt() * 100
                startAnimation(
                    ProgressBarAnimation(
                        it.findViewById(R.id.description),
                        this, notification.previousExperience,
                        notification.newExperience
                    )
                )
            }
        }
    }

    @Subscribe
    fun achievementUnlocked(notification: AchievementUnlockedEvent) {
        // Check that no notification is already shown. Else, delay
        if (checkForNotificationInProgress(this::achievementUnlocked, notification)) {
            return
        }

        generateBannerView(R.layout.notification_achievement_unlocked) {
            it.findViewById<ImageView>(R.id.icon).setImageResource(notification.achievement.icon)
            it.findViewById<TextView>(R.id.description).text = notification.achievement.name
        }
    }

    // Animation

    inner class ProgressBarAnimation(
        private val levelText: TextView,
        private val progressBar: ProgressBar,
        private val previous: Experience,
        private val new: Experience
    ) : Animation() {

        private var progressBarLevel = previous.level
        private var lastTime = 0f

        init {
            duration = 1000L
        }

        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            super.applyTransformation(interpolatedTime, t)

            // Add a small amount of progress
            val diff = (new.total - previous.total).toDouble()
            progressBar.progress = ((
                    if (progressBarLevel == previous.level) previous.current + diff * interpolatedTime
                    else previous.current - previous.toNextLevel + diff * interpolatedTime
                    ) * 100).toInt()

            // Check if we hit the max (to go to next level and play an effect)
            if (progressBarLevel != new.level && progressBar.progress >= previous.toNextLevel * 100) {
                progressBarLevel = new.level
                progressBar.progress = 0
                progressBar.max = new.toNextLevel.toInt() * 100
                levelText.text =
                    activity.getString(R.string.banner_experience_gained_level, progressBarLevel)
            }

            lastTime = interpolatedTime
        }

    }

}