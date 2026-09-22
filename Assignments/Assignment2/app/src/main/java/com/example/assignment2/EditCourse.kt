package com.example.assignment2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun EditCourse(myVM: CourseViewModel) {
    Column(Modifier.fillMaxWidth().statusBarsPadding(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center) {
        val observableCourses by myVM.coursesReadOnly.collectAsStateWithLifecycle()

        var courseText by remember {mutableStateOf("")}
    }
    // text: department
    // text string field
    // text: course number
    // int: number field
    // text: location
    // text: string field
    // save course button, back to course list, have previous info
    // in text fields
}