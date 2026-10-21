package com.example.assignment3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.StateFlow
import androidx.navigation.compose.rememberNavController
import com.example.assignment3.room.Course
import com.example.assignment3.room.CourseDatabase
import com.example.compose.Assignment3Theme
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel that holds the app course data
 */
class CourseViewModel(private val repository: CourseRepository) : ViewModel()
{
    val coursesReadOnly : StateFlow<List<Course>> = repository.allCourses
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    /** Creates a new course with given information and auto-incrementing id */
    fun addCourse (department: String, courseNumber: Int, location: String){
        viewModelScope.launch {
            repository.addCourse(
                Course(department = department, courseNumber = courseNumber, location = location)
            )
        }
    }

    /** Returns course that matches given id */
    fun getCourse(id: Int): Course? = coursesReadOnly.value.find { it.id == id }

    /** Updates course with new, changed information */
    fun editCourse(course: Course) {
        viewModelScope.launch { repository.updateCourse(course) }
    }

    /** Deletes course from course list */
    fun deleteCourse(course: Course) {
        viewModelScope.launch { repository.deleteCourse(course) }
    }
}

/**
 * ViewModelFactory that makes sure the single repository instance is used
 */
class CourseViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val app = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
        val dao = CourseDatabase.getDatabase(app).courseDao()
        val repository = CourseRepository.getInstance(dao)

        if (modelClass.isAssignableFrom(CourseViewModel::class.java)) {
            return CourseViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
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
            Assignment3Theme(dynamicColor = false) {
                val myVMObj: CourseViewModel = viewModel(factory = CourseViewModelFactory())
                val navController = rememberNavController()
                MyAppNav (navController, myVMObj, "list")
                }
            }
        }
    }