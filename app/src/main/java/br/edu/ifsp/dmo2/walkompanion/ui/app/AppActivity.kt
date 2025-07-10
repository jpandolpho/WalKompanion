package br.edu.ifsp.dmo2.walkompanion.ui.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.dmo2.walkompanion.R
import br.edu.ifsp.dmo2.walkompanion.databinding.ActivityAppBinding
import br.edu.ifsp.dmo2.walkompanion.ui.app.fragment.HomeFragment
import br.edu.ifsp.dmo2.walkompanion.ui.app.fragment.ProfileFragment

class AppActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

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