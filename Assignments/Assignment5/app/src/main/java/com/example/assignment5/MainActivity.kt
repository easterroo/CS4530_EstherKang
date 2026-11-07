package com.example.assignment5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.assignment5.ui.theme.Assignment5Theme

/**
 * Hosts the marble screen and ties together the sensor lifecycle
 */
class MainActivity : ComponentActivity() {
    private val marbleViewModel: MarbleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment5Theme() {
                MarbleScreen(marbleViewModel)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        marbleViewModel.start()
    }

    override fun onPause() {
        super.onPause()
        marbleViewModel.stop()
    }
}

/**
 * Marble screen that holds the actual area with marble bouncing around
 */
@Composable
fun MarbleScreen(marbleViewModel: MarbleViewModel) {
    val state by marbleViewModel.state.collectAsStateWithLifecycle()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize().safeDrawingPadding()) {
            LaunchedEffect(maxWidth, maxHeight) {
                marbleViewModel.setBounds(maxWidth.value, maxHeight.value)
            }

            if (!state.sensorAvailable) {
                Text("No gravity sensor or accelerometer available")
            } else {
                Box(
                    modifier = Modifier
                        .offset(state.x.dp, state.y.dp)
                        .size(MARBLE_SIZE_DP.dp)
                        .background(Color.DarkGray, CircleShape),
                )
            }
        }
    }
}