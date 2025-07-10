package br.edu.ifsp.dmo2.walkompanion.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.dmo2.walkompanion.R
import br.edu.ifsp.dmo2.walkompanion.model.Caminhada

class CaminhadaAdapter(private val caminhadas: Array<Caminhada>) :
    RecyclerView.Adapter<CaminhadaAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val txtDate : TextView = view.findViewById(R.id.text_date)
        val txtDuration : TextView = view.findViewById(R.id.text_time)
        val txtSteps : TextView = view.findViewById(R.id.text_steps)
        val txtDistance : TextView = view.findViewById(R.id.text_distance)
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
        holder.txtDate.text = caminhadas[position].getInicio().toString()
        holder.txtDuration.text = caminhadas[position].getDuration().toString()
        holder.txtSteps.text = caminhadas[position].getSteps().toString()
        holder.txtDistance.text = caminhadas[position].getAproxDistance().toString()
    }
}