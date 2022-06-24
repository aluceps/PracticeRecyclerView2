package me.aluceps.practicerecyclerview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _data: MutableLiveData<List<String>> = MutableLiveData()
    val data: LiveData<List<String>> = _data

    fun createDummy() =
        viewModelScope.launch {
            _data.value = mutableListOf<String>().apply {
                (0..100).forEach {
                    add("data: $it")
                }
            }
        }
}
