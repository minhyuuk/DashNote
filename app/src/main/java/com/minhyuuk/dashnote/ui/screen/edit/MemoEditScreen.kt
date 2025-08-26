package com.minhyuuk.dashnote.ui.screen.edit

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.activity.compose.BackHandler
import com.minhyuuk.dashnote.R
import com.minhyuuk.dashnote.ui.theme.DashNoteTheme
import com.minhyuuk.dashnote.ui.extensions.dashedBorder
import com.minhyuuk.dashnote.ui.screen.edit.viewmodel.FakeMemoEditViewModel
import com.minhyuuk.dashnote.ui.screen.edit.viewmodel.MemoEditViewModelInterface
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoEditScreen(
    onBackClick: () -> Unit = {},
    viewModel: MemoEditViewModelInterface
) {
    val titleText by viewModel.title.collectAsStateWithLifecycle()
    val descriptionText by viewModel.description.collectAsStateWithLifecycle()
    val isSaving by viewModel.isSaving.collectAsStateWithLifecycle()
    
    val handleBackClick = {
        Timber.d("뒤로가기 버튼 클릭 - 메모 저장 시작")
        Timber.v("현재 제목: '${titleText}', 현재 내용: '${descriptionText}'")
        viewModel.saveMemo(onBackClick)
    }
    
    BackHandler(enabled = !isSaving) {
        Timber.d("하드웨어 백 버튼/제스처 - 메모 저장 시작")
        handleBackClick()
    }

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
                    IconButton(
                        onClick = handleBackClick,
                        enabled = !isSaving
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = if (isSaving) 
                                MaterialTheme.colorScheme.onSurfaceVariant 
                            else 
                                MaterialTheme.colorScheme.onSurface
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
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                BasicTextField(
                    value = titleText,
                    onValueChange = if (isSaving) {{ }} else viewModel::updateTitle,
                    enabled = !isSaving,
                    textStyle = TextStyle(
                        color = if (isSaving) 
                            MaterialTheme.colorScheme.onSurfaceVariant 
                        else 
                            MaterialTheme.colorScheme.onSurface,
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
                        value = descriptionText,
                        onValueChange = if (isSaving) {{ }} else viewModel::updateDescription,
                        enabled = !isSaving,
                        textStyle = TextStyle(
                            color = if (isSaving) 
                                MaterialTheme.colorScheme.onSurfaceVariant 
                            else 
                                MaterialTheme.colorScheme.onSurface,
                            fontSize = 18.sp
                        ),
                        modifier = Modifier.fillMaxSize(),
                        decorationBox = { innerTextField ->
                            if (descriptionText.isEmpty()) {
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
            
            // 저장 중 인디케이터
            if (isSaving) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier.padding(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp
                            )
                            Text(
                                text = "저장 중...",
                                modifier = Modifier.padding(start = 16.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MemoEditScreenPreview() {
    DashNoteTheme {
        MemoEditScreen(
            viewModel = FakeMemoEditViewModel
        )
    }
}
