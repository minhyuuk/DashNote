package com.minhyuuk.dashnote.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
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
                            color = Color.Black,
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
                                    color = colorResource(id = R.color.gray_b8b8b8),
                                    cornerRadius = with(LocalDensity.current) { 10.dp.toPx() }
                                )
                                .wrapContentHeight()
                                .clickable { expanded = !expanded }
                        ) {
                            Text(
                                text = selectedSortOrder,
                                fontSize = 14.sp,
                                color = Color.Black,
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
                                tint = Color.Black
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
                    containerColor = Color.White
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
                    tint = Color.White
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
                        color = colorResource(id = R.color.gray_b8b8b8),
                        cornerRadius = with(LocalDensity.current) { 10.dp.toPx() }
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                BasicTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    decorationBox = { innerTextField ->
                        if (searchText.isEmpty()) {
                            Text(
                                text = stringResource(id = R.string.search_note_hint),
                                color = colorResource(id = R.color.gray_b8b8b8),
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
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // 메모 아이템들이 여기에 추가될 예정
                items(10) { index ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .border(
                                width = 1.dp,
                                color = colorResource(id = R.color.gray_b8b8b8),
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "메모 아이템 $index (구현 예정)",
                            color = colorResource(id = R.color.gray_b8b8b8)
                        )
                    }
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