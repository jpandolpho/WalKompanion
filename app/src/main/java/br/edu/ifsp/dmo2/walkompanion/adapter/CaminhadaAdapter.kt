package br.edu.ifsp.dmo2.walkompanion.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.dmo2.walkompanion.R
import br.edu.ifsp.dmo2.walkompanion.databinding.CaminhadaItemBinding
import br.edu.ifsp.dmo2.walkompanion.listener.CaminhadaItemClickListener
import br.edu.ifsp.dmo2.walkompanion.model.Caminhada

class CaminhadaAdapter(private val caminhadas: Array<Caminhada>, private val listener: CaminhadaItemClickListener) :
    RecyclerView.Adapter<CaminhadaAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: CaminhadaItemBinding = CaminhadaItemBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.caminhada_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return caminhadas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.textDate.text = caminhadas[position].getInicio().toString()
        holder.binding.textTime.text = caminhadas[position].getDuration().toString()
        holder.binding.textSteps.text = caminhadas[position].getSteps().toString()
        holder.binding.textDistance.text = caminhadas[position].getAproxDistance().toString()
        holder.binding.caminhadaLayout.setOnClickListener { listener.clickCaminhadaItem(position) }
    }
}