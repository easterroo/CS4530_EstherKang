package com.example.assignment5

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

data class GravityReading(val x: Float, val y: Float, val timestampNs: Long)

private const val ALPHA = 0.8f;

/**
 * Wraps the sensor framework and the tilt readings
 */
class GravityRepository(context: Context) {
    private val sensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    // Gravity sensor if present, otherwise accelerometer
    private val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
        ?: sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    private val needsFilter = sensor?.type == Sensor.TYPE_ACCELEROMETER

    val isAvailable: Boolean get() = sensor != null

    /**
     * Registers on collect, unregisters when the collector is canceled
     */
    fun readings(): Flow<GravityReading> = callbackFlow {
        val s = sensor
        if (s == null) {
            close()
            return@callbackFlow
        }

        val gravity = FloatArray(3)
        var initialized = false

        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                if (needsFilter) {
                    if (!initialized) {
                        event.values.copyInto(gravity, endIndex = 3)
                        initialized = true
                    } else {
                        for (i in 0..2) {
                            gravity[i] = ALPHA * gravity[i] + (1 - ALPHA) * event.values[i]
                        }
                    }
                } else {
                    event.values.copyInto(gravity, endIndex = 3)
                }
                trySend(GravityReading(gravity[0], gravity[1], event.timestamp))
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
        }

        sensorManager.registerListener(listener, s, SensorManager.SENSOR_DELAY_GAME)
        awaitClose { sensorManager.unregisterListener(listener) }
    }
}
