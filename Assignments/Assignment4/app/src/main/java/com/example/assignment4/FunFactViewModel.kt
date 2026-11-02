package com.example.assignment4

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FunFactViewModel(private val repo: FunFactRepo) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // Re-queries Room every time the search text changes; Room's Flow also
    // re-emits whenever a new fact is inserted, so the list updates immediately.
    @OptIn(ExperimentalCoroutinesApi::class)
    val facts: StateFlow<List<FunFact>> = _query
        .flatMapLatest { repo.getFunFacts(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    fun fetchFact() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            repo.fetchAndStoreFact()
                .onFailure { _error.value = "Couldn't fetch a fact. Check your connection." }
            _isLoading.value = false
        }
    }
}

class FunFactViewModelFactory(private val repo: FunFactRepo) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FunFactViewModel::class.java)) {
            return FunFactViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}