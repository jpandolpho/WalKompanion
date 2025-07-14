package br.edu.ifsp.dmo2.walkompanion.helper

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class StepHelper(
    private val context: Context,
    private val callback: CallbackStep
) : SensorEventListener {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

    fun start() {
        sensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    fun finish() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            callback.onStepDetectado()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, p1: Int) {}

    interface CallbackStep {
        fun onStepDetectado()
    }
}