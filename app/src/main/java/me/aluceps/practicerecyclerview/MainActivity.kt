package me.aluceps.practicerecyclerview

import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.aluceps.practicerecyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupView()
        observeViewModel()
        viewModel.createDummy()
    }

    private fun setupView() {
        binding.recyclerView.apply {
            adapter = MainAdapter()
            layoutManager = LinearLayoutManager(context)
            if (itemDecorationCount == 0) {
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(
                        outRect: Rect,
                        itemPosition: Int,
                        parent: RecyclerView
                    ) {
                        outRect.left = 16.toDp(resources)
                        outRect.right = 16.toDp(resources)
                        if (itemPosition > 0) {
                            outRect.top = 12.toDp(resources)
                        }
                    }

                    private fun Int.toDp(resources: Resources) =
                        (this * resources.displayMetrics.density).toInt()
                })
            }
        }
    }

    private fun observeViewModel() {
        viewModel.data.observe(this) {
            if (it == null) return@observe
            (binding.recyclerView.adapter as MainAdapter).addAll(it)
        }
    }
}
