package com.example.assignment2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

/**
 * Displays the "Edit Course" page where users can input information to update a course
 *
 * @param myNavController used to navigate back to the course list page
 * @param myVM provides the edit course functionality
 * @param id id of the course to update
 */
@Composable
fun EditCourse(myNavController: NavHostController, myVM: CourseViewModel, id: Int) {
    Column(Modifier.fillMaxWidth().statusBarsPadding(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center) {
        val course = myVM.getCourse(id)

        if (course == null) {
            Text("Course not found")
            return
        }
        var department by remember { mutableStateOf(course.department)}
        var courseNum by remember { mutableStateOf(course.courseNumber.toString())}
        var location by remember { mutableStateOf(course.location)}

        Column(modifier = Modifier.fillMaxWidth()
            .statusBarsPadding()
            .padding(24.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp)) {

            Text(text = "Edit Course",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Course Department:")
                OutlinedTextField(
                    value = department,
                    onValueChange = { department = it },
                    label = {Text("Department")},
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true)
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Course Number:")
                OutlinedTextField(
                    value = courseNum,
                    onValueChange = { courseNum = it },
                    label = {Text("Course Number")},
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Location:")
                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = {Text("Location")},
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(onClick = {
                    myVM.editCourse(
                        course.copy(
                            department = department.uppercase(),
                            courseNumber = courseNum.toIntOrNull() ?: 1000,
                            location = location
                        )
                    )
                    myNavController.navigate("list")},
                    modifier = Modifier.weight(1f)) {
                    Text("Save")
                }
                Button(onClick = {myNavController.navigate("list")},
                    modifier = Modifier.weight(1f),
                    enabled = department.isNotBlank() && courseNum.isNotBlank()) {
                    Text("Back")
                }
            }
        }
    }
}