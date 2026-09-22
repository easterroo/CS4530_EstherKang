package com.example.assignment2

import android.graphics.ColorSpace
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.assignment2.ui.theme.Assignment2Theme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.collections.plus
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

data class Course(
    val id: Int,
    val name: String,
    val department: String,
    val courseNumber: Int,
    val location: String
)

class CourseViewModel : ViewModel()
{
    private val courses = MutableStateFlow(listOf<Course>())
    val coursesReadOnly : StateFlow<List<Course>> = courses

    fun addCourse (course: Course){
        courses.value += course
    }

    fun editCourse(course: Course) {

    }

    fun deleteCourse(course: Course) {
        courses.value -= course
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment2Theme {
                val myVMObj: CourseViewModel = viewModel()
                val navController = rememberNavController()
                MyAppNav (myVMObj)
                }
            }
        }
    }

@Composable
fun MyAppNav(myNavController: NavHostController, myVM: CourseViewModel)
{
    NavHost(navController = myNavController, startDestination = "list") {
        composable("list") {
            CourseList(
                myVM = myVM,
                onAddClick = { myNavController.navigate("add")}
            )
        }
    }
}
/// Add Course
/// Edit Course
/// Delete Course
/// Course List Display (main page)