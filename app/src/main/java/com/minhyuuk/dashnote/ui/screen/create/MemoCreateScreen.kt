package com.minhyuuk.dashnote.ui.screen.create

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minhyuuk.dashnote.R
import com.minhyuuk.dashnote.ui.extensions.dashedBorder
import com.minhyuuk.dashnote.ui.theme.DashNoteTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoCreateScreen(
    onBackClick: () -> Unit = {}
) {
    var titleText by remember { mutableStateOf("") }
    var contentText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_note_24),
                            contentDescription = "App Icon",
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = "New Note",
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            BasicTextField(
                value = titleText,
                onValueChange = { titleText = it },
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier.fillMaxWidth(),
                decorationBox = { innerTextField ->
                    if (titleText.isEmpty()) {
                        Text(
                            text = stringResource(R.string.write_title),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    innerTextField()
                }
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .dashedBorder(
                        strokeWidth = with(LocalDensity.current) { 1.dp.toPx() },
                        color = MaterialTheme.colorScheme.outline,
                        cornerRadius = with(LocalDensity.current) { 10.dp.toPx() }
                    )
                    .padding(20.dp)
            ) {
                BasicTextField(
                    value = contentText,
                    onValueChange = { contentText = it },
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 18.sp
                    ),
                    modifier = Modifier.fillMaxSize(),
                    decorationBox = { innerTextField ->
                        if (contentText.isEmpty()) {
                            Text(
                                text = stringResource(R.string.write_description),
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 18.sp
                            )
                        }
                        innerTextField()
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MemoCreateScreenPreview() {
    DashNoteTheme {
        MemoCreateScreen()
    }
}