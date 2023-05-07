package me.nathanfallet.gamified.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.nathanfallet.gamified.R

class HeaderRecyclerViewAdapter(
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