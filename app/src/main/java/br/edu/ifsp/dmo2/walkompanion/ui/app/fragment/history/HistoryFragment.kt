package br.edu.ifsp.dmo2.walkompanion.ui.app.fragment.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.dmo2.walkompanion.R
import br.edu.ifsp.dmo2.walkompanion.adapter.CaminhadaAdapter
import br.edu.ifsp.dmo2.walkompanion.databinding.FragmentHistoryBinding
import br.edu.ifsp.dmo2.walkompanion.listener.CaminhadaItemClickListener
import br.edu.ifsp.dmo2.walkompanion.model.Caminhada
import br.edu.ifsp.dmo2.walkompanion.ui.app.AppViewModel
import br.edu.ifsp.dmo2.walkompanion.ui.app.fragment.walk.WalkFragment

class HistoryFragment : Fragment(), CaminhadaItemClickListener {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var adapter: CaminhadaAdapter
    private val viewModel: AppViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(
            inflater, container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupAdapter(mutableListOf<Caminhada>().toTypedArray())
        setupObserver()
        viewModel.resetList()
        viewModel.loadCaminhadas()
        binding.buttonMore.setOnClickListener {
            viewModel.loadCaminhadas()
        }
    }

    private fun setupObserver() {
        viewModel.caminhadas.observe(viewLifecycleOwner, {
            setupAdapter(it.toTypedArray())
        })
    }

    private fun setupAdapter(entryArray: Array<Caminhada>) {
        adapter = CaminhadaAdapter(entryArray, this)
        binding.caminhadaList.layoutManager = LinearLayoutManager(requireContext())
        binding.caminhadaList.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun clickCaminhadaItem(position: Int) {
        viewModel.clickCaminhada(position)
        val fragment = WalkFragment()
        val bundle = Bundle()
        bundle.putString("origin", "history")
        fragment.arguments = bundle
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.container_fragment, fragment)
            .commit()
    }
}