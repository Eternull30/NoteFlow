package com.example.mynotesapp.feature_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mynotesapp.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.example.mynotesapp.feature_note.presentation.notes.NotesScreen
import com.example.mynotesapp.feature_note.presentation.util.Screen
import com.example.mynotesapp.ui.theme.MyNotesAppTheme
import dagger.hilt.android.AndroidEntryPoint
import com.google.firebase.auth.FirebaseAuth
import android.util.Log
import com.example.mynotesapp.feature_note.home_screen.HomeScreen
import com.example.mynotesapp.feature_note.login.LoginScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val auth = FirebaseAuth.getInstance()
        Log.d("FIREBASE_TEST", "Firebase connected: ${auth != null}")

        setContent {
            MyNotesAppTheme() {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val auth = FirebaseAuth.getInstance()
                    val startDestination = if (auth.currentUser == null) {
                        "login"
                    } else {
                        Screen.NotesScreen.route
                    }

                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {

                        composable("login") {
                            LoginScreen(navController = navController)
                        }

                        composable("home") {
                            HomeScreen(navController = navController)
                        }

                        composable(
                            route = Screen.AddEditNoteScreen.route + "?noteId={noteId}",
                            arguments = listOf(
                                navArgument(
                                    name = "noteId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            AddEditNoteScreen(navController = navController)
                        }
                    }

                }
            }
        }
    }
}
