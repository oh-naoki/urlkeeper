package com.example.urlkeeper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.request()
        setContent {
            val uiState = viewModel.uiState
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    uiState.ogpList.forEach {
                        OgpPreview(
                            title = it.title,
                            description = it.description,
                            imageUrl = it.imageUrl,
                            url = it.url,
                            onClick = { viewModel.request() }
                        )
                    }
                }

                if (uiState.loading) {
                    Text(modifier = Modifier.align(Alignment.Center), text = "Loading")
                }
            }
        }
    }
}

@Composable
fun OgpPreview(
    title: String,
    description: String,
    imageUrl: String,
    url: String,
    onClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                onClick(url)
            }
    ) {
        AsyncImage(model = imageUrl, contentDescription = null)
        Text(text = title, style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = description,
            style = TextStyle(fontSize = 12.sp, color = Color.DarkGray)
        )
    }
}

@Preview
@Composable
fun PreviewOgpPreview() {
    OgpPreview(
        title = "Flutter - Build apps for any screen",
        description = "Flutter transforms the entire app development process. Build, test, and deploy beautiful mobile, web, desktop, and embedded apps from a single codebase.",
        imageUrl = "https://storage.googleapis.com/cms-storage-bucket/70760bf1e88b184bb1bc.png",
        url = "https://flutter.dev/",
        onClick = {}
    )
}