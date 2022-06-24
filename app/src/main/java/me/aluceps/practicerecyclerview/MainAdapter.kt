package me.aluceps.practicerecyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import me.aluceps.practicerecyclerview.databinding.LayoutAdBinding
import me.aluceps.practicerecyclerview.databinding.LayoutItemBinding
import me.aluceps.practicerecyclerview.databinding.LayoutTitleBinding

class MainAdapter : RecyclerView.Adapter<MainAdapter.ListItem>() {

    private val items = mutableListOf<Payload>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItem =
        LayoutInflater.from(parent.context).let {
            return@let when (viewType) {
                VIEW_TYPE_TITLE -> it.inflate(R.layout.layout_title, parent, false).let {
                    ListItem.TitleViewHolder(it)
                }
                VIEW_TYPE_AD -> it.inflate(R.layout.layout_ad, parent, false).let {
                    ListItem.AdViewHolder(it)
                }
                else -> it.inflate(R.layout.layout_item, parent, false).let {
                    ListItem.ItemViewHolder(it)
                }
            }
        }

    override fun onBindViewHolder(holder: ListItem, position: Int) {
        when (val item = items[position]) {
            is Payload.Item -> (holder as ListItem.ItemViewHolder).bind(item)
            is Payload.Title -> (holder as ListItem.TitleViewHolder).bind(item)
            is Payload.Ad -> (holder as ListItem.AdViewHolder).bind(item)
        }
    }

    override fun onViewAttachedToWindow(holder: ListItem) {
        super.onViewAttachedToWindow(holder)
        when (holder) {
            is ListItem.AdViewHolder -> Log.d("###", "onViewAttachedToWindow: index=${holder.index}")
            else -> Unit
        }
    }

    override fun onViewDetachedFromWindow(holder: ListItem) {
        super.onViewDetachedFromWindow(holder)
        when (holder) {
            is ListItem.AdViewHolder -> Log.d("###", "onViewDetachedFromWindow: index=${holder.index}")
            else -> Unit
        }
    }

    override fun getItemCount(): Int =
        items.size

    override fun getItemViewType(position: Int): Int =
        items[position].getViewType()

    fun addAll(items: List<String>) {
        this.items.apply {
            add(Payload.Title("title"))
            addAll(mutableListOf<Payload>().apply {
                var index = 0
                items.forEachIndexed { i, s ->
                    if (isNotEmpty() && i % AD_INTERVAL == 0) {
                        add(Payload.Ad("Ad Test", index++))
                    }
                    add(Payload.Item(s))
                }
            })
        }
    }

    sealed class ListItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract val binding: ViewBinding

        class ItemViewHolder(itemView: View) : ListItem(itemView) {
            override val binding = LayoutItemBinding.bind(itemView)
            fun bind(data: Payload.Item) {
                binding.data.text = data.value
            }
        }

        class TitleViewHolder(itemView: View) : ListItem(itemView) {
            override val binding = LayoutTitleBinding.bind(itemView)
            fun bind(data: Payload.Title) {
                binding.data.text = data.value
            }
        }

        class AdViewHolder(itemView: View) : ListItem(itemView) {
            override val binding = LayoutAdBinding.bind(itemView)
            var index = 0
            fun bind(data: Payload.Ad) {
                index = data.index
                binding.data.text = "%s %d".format(data.value, data.index)
            }
        }
    }

    sealed class Payload {
        abstract fun getViewType(): Int

        data class Item(val value: String) : Payload() {
            override fun getViewType(): Int = VIEW_TYPE_ITEM
        }

        data class Title(val value: String) : Payload() {
            override fun getViewType(): Int = VIEW_TYPE_TITLE
        }

        data class Ad(val value: String, val index: Int) : Payload() {
            override fun getViewType(): Int = VIEW_TYPE_AD
        }
    }

    companion object {
        const val VIEW_TYPE_ITEM = 100
        const val VIEW_TYPE_TITLE = 200
        const val VIEW_TYPE_AD = 300
        const val AD_INTERVAL = 5
    }
}
