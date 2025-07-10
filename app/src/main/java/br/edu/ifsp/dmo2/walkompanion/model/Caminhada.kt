package br.edu.ifsp.dmo2.walkompanion.model

import com.google.firebase.Timestamp
import kotlin.time.Duration

class Caminhada (
    private val inicio : Timestamp,
    private val steps : Int,
    private val maxHeight : Float,
    private val minHeigt : Float,
    private val duration : Duration,
    private val aproxDistance: Float) {

    public fun getInicio() : Timestamp{
        return inicio
    }

    public fun getSteps() : Int{
        return steps
    }

    public fun getMaxHeight() : Float{
        return maxHeight
    }

    public fun getMinHeight() : Float{
        return minHeigt
    }

    public fun getDuration() : Duration{
        return duration
    }

    public fun getAproxDistance() : Float{
        return aproxDistance
    }
}