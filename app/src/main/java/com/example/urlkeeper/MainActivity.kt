package com.example.urlkeeper

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
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
            var openRegisterDialog by remember { mutableStateOf(false) }

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
                            onClick = { viewModel.request() },
                            onDeleteClick = {}
                        )
                    }
                }

                Button(
                    modifier = Modifier
                        .padding(32.dp)
                        .size(32.dp)
                        .align(Alignment.BottomEnd),
                    onClick = { openRegisterDialog = true }
                ) {
                    Icon(
                        painterResource(id = coil.base.R.drawable.ic_100tb),
                        contentDescription = null
                    )
                }

                if (openRegisterDialog) {
                    RegisterUrlDialog(
                        onSubmit = { viewModel.registerUrl(it) },
                        onDismiss = { openRegisterDialog = false }
                    )
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
    onClick: (String) -> Unit,
    onDeleteClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                onClick(url)
            }
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            SiteImage(imageUrl = imageUrl.takeIf { it.isNotBlank() })
            Image(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.TopEnd)
                    .clickable {
                        onDeleteClick()
                    },
                painter = painterResource(id = coil.base.R.drawable.ic_100tb),
                contentDescription = null
            )
        }
        Text(text = title, style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = description,
            style = TextStyle(fontSize = 12.sp, color = Color.DarkGray)
        )
    }
}

@Composable
fun RegisterUrlDialog(
    onSubmit: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val focus = remember { FocusRequester() }
    val (value, onValueChanged) = remember { mutableStateOf("") }
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier.background(color = Color.White)
        ) {
            TextField(
                modifier = Modifier.focusRequester(focus),
                value = value,
                onValueChange = onValueChanged,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions {
                    onSubmit(value)
                    onDismiss()
                }
            )
        }
    }
}

@Composable
fun SiteImage(imageUrl: String?) {
    if (imageUrl == null) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null
        )
    } else {
        AsyncImage(model = imageUrl, contentDescription = null)
    }
}

@Preview
@Composable
fun PreviewOgpPreview() {
    Box(modifier = Modifier.background(Color.White)) {
        OgpPreview(
            title = "Flutter - Build apps for any screen",
            description = "Flutter transforms the entire app development process. Build, test, and deploy beautiful mobile, web, desktop, and embedded apps from a single codebase.",
            imageUrl = "",
            url = "https://flutter.dev/",
            onClick = {},
            onDeleteClick = {}
        )
    }
}