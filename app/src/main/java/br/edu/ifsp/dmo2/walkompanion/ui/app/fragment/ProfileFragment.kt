package br.edu.ifsp.dmo2.walkompanion.ui.app.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import br.edu.ifsp.dmo2.walkompanion.R
import br.edu.ifsp.dmo2.walkompanion.databinding.FragmentProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(
            inflater, container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val email = firebaseAuth.currentUser!!.email.toString()
        val db = Firebase.firestore
        db.collection("users").document(email).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    val name = document.data!!["nome"].toString()
                    val txt = "${binding.txtWelcome.text}${name}!"
                    binding.txtWelcome.text = txt
                }
            }

        binding.buttonWalk.setOnClickListener {
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.container_fragment, WalkFragment())
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}