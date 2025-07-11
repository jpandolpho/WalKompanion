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

class AppViewModel(application: Application) : AndroidViewModel(application) {
    private var activeWalk = false
    private val firebaseAuth = FirebaseAuth.getInstance()
    private var lastTimestamp: Timestamp? = null
    private val db = Firebase.firestore

    private val _isWalking = MutableLiveData<Boolean>()
    val isWalking: LiveData<Boolean> = _isWalking

    private val _nome = MutableLiveData<String>()
    val nome: LiveData<String> = _nome

    private val _caminhadas = MutableLiveData<ArrayList<Caminhada>>()
    val caminhadas : LiveData<ArrayList<Caminhada>> = _caminhadas

    private val _caminhada = MutableLiveData<Caminhada>()
    val caminhada : LiveData<Caminhada> = _caminhada

    fun isWalking(){
        _isWalking.value = activeWalk
    }

    fun startWalk(){
        activeWalk = true
    }

    fun finishWalk(){
        activeWalk = false
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
                        val distance = (steps * 0.76 / 1000).toFloat()
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
        _caminhada.value = _caminhadas.value!![position]
    }

    fun showData() {
        _caminhada.value = _caminhada.value
    }
}