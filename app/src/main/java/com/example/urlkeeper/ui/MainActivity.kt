package com.example.urlkeeper.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.urlkeeper.MainViewModel
import com.example.urlkeeper.UrlListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "urllist") {
                composable("urllist") {
                    val viewModel: MainViewModel = hiltViewModel()
                    UrlListScreen(
                        toWebViewContent = {
                            navController.navigate("")
                        },
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}


