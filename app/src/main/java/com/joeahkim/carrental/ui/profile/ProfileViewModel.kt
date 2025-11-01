package com.joeahkim.carrental.ui.profile


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joeahkim.carrental.data.model.Client
import com.joeahkim.carrental.data.remote.ProfileApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val api: ProfileApiService
) : ViewModel() {

    private val _client = MutableStateFlow<Client?>(null)
    val client: StateFlow<Client?> = _client

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadProfile(token: String) {
        viewModelScope.launch {
            try {
                val client = api.getProfile(token) // This uses Gson automatically
                _client.value = client
                println("DEBUG: Client = $client")
            } catch (e: Exception) {
                _error.value = e.localizedMessage
                println("DEBUG: Error = ${e.localizedMessage}")
            }
        }
    }

}
