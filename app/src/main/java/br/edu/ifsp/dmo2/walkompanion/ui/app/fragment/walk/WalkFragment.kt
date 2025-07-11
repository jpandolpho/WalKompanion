package br.edu.ifsp.dmo2.walkompanion.ui.app.fragment.walk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.edu.ifsp.dmo2.walkompanion.databinding.FragmentWalkBinding
import com.google.firebase.auth.FirebaseAuth

class WalkFragment : Fragment() {
    private lateinit var binding: FragmentWalkBinding
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWalkBinding.inflate(
            inflater, container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bundle: Bundle = requireArguments()
        val origin = bundle.getString("origin")
        if (origin == "home") {
            binding.txtDate.visibility = View.GONE
            binding.txtMaxh.visibility = View.GONE
            binding.txtMinh.visibility = View.GONE

            binding.txtSteps.text = "Passos dados: "
            binding.txtDuration.text = "HH:MM:SS"
            binding.txtDistance.text = "Distancia percorrida: "
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}