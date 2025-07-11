package br.edu.ifsp.dmo2.walkompanion.ui.app.fragment.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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
                    binding.txtName.text = name
                }
            }

        binding.buttonReset.setOnClickListener {
            if (checkValues()) {
                val senha = binding.textSenha.text.toString()
                firebaseAuth.currentUser!!.updatePassword(senha)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                activity,
                                "Senha alterada com sucesso!",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(activity, task.exception?.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
            }
        }

        binding.buttonLogout.setOnClickListener {
            firebaseAuth.signOut()
            requireActivity().finish()
        }
    }

    private fun checkValues(): Boolean {
        return !binding.textSenha.text.isNullOrBlank()
                && !binding.textConfirm.text.isNullOrBlank()
                && (binding.textSenha.text.toString() == binding.textConfirm.text.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}