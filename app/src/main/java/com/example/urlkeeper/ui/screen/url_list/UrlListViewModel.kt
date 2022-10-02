package com.example.urlkeeper.ui.screen.url_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.urlkeeper.model.OgpMeta
import com.example.urlkeeper.repository.UrlRepository
import com.example.urlkeeper.usecase.DeleteUrlUseCase
import com.example.urlkeeper.usecase.OgpParseUseCase
import com.example.urlkeeper.usecase.RegisterUrlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UrlListViewModel @Inject constructor(
    private val ogpParseUseCase: OgpParseUseCase,
    private val urlRepository: UrlRepository,
    private val registerUrlUseCase: RegisterUrlUseCase,
    private val deleteUrlUseCase: DeleteUrlUseCase
) : ViewModel() {

    var uiState by mutableStateOf(UiState())
        private set

    init {
        request()
    }

    private fun request() {
        viewModelScope.launch {
            uiState = uiState.copy(loading = true)

            urlRepository.getUrls().onEach {
                // TODO: 並列実行数設定
                val result = it.urls.map {
                    async { ogpParseUseCase.getOgp(it.id, it.url) }
                }.awaitAll()
                uiState = uiState.copy(loading = false, ogpList = result)
            }.launchIn(this)
        }
    }

    // TODO: エラーハンドリング

    fun registerUrl(url: String) {
        registerUrlUseCase.registerUrl(url = url)
            .catch {
                uiState = uiState.copy(error = it)
            }.onEach {
                request()
            }.launchIn(viewModelScope)
    }

    fun deleteUrl(id: String) {
        deleteUrlUseCase.deleteUrl(id)
            .onStart {
                uiState = uiState.copy(loading = true)
            }
            .onCompletion { request() }
            .launchIn(viewModelScope)
    }
}

data class UiState(
    val loading: Boolean = false,
    val ogpList: List<OgpMeta> = emptyList(),
    val error: Throwable? = null
)