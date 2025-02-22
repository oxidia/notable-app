package com.example.notable.feature_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notable.feature_note.common.Routes
import com.example.notable.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.example.notable.feature_note.presentation.notes.NotesScreen
import com.example.notable.ui.theme.NotableTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotableTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Routes.NOTES_LIST,
                ) {
                    composable(Routes.NOTES_LIST) {
                        NotesScreen(
                            navController = navController
                        )
                    }

                    composable(
                        route = "${Routes.ADD_EDIT_NOTE}?noteId={noteId}",
                        arguments = listOf(
                            navArgument(
                                name = "noteId"
                            ) {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ) {
                        AddEditNoteScreen(
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}