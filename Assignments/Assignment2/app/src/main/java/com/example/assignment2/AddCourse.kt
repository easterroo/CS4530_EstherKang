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
 * Displays the "Add Course" page where users can input information to create a new course
 *
 * @param myNavController used to navigate back to the course list page
 * @param myVM provides the add course functionality
 */
@Composable
fun AddCourse(myNavController: NavHostController, myVM: CourseViewModel) {
    var department by remember { mutableStateOf("")}
    var courseNum by remember { mutableStateOf("")}
    var location by remember { mutableStateOf("")}

    Column(modifier = Modifier.fillMaxWidth()
        .statusBarsPadding()
        .padding(24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp)) {

        Text(text = "Add Course",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text("Course Department:")
            OutlinedTextField(
                value = department,
                onValueChange = { newText -> department = newText },
                label = {Text("Department")},
                modifier = Modifier.fillMaxWidth(),
                singleLine = true)
        }

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text("Course Number:")
            OutlinedTextField(
                value = courseNum,
                onValueChange = { newText -> courseNum = newText },
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
                onValueChange = { newText -> location = newText },
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
                myVM.addCourse(department.uppercase(), courseNum.toIntOrNull() ?: 1000, location)
                myNavController.navigate("list")},
                modifier = Modifier.weight(1f)) {
                Text("Save")
            }
            Button(onClick = {myNavController.navigate("list")},
                modifier = Modifier.weight(1f)) {
                Text("Back")
            }
        }
    }
}