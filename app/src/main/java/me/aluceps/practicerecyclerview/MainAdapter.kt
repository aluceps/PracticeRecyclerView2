package me.aluceps.practicerecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.aluceps.practicerecyclerview.databinding.LayoutItemBinding

class MainAdapter : RecyclerView.Adapter<MainAdapter.ListItem>() {

    private val items = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItem =
        LayoutInflater.from(parent.context).inflate(R.layout.layout_item, parent, false)
            .let {
                ListItem.ItemViewHolder(it)
            }

    override fun onBindViewHolder(holder: ListItem, position: Int) {
        (holder as ListItem.ItemViewHolder).let {
            it.binding.data.text = items[position]
        }
    }

    override fun getItemCount(): Int =
        items.size

    fun addAll(items: List<String>) {
        this.items.addAll(items)
    }

    sealed class ListItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        class ItemViewHolder(itemView: View) : ListItem(itemView) {
            val binding = LayoutItemBinding.bind(itemView)
        }
    }
}
