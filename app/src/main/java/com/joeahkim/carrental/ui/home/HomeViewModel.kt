package com.joeahkim.carrental.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joeahkim.carrental.data.local.DataStoreManager
import com.joeahkim.carrental.domain.usecase.GetHomeScreenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeScreenUseCase: GetHomeScreenUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val token = dataStoreManager.getToken().firstOrNull()

            if (token.isNullOrEmpty()) {
                _uiState.update {
                    it.copy(isLoading = false, error = "Not logged in")
                }
                return@launch
            }

            _uiState.update { it.copy(isLoading = true, error = null) }

            getHomeScreenUseCase(token)
                .onSuccess { data ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            availableCars = data.availableCars,
                            topCars = data.topCars
                        )
                    }
                }
                .onFailure { throwable ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = throwable.message ?: "Something went wrong"
                        )
                    }
                }
        }
    }
}
