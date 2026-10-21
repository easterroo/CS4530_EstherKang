package com.example.assignment3.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.assignment3.room.Course
import kotlinx.coroutines.flow.Flow

/**
 * DAO interface that is able to retrieve information and execute
 * queries in the database
 */
@Dao
interface CourseDao {
    @Query("SELECT * FROM courses")
    fun getAllCourses(): Flow<List<Course>>

    @Insert
    suspend fun addCourse(course: Course)

    @Update
    suspend fun updateCourse(course: Course)

    @Delete
    suspend fun deleteCourse(course: Course)
}