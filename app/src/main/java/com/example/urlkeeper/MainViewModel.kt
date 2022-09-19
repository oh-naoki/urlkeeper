package com.example.urlkeeper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val ogpParseUseCase: OgpParseUseCase
) : ViewModel() {


    fun request() {
        viewModelScope.launch {
            val ogpMeta = ogpParseUseCase.getOgp("https://flutter.dev/")
        }
    }
}