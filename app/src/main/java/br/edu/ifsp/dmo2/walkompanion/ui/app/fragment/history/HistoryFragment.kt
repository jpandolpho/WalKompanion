package br.edu.ifsp.dmo2.walkompanion.ui.app.fragment.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.dmo2.walkompanion.R
import br.edu.ifsp.dmo2.walkompanion.adapter.CaminhadaAdapter
import br.edu.ifsp.dmo2.walkompanion.databinding.FragmentHistoryBinding
import br.edu.ifsp.dmo2.walkompanion.listener.CaminhadaItemClickListener
import br.edu.ifsp.dmo2.walkompanion.model.Caminhada
import br.edu.ifsp.dmo2.walkompanion.ui.app.fragment.walk.WalkFragment
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class HistoryFragment : Fragment(), CaminhadaItemClickListener {
    private lateinit var binding: FragmentHistoryBinding
    private val firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var adapter: CaminhadaAdapter
    private var caminhadas: ArrayList<Caminhada>? = null
    private var lastTimestamp: Timestamp? = null

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
        adapter = CaminhadaAdapter(mutableListOf<Caminhada>().toTypedArray(), this)
        binding.caminhadaList.layoutManager = LinearLayoutManager(requireContext())
        binding.caminhadaList.adapter = adapter
        loadCaminhadas()
        binding.buttonMore.setOnClickListener {
            loadCaminhadas()
        }
    }

    private fun loadCaminhadas() {
        val db = Firebase.firestore
        var query = db.collection("caminhadas")
            .whereEqualTo("owner", firebaseAuth.currentUser!!.email.toString())
            .orderBy("inicio", Query.Direction.DESCENDING)
            .limit(5)
        if (lastTimestamp != null) {
            query = query.startAfter(lastTimestamp)
        }
        query.get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (!document.isEmpty) {
                        lastTimestamp = if (document.documents.size == 5)
                            document.documents.last().getTimestamp("inicio")
                        else
                            null
                    }
                    caminhadas = ArrayList()
                    for (document in document.documents) {
                        val timestamp = document.getTimestamp("inicio")
                        val steps = document.data!!["passos"].toString().toInt()
                        val duration: Duration =
                            document.data!!["duracao"].toString().toInt().seconds
                        val maxH = document.data!!["max_height"].toString().toFloat()
                        val minH = document.data!!["min_height"].toString().toFloat()
                        val distance = (steps * 0.76 / 1000).toFloat()
                        val caminhada =
                            Caminhada(timestamp!!, steps, maxH, minH, duration, distance)
                        caminhadas!!.add(caminhada)
                    }
                    adapter = CaminhadaAdapter(caminhadas!!.toTypedArray(), this)
                    binding.caminhadaList.layoutManager = LinearLayoutManager(requireContext())
                    binding.caminhadaList.adapter = adapter
                }else{
                    Log.v("KEKW",task.exception?.message.toString())
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun clickCaminhadaItem(position: Int) {
        val fragment = WalkFragment()
        val bundle = Bundle()
        bundle.putString("email",firebaseAuth.currentUser!!.email.toString())
        bundle.putString("timestamp", caminhadas!![position].getInicio().toString())
        fragment.arguments = bundle
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.container_fragment, fragment)
            .commit()
    }
}