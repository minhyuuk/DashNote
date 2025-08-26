package com.minhyuuk.dashnote.ui.screen.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.minhyuuk.dashnote.R
import com.minhyuuk.dashnote.ui.extensions.dashedBorder
import com.minhyuuk.dashnote.ui.theme.DashNoteTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.clickable
import com.minhyuuk.dashnote.data.model.memo.MemoData
import com.minhyuuk.dashnote.ui.components.MemoCard
import com.minhyuuk.dashnote.ui.components.EmptyState
import com.minhyuuk.dashnote.ui.screen.main.viewmodel.MemoListViewModelInterface
import com.minhyuuk.dashnote.ui.screen.main.viewmodel.FakeMemoListViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoListScreen(
    viewModel: MemoListViewModelInterface,
    onCreateMemoClick: () -> Unit = {}
) {
    val memoList by viewModel.memos.collectAsStateWithLifecycle()
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    val selectedSortOrder by viewModel.sortOrder.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    
    var expanded by remember { mutableStateOf(false) }

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
                            text = stringResource(id = R.string.app_name),
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                },
                actions = {
                    Box {
                        Box(
                            modifier = Modifier
                                .dashedBorder(
                                    strokeWidth = with(LocalDensity.current) { 1.dp.toPx() },
                                    color = if (isLoading) 
                                        MaterialTheme.colorScheme.outlineVariant 
                                    else 
                                        MaterialTheme.colorScheme.outline,
                                    cornerRadius = with(LocalDensity.current) { 10.dp.toPx() }
                                )
                                .wrapContentHeight()
                                .clickable(enabled = !isLoading) { expanded = !expanded }
                        ) {
                            Text(
                                text = selectedSortOrder,
                                fontSize = 14.sp,
                                color = if (isLoading) 
                                    MaterialTheme.colorScheme.onSurfaceVariant 
                                else 
                                    MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(horizontal = 24.dp, vertical = 8.dp)
                                    .padding(end = 8.dp)
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_keyboard_arrow_down_24),
                                contentDescription = "정렬 옵션 선택",
                                modifier = Modifier
                                    .size(24.dp)
                                    .align(Alignment.CenterEnd)
                                    .padding(end = 8.dp),
                                tint = if (isLoading) 
                                    MaterialTheme.colorScheme.onSurfaceVariant 
                                else 
                                    MaterialTheme.colorScheme.onSurface
                            )
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("최신순") },
                                onClick = {
                                    viewModel.updateSortOrder("최신순")
                                    expanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("제목순") },
                                onClick = {
                                    viewModel.updateSortOrder("제목순")
                                    expanded = false
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        floatingActionButton = {
            Box(
                modifier = Modifier.navigationBarsPadding()
            ) {
                FloatingActionButton(
                    onClick = if (isLoading) {{ }} else onCreateMemoClick,
                    modifier = Modifier.size(48.dp),
                    shape = RoundedCornerShape(4.dp),
                    containerColor = if (isLoading) 
                        MaterialTheme.colorScheme.surfaceVariant 
                    else 
                        MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Memo",
                        modifier = Modifier.size(24.dp),
                        tint = if (isLoading) 
                            MaterialTheme.colorScheme.onSurfaceVariant 
                        else 
                            MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // 검색바
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                        .dashedBorder(
                            strokeWidth = with(LocalDensity.current) { 1.dp.toPx() },
                            color = MaterialTheme.colorScheme.outline,
                            cornerRadius = with(LocalDensity.current) { 10.dp.toPx() }
                        )
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    BasicTextField(
                        value = searchText,
                        onValueChange = if (isLoading) {{ }} else viewModel::updateSearchText,
                        enabled = !isLoading,
                        textStyle = TextStyle(
                            color = if (isLoading) 
                                MaterialTheme.colorScheme.onSurfaceVariant 
                            else 
                                MaterialTheme.colorScheme.onSurface,
                            fontSize = 16.sp
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        decorationBox = { innerTextField ->
                            if (searchText.isEmpty()) {
                                Text(
                                    text = stringResource(id = R.string.search_note_hint),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 16.sp
                                )
                            }
                            innerTextField()
                        }
                    )
                }

                if(memoList.isNotEmpty()){
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 80.dp)
                    ) {
                        items(memoList.size) { index ->
                            MemoCard(
                                memoData = memoList[index],
                                isFirstItem = index == 0,
                                onCardClick = {},
                                onDeleteClick = { 
                                    viewModel.deleteMemo(memoList[index])
                                }
                            )
                        }
                    }
                }
            }
            
            // 로딩 상태 처리
            when {
                isLoading -> {
                    // 로딩 인디케이터
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 100.dp),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Card(
                            modifier = Modifier.padding(16.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(20.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    strokeWidth = 2.dp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "메모를 불러오는 중...",
                                    modifier = Modifier.padding(start = 16.dp),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
                memoList.isEmpty() -> {
                    EmptyState(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MemoListScreenPreview() {
    DashNoteTheme {
        MemoListScreen(
            viewModel = FakeMemoListViewModel
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MemoListScreenWithDataPreview() {
    DashNoteTheme {
        MemoListScreen(
            viewModel = FakeMemoListViewModel
        )
    }
}