package com.example.assignment5

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Marble position in dp.
 */
data class MarbleState(
    val x: Float = 0f,
    val y: Float = 0f,
    val sensorAvailable: Boolean = true
)

const val MARBLE_SIZE_DP = 60f
private const val ACCEL_SCALE = 120f
private const val FRICTION = 1.5f
private const val MAX_DT = 0.05f

/**
 * ViewModel that holds the information for the marble and sensors
 */
class MarbleViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = GravityRepository(app)

    private val _state = MutableStateFlow(MarbleState(sensorAvailable = repository.isAvailable))
    val state: StateFlow<MarbleState> = _state.asStateFlow()

    private var maxX = 0f
    private var maxY = 0f
    private var vx = 0f
    private var vy = 0f
    private var lastTimestampNs = 0L
    private var job: Job? = null

    /**
     * Called by the UI once it knows its size (in dp).
     */
    fun setBounds(widthDp: Float, heightDp: Float) {
        maxX = (widthDp - MARBLE_SIZE_DP).coerceAtLeast(0f)
        maxY = (heightDp - MARBLE_SIZE_DP).coerceAtLeast(0f)
        _state.update { it.copy(x = it.x.coerceIn(0f, maxX), y = it.y.coerceIn(0f, maxY)) }
    }

    /**
     * Called from Activity.onResume()
     */
    fun start() {
        if (job != null) return
        lastTimestampNs = 0L
        job = viewModelScope.launch {
            repository.readings().collect { onReading(it) }
        }
    }

    /**
     * Called from Activity.onPause()
     */
    fun stop() {
        job?.cancel()
        job = null
        vx = 0f
        vy = 0f
    }

    private fun onReading(reading: GravityReading) {
        if (lastTimestampNs == 0L) {
            lastTimestampNs = reading.timestampNs
            return
        }
        val dt = ((reading.timestampNs - lastTimestampNs) / 1e9f).coerceIn(0f, MAX_DT)
        lastTimestampNs = reading.timestampNs

        // Sensor x points right, y points up the screen, offset y points down
        vx += dt * ACCEL_SCALE * -reading.x
        vy += dt * ACCEL_SCALE * reading.y

        // Basic friction
        val damping = (1f - FRICTION * dt).coerceAtLeast(0f)
        vx *= damping
        vy *= damping

        var nx = _state.value.x + dt * vx
        var ny = _state.value.y + dt * vy

        // Keeps the marble within the screen
        if (nx < 0f) { nx = 0f; vx = 0f } else if (nx > maxX) { nx = maxX; vx = 0f }
        if (ny < 0f) { ny = 0f; vy = 0f } else if (ny > maxY) { ny = maxY; vy = 0f }

        _state.update { it.copy(x = nx, y = ny) }
    }
}