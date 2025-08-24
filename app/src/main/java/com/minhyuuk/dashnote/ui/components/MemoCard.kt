package com.minhyuuk.dashnote.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minhyuuk.dashnote.data.model.memo.MemoData
import com.minhyuuk.dashnote.ui.extensions.dashedBorder
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoCard(
    memoData: MemoData,
    isFirstItem: Boolean = false,
    onCardClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    val density = LocalDensity.current
    var offsetX by remember { mutableFloatStateOf(0f) }
    val maxSwipeDistance = with(density) { 100.dp.toPx() }
    val topPadding = if (isFirstItem) 8.dp else 0.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = topPadding)
            .dashedBorder(
                strokeWidth = with(LocalDensity.current) { 1.dp.toPx() },
                color = MaterialTheme.colorScheme.outline,
                cornerRadius = with(LocalDensity.current) { 10.dp.toPx() }
            )
    ) {
        // 삭제 버튼 배경 (뒤쪽에 위치)
        if (offsetX < -50f) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.Red)
                    .clickable {
                        onDeleteClick()
                        offsetX = 0f
                    },
                contentAlignment = Alignment.CenterEnd
            ) {
                Row(
                    modifier = Modifier.padding(end = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "삭제",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "삭제",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }

        // 메모 카드 (앞쪽에 위치)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .offset { IntOffset(offsetX.roundToInt(), 0) }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            // 스와이프 거리에 따라 원래 위치로 복귀하거나 삭제 위치 유지
                            offsetX = if (offsetX < -maxSwipeDistance / 2) {
                                -maxSwipeDistance
                            } else {
                                0f
                            }
                        }
                    ) { _, dragAmount ->
                        // 왼쪽으로만 드래그 가능하도록 제한
                        val newOffset = offsetX + dragAmount
                        offsetX = newOffset.coerceIn(-maxSwipeDistance, 0f)
                    }
                }
                .clickable {
                    if (offsetX == 0f) {
                        onCardClick()
                    } else {
                        offsetX = 0f
                    }
                },
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                // Title과 Date가 같은 행에 위치 (6:4 비율)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    // Title (6/10 비율)
                    Text(
                        text = memoData.title ?: "",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .weight(0.6f)
                            .padding(start = 8.dp)
                    )

                    // Date (4/10 비율)
                    Text(
                        text = memoData.createdDate,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .weight(0.4f)
                            .padding(end = 8.dp),
                        textAlign = TextAlign.End
                    )
                }

                // Description
                Text(
                    text = memoData.description ?: "",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, top = 8.dp)
                        .weight(1f)
                )

                // Time과 Export dot
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "메모 옵션",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier =
                            Modifier
                                .size(16.dp)
                                .rotate(90f)
                    )
                    Text(
                        text = memoData.createdTime,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MemoCardPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            MemoCard(
                memoData = MemoData(
                    title = "오늘의 할일 목록",
                    description = "1. 프로젝트 완료하기\n2. 운동하기\n3. 책 읽기\n4. 친구와 만나기",
                    createdDate = "2024.01.15",
                    createdTime = "14:30"
                )
            )

            MemoCard(
                memoData = MemoData(
                    title = "긴 제목을 가진 메모입니다 이것은 한 줄을 넘어갈 것입니다",
                    description = "짧은 설명",
                    createdDate = "2024.01.14",
                    createdTime = "09:15"
                )
            )
        }
    }
}