package me.aluceps.practicerecyclerview

import android.util.Log
import android.view.View
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Section
import com.xwray.groupie.viewbinding.BindableItem
import me.aluceps.practicerecyclerview.databinding.LayoutAdBinding
import me.aluceps.practicerecyclerview.databinding.LayoutItemBinding
import me.aluceps.practicerecyclerview.databinding.LayoutTitleBinding

class MainAdapter : GroupAdapter<GroupieViewHolder>() {

    private val section = Section()

    init {
        add(section)
    }

    fun addAll(items: List<String>) {
        section.add(TitleViewHolder(Payload.Title("title")))
        mutableListOf<Payload>().apply {
            items.forEachIndexed { i, s ->
                var adIndex = 0
                if (isNotEmpty() && i % AD_INTERVAL == 0) {
                    add(Payload.Ad("Ad Test", adIndex++))
                }
                add(Payload.Item(s))
            }
        }.map {
            when (it) {
                is Payload.Ad -> AdViewHolder(it)
                is Payload.Item -> ItemViewHolder(it)
                else -> return
            }
        }.let {
            section.addAll(it)
        }
    }

    class ItemViewHolder(private val data: Payload.Item) :
        BindableItem<LayoutItemBinding>(data.hashCode().toLong()) {
        override fun bind(viewBinding: LayoutItemBinding, position: Int) {
            viewBinding.data.text = data.value
        }

        override fun getLayout(): Int = R.layout.layout_item

        override fun initializeViewBinding(view: View): LayoutItemBinding =
            LayoutItemBinding.bind(view)
    }

    class TitleViewHolder(private val data: Payload.Title) :
        BindableItem<LayoutTitleBinding>(data.hashCode().toLong()) {
        override fun bind(viewBinding: LayoutTitleBinding, position: Int) {
            viewBinding.data.text = data.value
        }

        override fun getLayout(): Int = R.layout.layout_title

        override fun initializeViewBinding(view: View): LayoutTitleBinding =
            LayoutTitleBinding.bind(view)
    }

    class AdViewHolder(private val data: Payload.Ad) :
        BindableItem<LayoutAdBinding>(data.hashCode().toLong()) {
        override fun bind(viewBinding: LayoutAdBinding, position: Int) {
            viewBinding.data.text = data.value
        }

        override fun getLayout(): Int = R.layout.layout_ad

        override fun initializeViewBinding(view: View): LayoutAdBinding =
            LayoutAdBinding.bind(view)

        override fun onViewAttachedToWindow(viewHolder: com.xwray.groupie.viewbinding.GroupieViewHolder<LayoutAdBinding>) {
            super.onViewAttachedToWindow(viewHolder)
            Log.d("###", "onViewAttachedToWindow")
        }

        override fun onViewDetachedFromWindow(viewHolder: com.xwray.groupie.viewbinding.GroupieViewHolder<LayoutAdBinding>) {
            super.onViewDetachedFromWindow(viewHolder)
            Log.d("###", "onViewDetachedFromWindow")
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
