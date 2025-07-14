package br.edu.ifsp.dmo2.walkompanion.ui.app

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.edu.ifsp.dmo2.walkompanion.model.Caminhada
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class AppViewModel(application: Application) : AndroidViewModel(application) {
    private var activeWalk = false
    private val firebaseAuth = FirebaseAuth.getInstance()
    private var timestampInicio: Timestamp? = null
    private var lastTimestamp: Timestamp? = null
    private val db = Firebase.firestore
    private var position: Int = -1
    var startingTime: Long = 0
        private set
    var steps: Int = -1
        private set
    var maxH: Float? = null
        private set
    var minH: Float? = null
        private set
    var elapsedTime: Long = 0
        private set

    private val _isWalking = MutableLiveData<Boolean>()
    val isWalking: LiveData<Boolean> = _isWalking

    private val _nome = MutableLiveData<String>()
    val nome: LiveData<String> = _nome

    private val _caminhadas = MutableLiveData<ArrayList<Caminhada>>()
    val caminhadas: LiveData<ArrayList<Caminhada>> = _caminhadas

    private val _caminhada = MutableLiveData<Caminhada>()
    val caminhada: LiveData<Caminhada> = _caminhada

    private val _ligarSensores = MutableLiveData<Boolean>()
    val ligarSensores: LiveData<Boolean> = _ligarSensores

    private val _atualizarPassos = MutableLiveData<Boolean>()
    val atualizarPassos: LiveData<Boolean> = _atualizarPassos

    private val _finalizarCaminhada = MutableLiveData<Caminhada>()
    val finalizarCaminhada: LiveData<Caminhada> = _finalizarCaminhada

    fun isWalking() {
        _isWalking.value = activeWalk
    }

    fun startWalk() {
        activeWalk = true
        steps = -1
        timestampInicio = Timestamp.now()
        _ligarSensores.value = true
    }

    fun finishWalk() {
        activeWalk = false
        val duration = (elapsedTime - startingTime).toDuration(DurationUnit.MILLISECONDS)
        val email = firebaseAuth.currentUser!!.email.toString()
        val steps = this.steps
        val maxH: Float = if (this.maxH == null) 0F else this.maxH!!
        val minH: Float = if (this.minH == null) 0f else this.minH!!
        val data = timestampInicio!!
        val dados = hashMapOf(
            "duracao" to duration.inWholeSeconds,
            "inicio" to data,
            "max_height" to maxH,
            "min_height" to minH,
            "owner" to email,
            "passos" to steps
        )
        db.collection("caminhadas").document()
            .set(dados)
            .addOnSuccessListener {
                _finalizarCaminhada.value =
                    Caminhada(data, steps, maxH, minH, duration, (steps * 0.76f))
            }
    }

    fun storeName(name: String) {
        _nome.value = name
    }

    fun loadCaminhadas() {
        var query = db.collection("caminhadas")
            .whereEqualTo("owner", firebaseAuth.currentUser!!.email.toString())
            .orderBy("inicio", Query.Direction.DESCENDING)
            .limit(5)
        if (lastTimestamp != null) {
            query = query.startAfter(lastTimestamp)
        }
        query.get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (!document.isEmpty) {
                        lastTimestamp = if (document.documents.size == 5)
                            document.documents.last().getTimestamp("inicio")
                        else
                            null
                    }
                    val lista = ArrayList<Caminhada>()
                    for (document in document.documents) {
                        val timestamp = document.getTimestamp("inicio")
                        val steps = document.data!!["passos"].toString().toInt()
                        val duration: Duration =
                            document.data!!["duracao"].toString().toInt().seconds
                        val maxH = document.data!!["max_height"].toString().toFloat()
                        val minH = document.data!!["min_height"].toString().toFloat()
                        val distance = (steps * 0.76).toFloat()
                        val caminhada =
                            Caminhada(timestamp!!, steps, maxH, minH, duration, distance)
                        lista.add(caminhada)
                    }
                    _caminhadas.value = lista
                } else {
                    Log.v("WALKOMPANION", task.exception?.message.toString())
                }
            }
    }

    fun clickCaminhada(position: Int) {
        this.position = position
    }

    fun showData() {
        _caminhada.value = _caminhadas.value!![position]
    }

    fun resetList() {
        lastTimestamp = null
    }

    fun addStep() {
        steps += 1
    }

    fun updateHeights(altura: Float) {
        if (maxH == null && minH == null) {
            maxH = altura
            minH = altura
        } else {
            if (maxH!! < altura) maxH = altura
            if (minH!! > altura) minH = altura
        }
    }

    fun saveTime(base: Long) {
        elapsedTime = base
    }

    fun updateStepView() {
        _atualizarPassos.value = true
    }

    fun startTime(base: Long) {
        startingTime = base
    }
}