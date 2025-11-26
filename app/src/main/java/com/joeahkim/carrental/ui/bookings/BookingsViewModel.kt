package com.joeahkim.carrental.ui.bookings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joeahkim.carrental.data.local.DataStoreManager
import com.joeahkim.carrental.domain.usecase.GetBookingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingsViewModel @Inject constructor(
    private val getBookingsUseCase: GetBookingsUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(BookingsState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        loadBookings()
    }

    private fun loadBookings() {
        viewModelScope.launch {
            dataStoreManager.getToken().collect { token ->
                if (token.isNullOrEmpty()) {
                    _uiState.update { it.copy(isLoading = false, error = "Not logged in") }
                    return@collect
                }

                getBookingsUseCase(token).fold(
                    onSuccess = { bookings ->
                        _uiState.update { BookingsState(bookings = bookings) }
                    },
                    onFailure = { throwable ->
                        _uiState.update { it.copy(isLoading = false, error = throwable.message) }
                    }
                )
            }
        }
    }
}