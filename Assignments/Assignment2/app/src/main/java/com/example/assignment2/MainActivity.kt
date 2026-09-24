package com.example.assignment2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.collections.plus
import androidx.navigation.compose.rememberNavController
import com.example.compose.Assignment2Theme

/**
 * Represents a course object
 */
data class Course(
    val id: Int,
    val department: String,
    val courseNumber: Int,
    val location: String
) {
    /** Display name used in the course list page, combining department and course number */
    val name: String get() = "$department$courseNumber"
}

/**
 * ViewModel that holds the app course data
 */
class CourseViewModel : ViewModel()
{
    private val courses = MutableStateFlow(listOf<Course>())
    private var nextId = 1
    val coursesReadOnly : StateFlow<List<Course>> = courses

    /** Creates a new course with given information and auto-incrementing id */
    fun addCourse (department: String, courseNumber: Int, location: String){
        val newCourse = Course(
            id = nextId,
            department = department,
            courseNumber = courseNumber,
            location = location
        )
        courses.value += newCourse
        nextId++
    }

    /** Returns course that matches given id */
    fun getCourse(id: Int): Course? {
        return courses.value.find { it.id == id }
    }

    /** Updates course with new, changed information */
    fun editCourse(course: Course) {
        courses.value = courses.value.map {
            if (it.id == course.id) course else it
        }
    }

    /** Deletes course from course list */
    fun deleteCourse(course: Course) {
        courses.value -= course
    }
}

/**
 * One activity that hosts all the UI and novigation
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment2Theme(dynamicColor = false) {
                val myVMObj: CourseViewModel = viewModel()
                val navController = rememberNavController()
                MyAppNav (navController, myVMObj, "list")
                }
            }
        }
    }