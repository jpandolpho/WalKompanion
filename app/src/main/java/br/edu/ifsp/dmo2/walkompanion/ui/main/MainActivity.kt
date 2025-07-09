package br.edu.ifsp.dmo2.walkompanion.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.dmo2.walkompanion.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListener()
        checkUser()
    }

    private fun checkUser() {
        if(firebaseAuth.currentUser!=null){
            launchApp()
        }
    }

    private fun setupListener() {
        binding.buttonLogin.setOnClickListener {
            val email = binding.textEmail.text.toString()
            val senha = binding.textSenha.text.toString()
            if(email.isNotBlank() && senha.isNotBlank()) {
                firebaseAuth
                    .signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            launchApp()
                        } else {
                            Toast.makeText(this, "Erro no Login", Toast.LENGTH_LONG).show()
                        }
                    }
            }else{
                Toast.makeText(this, "Erro no Login", Toast.LENGTH_LONG).show()
            }
        }
        binding.buttonSignup.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }
    }

    private fun launchApp(){
        startActivity(Intent(this, AppActivity::class.java))
        finish()
    }
}