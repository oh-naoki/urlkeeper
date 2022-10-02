package com.example.urlkeeper.ui.screen

import androidx.compose.runtime.Composable
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewNavigator
import com.google.accompanist.web.rememberWebViewState

@Composable
fun WebViewScreen(url: String) {
    val state = rememberWebViewState(url = url)
    val navigator = rememberWebViewNavigator()

    WebView(
        state = state,
        navigator = navigator,
        onCreated = { webView ->
            webView.settings.javaScriptEnabled = true
        }
    )
}