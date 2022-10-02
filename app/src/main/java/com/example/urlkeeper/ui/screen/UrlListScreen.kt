package com.example.urlkeeper

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.urlkeeper.model.OgpMeta

@Composable
fun UrlListScreen(
    toWebViewContent: (String) -> Unit,
    viewModel: MainViewModel
) {
    val uiState = viewModel.uiState
    var openRegisterDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        UrlList(ogpList = uiState.ogpList, toWebViewContent = toWebViewContent)

        RegisterButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = {
                openRegisterDialog = true
            }
        )

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

@Composable
private fun UrlList(
    ogpList: List<OgpMeta>,
    toWebViewContent: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(ogpList) {
            OgpPreview(
                title = it.title,
                description = it.description,
                imageUrl = it.imageUrl,
                url = it.url,
                onClick = {
                    toWebViewContent(it)
                },
                onDeleteClick = {}
            )
        }
    }
}

@Composable
private fun OgpPreview(
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            SiteImage(
                modifier = Modifier.align(Alignment.Center),
                imageUrl = imageUrl.takeIf { it.isNotBlank() }
            )
            Image(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.TopEnd)
                    .clickable {
                        onDeleteClick()
                    },
                painter = rememberVectorPainter(image = Icons.Default.Delete),
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
private fun RegisterButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier
            .padding(32.dp),
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier.size(48.dp),
            imageVector = Icons.Default.AddCircle,
            tint = Color.Black,
            contentDescription = null
        )
    }
}

@Composable
private fun RegisterUrlDialog(
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
private fun SiteImage(
    modifier: Modifier = Modifier,
    imageUrl: String?
) {
    if (imageUrl == null) {
        Text(
            text = "No Image",
            style = TextStyle(
                color = Color.DarkGray,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            ),
        )
    } else {
        AsyncImage(
            modifier = modifier,
            model = imageUrl,
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
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