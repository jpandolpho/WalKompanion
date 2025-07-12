package br.edu.ifsp.dmo2.walkompanion.helper

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class AltimetroHelper(
    private val context: Context,
    private val callback: CallbackHeight
) : SensorEventListener {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
    private var pressaoBase: Float? = null

    fun start() {
        pressureSensor?.let {
            sensorManager.registerListener(this, it,
                SensorManager.SENSOR_DELAY_UI)
        }
    }

    fun finish() {
        sensorManager.unregisterListener(this)
    }

    fun zerarAltura() {
        pressaoBase = null
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null && event.sensor.type == Sensor.TYPE_PRESSURE) {
            val pressaoAtual = event.values[0]
            if (pressaoBase == null) {
                pressaoBase = pressaoAtual
                callback.onAlturaAtualizada(0f)
            } else {
                val alturaRelativa = SensorManager.getAltitude(pressaoBase!!,
                    pressaoAtual)
                callback.onAlturaAtualizada(alturaRelativa)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    interface CallbackHeight {
        fun onAlturaAtualizada(altura: Float)
    }
}