package com.minhyuuk.dashnote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.minhyuuk.dashnote.ui.screen.MemoListScreen
import com.minhyuuk.dashnote.ui.theme.DashNoteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DashNoteTheme {
                MemoListScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    DashNoteTheme {
        MemoListScreen()
    }
}