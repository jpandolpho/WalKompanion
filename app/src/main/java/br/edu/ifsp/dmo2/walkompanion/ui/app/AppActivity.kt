package br.edu.ifsp.dmo2.walkompanion.ui.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.edu.ifsp.dmo2.walkompanion.R
import br.edu.ifsp.dmo2.walkompanion.databinding.ActivityAppBinding
import br.edu.ifsp.dmo2.walkompanion.ui.app.fragment.history.HistoryFragment
import br.edu.ifsp.dmo2.walkompanion.ui.app.fragment.home.HomeFragment
import br.edu.ifsp.dmo2.walkompanion.ui.app.fragment.profile.ProfileFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class AppActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAppBinding
    private lateinit var viewModel: AppViewModel
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(AppViewModel::class.java)

        val email = firebaseAuth.currentUser!!.email.toString()
        val db = Firebase.firestore
        db.collection("users").document(email).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    val name = document.data!!["nome"].toString()
                    viewModel.storeName(name)
                }
            }

        supportFragmentManager.beginTransaction()
            .replace(R.id.container_fragment, HomeFragment())
            .commit()

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container_fragment, ProfileFragment())
                        .commit()
                    true
                }

                R.id.nav_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container_fragment, HomeFragment())
                        .commit()
                    true
                }

                R.id.nav_history -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container_fragment, HistoryFragment())
                        .commit()
                    true
                }

                else -> false
            }
        }
    }
}