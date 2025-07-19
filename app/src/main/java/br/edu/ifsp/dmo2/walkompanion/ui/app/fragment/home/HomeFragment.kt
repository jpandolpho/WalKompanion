package br.edu.ifsp.dmo2.walkompanion.ui.app.fragment.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
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
    private val permissionRequestCode = 1001

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

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupObserver()
        viewModel.isWalking()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun moveToWalkFragment(bundle: Bundle) {
        if ((ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACTIVITY_RECOGNITION
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACTIVITY_RECOGNITION,
                    Manifest.permission.FOREGROUND_SERVICE
                ), permissionRequestCode
            )
        } else {
            val fragment = WalkFragment()
            fragment.arguments = bundle
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.container_fragment, fragment)
                .commit()
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun setupObserver() {
        viewModel.isWalking.observe(viewLifecycleOwner, {
            val bundle = Bundle()
            bundle.putString("origin", "home")
            if (it) {
                binding.buttonWalk.text = "Voltar para caminhada"
                binding.buttonWalk.setOnClickListener {
                    bundle.putBoolean("started", true)
                    moveToWalkFragment(bundle)
                }
            } else {
                binding.buttonWalk.text = "Caminhar"
                binding.buttonWalk.setOnClickListener {
                    bundle.putBoolean("started", false)
                    moveToWalkFragment(bundle)
                    viewModel.startWalk()
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