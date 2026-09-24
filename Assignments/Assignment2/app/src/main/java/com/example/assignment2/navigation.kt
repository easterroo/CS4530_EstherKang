package com.example.assignment2

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

/**
 * Defines the app's navigation and routing between pages
 *
 * @param myNavController controller used by screens to navigate
 * @param myVM shared ViewModel passed to every screen with the same shared data
 * @param startDestination starting destination when app launches
 */
@Composable
fun MyAppNav (myNavController: NavHostController, myVM: CourseViewModel, startDestination: String) {
    NavHost(myNavController, startDestination) {
        composable ("list") { CourseList(myNavController, myVM) }
        composable ("add") { AddCourse(myNavController, myVM) }
        composable ("details/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: return@composable
            CourseDetails(myNavController, myVM, id) }
        composable ("edit/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: return@composable
            EditCourse(myNavController, myVM, id) }
    }
}