package br.edu.ifsp.dmo2.walkompanion.ui.app.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import br.edu.ifsp.dmo2.walkompanion.R
import br.edu.ifsp.dmo2.walkompanion.databinding.FragmentHomeBinding
import br.edu.ifsp.dmo2.walkompanion.ui.app.AppViewModel
import br.edu.ifsp.dmo2.walkompanion.ui.app.fragment.walk.WalkFragment

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: AppViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(
            inflater, container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupObserver()
        viewModel.isWalking()
    }

    private fun moveToWalkFragment() {
        val fragment = WalkFragment()
        val bundle = Bundle()
        bundle.putString("origin", "home")
        fragment.arguments = bundle
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.container_fragment, fragment)
            .commit()
    }

    private fun setupObserver() {
        viewModel.isWalking.observe(viewLifecycleOwner, {
            if (it) {
                binding.buttonWalk.text = "Voltar para caminhada"
                binding.buttonWalk.setOnClickListener {
                    moveToWalkFragment()
                }
            } else {
                binding.buttonWalk.text = "Caminhar"
                binding.buttonWalk.setOnClickListener {
                    viewModel.startWalk()
                    moveToWalkFragment()
                }
            }
        })

        viewModel.nome.observe(viewLifecycleOwner, {
            binding.txtWelcome.text = "${binding.txtWelcome.text} ${it}!"
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}