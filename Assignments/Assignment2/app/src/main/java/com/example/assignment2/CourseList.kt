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
fun CourseList(myVM: CourseViewModel) {
    Column(Modifier.fillMaxWidth().statusBarsPadding(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center) {
        val observableCourses by myVM.coursesReadOnly.collectAsStateWithLifecycle()

        var courseText by remember {mutableStateOf("")}
        // list of courses, clicking each sends you to their
        // details page
        // delete on each row, add delete confirmation
        // add course button at the top
    }
}