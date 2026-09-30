package com.example.assignment2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

/**
 * Displays every course in a scrollable LazyColumn with access to each course's details page
 *
 * @param myNavController used to navigate between pages
 * @param myVM source of course data
 */
@Composable
fun CourseList(myNavController : NavHostController, myVM: CourseViewModel) {
    val observableCourses by myVM.coursesReadOnly.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxWidth()
        .statusBarsPadding()
        .padding(24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center) {

        Text(text = "Course List",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Button(onClick = { myNavController.navigate("add")},
            modifier = Modifier.fillMaxWidth()) {
            Text("Add Course")
        }

        LazyColumn(modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(observableCourses, key = {it.id}) {
                course ->
                val courseName = course.name
                val courseId = course.id
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = courseName,
                        fontSize = 16.sp)
                    Button(onClick = { myNavController.navigate("details/$courseId") }) {
                        Text(">")
                    }
                }
                HorizontalDivider(color = Color.LightGray)
            }
        }
    }
}