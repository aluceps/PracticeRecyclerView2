package me.aluceps.practicerecyclerview

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
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
        }
    }

    private fun observeViewModel() {
        viewModel.data.observe(this) {
            if (it == null) return@observe
            (binding.recyclerView.adapter as MainAdapter).addAll(it)
        }
    }
}
