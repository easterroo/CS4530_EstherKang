package com.example.assignment2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

/**
 * Displays the "Course Details" page where users can find information on a course
 *
 * @param myNavController used to navigate back to the course list page
 * @param myVM provides the add course functionality
 * @param id id of the course to display
 */
@Composable
fun CourseDetails(myNavController: NavHostController, myVM: CourseViewModel, id: Int) {
    val course = myVM.getCourse(id)

    Column(modifier = Modifier.fillMaxWidth()
            .statusBarsPadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Course Details",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        if (course != null) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Course Department:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(course.department)
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Course Number:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(course.courseNumber.toString())
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Course Location:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(course.location)
            }
        } else {
            Text("Course not found")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            if (course != null) {
                Button(
                    onClick = { myNavController.navigate("edit/${course.id}") },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Edit")
                }
                Button(
                    onClick = { myVM.deleteCourse(course)
                        myNavController.navigate("list") },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Delete")
                }
            }
            Button(onClick = {myNavController.navigate("list")}) {
                Text("Back to List")
            }

        }
    }
}