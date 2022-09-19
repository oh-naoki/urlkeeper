package com.example.urlkeeper

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val ogpParseUseCase: OgpParseUseCase
) : ViewModel() {

    var uiState by mutableStateOf(UiState())
        private set

    fun request() {
        viewModelScope.launch {
            uiState = uiState.copy(loading = true)
            val result = listOf(
                "https://flutter.dev/",
                "https://flutter.dev/",
                "https://flutter.dev/",
                "https://flutter.dev/",
                "https://flutter.dev/",
                "https://flutter.dev/",
                "https://flutter.dev/",
                "https://flutter.dev/",
                "https://flutter.dev/",
                "https://flutter.dev/",
                "https://flutter.dev/",
                "https://flutter.dev/",
                "https://flutter.dev/",
                "https://flutter.dev/",
            ).map {
                async { ogpParseUseCase.getOgp(it) }
            }.awaitAll()
            uiState = uiState.copy(loading = false, ogpList = result)
        }
    }
}

data class UiState(
    val loading: Boolean = false,
    val ogpList: List<OgpMeta> = emptyList()
)