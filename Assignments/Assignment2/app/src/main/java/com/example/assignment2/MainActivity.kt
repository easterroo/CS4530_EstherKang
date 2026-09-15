package com.example.assignment2

import android.graphics.ColorSpace
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.example.assignment2.ui.theme.Assignment2Theme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.collections.plus
import androidx.lifecycle.compose.collectAsStateWithLifecycle

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

    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment2Theme {
                val myVMObj: CourseViewModel = viewModel()
                // CourseList (myVMObj)
                }
            }
        }
    }

@Composable
fun CourseList(myVM: CourseViewModel) {

}
/// Add Course
/// Edit Course
/// Delete Course
/// Course List Display (main page)