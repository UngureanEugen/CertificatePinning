package com.certificatepinning.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.certificatepinning.data.LocationItem
import com.certificatepinning.data.Store
import com.certificatepinning.usecase.LocationUpdaterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val locationUpdaterUseCase: LocationUpdaterUseCase,
    private val repository: StoreRepository
) : ViewModel() {

    private val _state: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)

    val state: StateFlow<UiState> = _state

    fun listenLocation() = viewModelScope.launch {
        locationUpdaterUseCase.execute().collectLatest { location ->
            val response = repository.getStores(location, 1)
            if (response.isSuccessful) {
                _state.emit(UiState.Success(stores = response.body()?.stores ?: emptyList()))
            } else {
                _state.emit(UiState.Error(response.errorBody()?.string() ?: ""))
            }
        }
    }
}

sealed class UiState {
    data class Success(
        val location: LocationItem = LocationItem(),
        val stores: List<Store> = emptyList()
    ) : UiState()

    object Loading : UiState()

    data class Error(val exception: String) : UiState()
}
