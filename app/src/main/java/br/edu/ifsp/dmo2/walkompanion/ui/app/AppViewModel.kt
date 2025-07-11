package br.edu.ifsp.dmo2.walkompanion.ui.app

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class AppViewModel(application: Application) : AndroidViewModel(application) {
    private var activeWalk = false

    private val _isWalking = MutableLiveData<Boolean>()
    val isWalking: LiveData<Boolean> = _isWalking

    private val _nome = MutableLiveData<String>()
    val nome: LiveData<String> = _nome

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
}