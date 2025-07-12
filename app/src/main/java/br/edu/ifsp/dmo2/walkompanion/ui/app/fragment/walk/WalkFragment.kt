package br.edu.ifsp.dmo2.walkompanion.ui.app.fragment.walk

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import br.edu.ifsp.dmo2.walkompanion.databinding.FragmentWalkBinding
import br.edu.ifsp.dmo2.walkompanion.ui.app.AppViewModel

class WalkFragment : Fragment() {
    private lateinit var binding: FragmentWalkBinding
    private val viewModel: AppViewModel by activityViewModels()
    private lateinit var bundle: Bundle
    private lateinit var cronometro : Chronometer

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
        bundle = requireArguments()
        cronometro = binding.chronometer
        setupObserver()
        val origin = bundle.getString("origin")
        if (origin == "home") {
            if(bundle.getBoolean("started"))
                cronometro.base = viewModel.elapsedTime
            else
                cronometro.base = SystemClock.elapsedRealtime()
            cronometro.start()
            binding.txtDate.visibility = View.GONE
            binding.txtMaxh.visibility = View.GONE
            binding.txtMinh.visibility = View.GONE
            binding.txtDuration.visibility = View.GONE

            binding.txtSteps.text = "Passos dados: ${viewModel.steps}"
            binding.txtDistance.text = "Distancia percorrida: 0.00m"
        } else if (origin == "history") {
            setupReview()
        }

        binding.buttonFinish.setOnClickListener {
            cronometro.stop()
            viewModel.saveTime(cronometro.base)
            viewModel.finishWalk()
            setupReview()
            binding.txtDate.visibility = View.VISIBLE
            binding.txtMaxh.visibility = View.VISIBLE
            binding.txtMinh.visibility = View.VISIBLE
        }

        binding.buttonTest.setOnClickListener {
            viewModel.addStep()
            viewModel.updateStepView()
        }
    }

    private fun setupObserver() {
        val origin = bundle.getString("origin")
        if(origin == "home"){
            viewModel.atualizarPassos.observe(viewLifecycleOwner, {
                binding.txtSteps.text = "Passos dados: ${viewModel.steps}"
                val caminhada = viewModel.steps * 0.76
                val distanceStr =
                    if (caminhada < 1000)
                        "${"%.2f".format(caminhada)}m"
                    else "${"%.2f".format((caminhada/1000))}km"
                binding.txtDistance.text = "Distancia percorrida: ${distanceStr}"
            })
        }
        if(origin == "history") {
            viewModel.caminhada.observe(viewLifecycleOwner, {
                val caminhada = it
                val date = caminhada.getInicio().toDate()
                val day = if (date.date < 10) "0${date.date}" else "${date.date}"
                val month = if ((date.month + 1) < 10) "0${date.month + 1}" else "${date.month + 1}"
                val minutes = if (date.minutes < 10) "0${date.minutes}" else "${date.minutes}"
                val dateStr =
                    "${day}/${month}/${(date.year + 1900)} - ${(date.hours - 3)}:${minutes}"
                binding.txtDate.text = dateStr

                val duration = caminhada.getDuration()
                val durationStr = duration.inWholeMinutes.toString()
                binding.txtDuration.text = "Duração: ${durationStr}"

                binding.txtSteps.text = "Passos dados: ${caminhada.getSteps()}"

                val distanceStr =
                    if (caminhada.getAproxDistance() < 1000)
                        "${"%.2f".format(caminhada.getAproxDistance())}m"
                    else "${"%.2f".format(caminhada.getAproxDistance() / 1000)}km"
                binding.txtDistance.text = "Distância percorrida: ${distanceStr}"

                binding.txtMaxh.text =
                    "Altitude máxima: ${"%.2f".format(caminhada.getMaxHeight())}m"
                binding.txtMinh.text =
                    "Altitude mínima: ${"%.2f".format(caminhada.getMinHeight())}m"
            })
        }
    }

    private fun setupReview() {
        binding.buttonFinish.visibility = View.GONE
        binding.chronometer.visibility = View.GONE
        viewModel.showData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.saveTime(cronometro.base)
    }
}