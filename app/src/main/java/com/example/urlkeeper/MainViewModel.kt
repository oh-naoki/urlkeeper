package com.example.urlkeeper

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val ogpParseUseCase: OgpParseUseCase,
    private val urlRepository: UrlRepository,
    private val registerUrlUseCase: RegisterUrlUseCase
) : ViewModel() {

    var uiState by mutableStateOf(UiState())
        private set

    fun request() {
        viewModelScope.launch {
            uiState = uiState.copy(loading = true)

            urlRepository.getUrls().onEach {
                val result = it.urls.map {
                    async { ogpParseUseCase.getOgp(it.url) }
                }.awaitAll()
                uiState = uiState.copy(loading = false, ogpList = result)
            }.launchIn(this)
        }
    }

    fun registerUrl(url: String) {
        registerUrlUseCase.registerUrl(url = url)
            .catch {
                uiState = uiState.copy(error = it)
            }.launchIn(viewModelScope)
    }
}

data class UiState(
    val loading: Boolean = false,
    val ogpList: List<OgpMeta> = emptyList(),
    val error: Throwable? = null
)