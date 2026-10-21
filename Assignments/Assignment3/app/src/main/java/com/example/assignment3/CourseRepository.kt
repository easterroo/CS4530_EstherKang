package com.example.assignment3

import com.example.assignment3.room.Course
import com.example.assignment3.room.CourseDao
import kotlinx.coroutines.flow.Flow

/**
 * Layer between the ViewModel and the Room database
 * Singleton: only one instance of the repository exists in the whole app
 */
class CourseRepository private constructor(private val dao: CourseDao) {
    val allCourses: Flow<List<Course>> = dao.getAllCourses()

    suspend fun addCourse(course: Course) = dao.addCourse(course)
    suspend fun updateCourse(course: Course) = dao.updateCourse(course)
    suspend fun deleteCourse(course: Course) = dao.deleteCourse(course)

    companion object {
        @Volatile private var INSTANCE: CourseRepository? = null

        fun getInstance(dao: CourseDao): CourseRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: CourseRepository(dao).also { INSTANCE = it }
            }
    }
}