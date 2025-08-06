package com.minhyuuk.dashnote.ui.screen

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
import com.minhyuuk.dashnote.R
import com.minhyuuk.dashnote.ui.extensions.dashedBorder
import com.minhyuuk.dashnote.ui.theme.DashNoteTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.clickable
import com.minhyuuk.dashnote.data.model.memo.MemoData
import com.minhyuuk.dashnote.ui.components.MemoCard


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoListScreen() {
    var searchText by remember { mutableStateOf("") }
    var selectedSortOrder by remember { mutableStateOf("최신순") }
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
                                    color = MaterialTheme.colorScheme.outline,
                                    cornerRadius = with(LocalDensity.current) { 10.dp.toPx() }
                                )
                                .wrapContentHeight()
                                .clickable { expanded = !expanded }
                        ) {
                            Text(
                                text = selectedSortOrder,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface,
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
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("최신순") },
                                onClick = {
                                    selectedSortOrder = "최신순"
                                    expanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("제목순") },
                                onClick = {
                                    selectedSortOrder = "제목순"
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
            FloatingActionButton(
                onClick = { /* 메모 추가 */ },
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(4.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Memo",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
                    onValueChange = { searchText = it },
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(10) { index ->
                    MemoCard(
                        memoData = MemoData(
                            title = "메모 제목 $index",
                            description = "메모 내용이 여기에 들어갑니다. 이 메모는 예시로 작성된 것입니다.",
                            createdDate = "2023-10-01",
                            createdTime = "12:00 PM"
                        ),
                        isFirstItem = index == 0,
                        onCardClick = {},
                        onDeleteClick = {}
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
        MemoListScreen()
    }
}