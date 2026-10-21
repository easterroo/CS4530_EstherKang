package com.example.assignment3.room

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a course as a Room entity
 */
@Entity(tableName = "courses")
data class Course(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val department: String,
    val courseNumber: Int,
    val location: String
) {
    val name: String get() = "$department$courseNumber"
}