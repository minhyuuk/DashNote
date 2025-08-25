package com.minhyuuk.dashnote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.minhyuuk.dashnote.ui.screen.edit.MemoEditScreen
import com.minhyuuk.dashnote.ui.screen.edit.viewmodel.MemoEditViewModel
import com.minhyuuk.dashnote.ui.screen.main.MemoListScreen
import com.minhyuuk.dashnote.ui.theme.DashNoteTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DashNoteTheme {
                DashNoteApp()
            }
        }
    }
}

@Composable
fun DashNoteApp() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = "memo_list",
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
    ) {
        composable("memo_list") {
            MemoListScreen(
                onCreateMemoClick = {
                    navController.navigate("memo_create")
                }
            )
        }
        composable("memo_create") {
            MemoEditScreen(
                onBackClick = { navController.popBackStack() },
                viewModel = hiltViewModel<MemoEditViewModel>()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    DashNoteTheme {
        DashNoteApp()
    }
}