package br.edu.ifsp.dmo2.walkompanion.ui.app.fragment.walk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import br.edu.ifsp.dmo2.walkompanion.databinding.FragmentWalkBinding
import br.edu.ifsp.dmo2.walkompanion.ui.app.AppViewModel

class WalkFragment : Fragment() {
    private lateinit var binding: FragmentWalkBinding
    private val viewModel: AppViewModel by activityViewModels()

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
        } else if (origin == "history") {
            setupReview()
        }

        binding.buttonFinish.setOnClickListener {
            viewModel.finishWalk()
            setupReview()
            binding.txtDate.visibility = View.VISIBLE
            binding.txtMaxh.visibility = View.VISIBLE
            binding.txtMinh.visibility = View.VISIBLE
        }
    }

    private fun setupReview() {
        binding.buttonFinish.visibility = View.GONE

        binding.txtSteps.text = "Passos dados: "
        binding.txtDuration.text = "HH:MM:SS"
        binding.txtDistance.text = "Distancia percorrida: "
        binding.txtDate.text = "data :)"
        binding.txtMaxh.text = "1000"
        binding.txtMinh.text = "-1000"
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}