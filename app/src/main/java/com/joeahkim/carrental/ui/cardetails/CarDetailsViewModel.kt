package com.joeahkim.carrental.ui.cardetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joeahkim.carrental.data.local.DataStoreManager
import com.joeahkim.carrental.data.repository.CarRepository
import com.joeahkim.carrental.domain.model.CarDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CarDetailsUiState(
    val isLoading: Boolean = false,
    val carDetail: CarDetail? = null,
    val error: String? = null
)

@HiltViewModel
class CarDetailsViewModel @Inject constructor(
    private val carRepository: CarRepository,
    private val tokenManager: DataStoreManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(CarDetailsUiState())
    val uiState = _uiState.asStateFlow()

    fun loadCarDetails(carId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                // Get token from TokenManager
                val token = tokenManager.getToken().first()

                if (token.isNullOrEmpty()) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "Authentication token not found. Please login again."
                        )
                    }
                    return@launch
                }

                // Call repository with token
                val result = carRepository.getCarDetails(carId, token)

                result.onSuccess { carDetail ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            carDetail = carDetail,
                            error = null
                        )
                    }
                }.onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = exception.localizedMessage ?: "An error occurred"
                        )
                    }
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.localizedMessage ?: "An unknown error occurred"
                    )
                }
            }
        }
    }
}