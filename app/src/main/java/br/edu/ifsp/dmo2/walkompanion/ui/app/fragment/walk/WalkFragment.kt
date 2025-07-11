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
        setupObserver()
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

    private fun setupObserver() {
        viewModel.caminhada.observe(viewLifecycleOwner,{
            val caminhada = it
            val date = caminhada.getInicio().toDate()
            val day = if (date.date < 10) "0${date.date}" else "${date.date}"
            val month = if ((date.month + 1) < 10) "0${date.month + 1}" else "${date.month + 1}"
            val minutes = if (date.minutes < 10) "0${date.minutes}" else "${date.minutes}"
            val dateStr = "${day}/${month}/${(date.year + 1900)} - ${(date.hours - 3)}:${minutes}"
            binding.txtDate.text = dateStr

            val duration = caminhada.getDuration()
            val durationStr = duration.inWholeMinutes.toString()
            binding.txtDuration.text = "Duração: ${durationStr}"

            binding.txtSteps.text = "Passos dados: ${caminhada.getSteps()}"

            val distanceStr =
                if (caminhada.getAproxDistance() < 1000)
                    "${"%.2f".format(caminhada.getAproxDistance())}m"
                else "${"%.2f".format(caminhada.getAproxDistance()/1000)}km"
            binding.txtDistance.text = "Distância percorrida: ${distanceStr}"

            //PRECISA TERMINAR ISSO AQUI!!!!!!!!!!!!!!!!!!!!!!!
            binding.txtMaxh.text = "1000"
            binding.txtMinh.text = "-1000"
        })
    }

    private fun setupReview() {
        binding.buttonFinish.visibility = View.GONE
        viewModel.showData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}