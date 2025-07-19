package br.edu.ifsp.dmo2.walkompanion.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.dmo2.walkompanion.R
import br.edu.ifsp.dmo2.walkompanion.databinding.CaminhadaItemBinding
import br.edu.ifsp.dmo2.walkompanion.listener.CaminhadaItemClickListener
import br.edu.ifsp.dmo2.walkompanion.model.Caminhada

class CaminhadaAdapter(
    private val caminhadas: Array<Caminhada>,
    private val listener: CaminhadaItemClickListener
) :
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
        val date = caminhadas[position].getInicio().toDate()
        val day = if (date.date < 10) "0${date.date}" else "${date.date}"
        val month = if ((date.month + 1) < 10) "0${date.month + 1}" else "${date.month + 1}"
        val minutes = if (date.minutes < 10) "0${date.minutes}" else "${date.minutes}"
        val dateStr =
            "${day}/${month}/${(date.year + 1900)} - ${date.hours}:${minutes}"
        holder.binding.textDate.text = dateStr

        val duration = caminhadas[position].getDuration()
        val durationStr = if (duration.inWholeMinutes > 60) {
            val hours = duration.inWholeHours
            val minutes = duration.inWholeMinutes - (hours * 60)
            "${hours} hora(s) e ${minutes} minuto(s)"
        } else {
            "${duration.inWholeMinutes} minuto(s)"
        }
        holder.binding.textTime.text = "Duração: ${durationStr}"

        holder.binding.textSteps.text = "Passos dados: ${caminhadas[position].getSteps()}"

        val distanceStr =
            if (caminhadas[position].getAproxDistance() < 1000)
                "${caminhadas[position].getAproxDistance()}m"
            else "${"%.2f".format(caminhadas[position].getAproxDistance() / 1000)}km"

        holder.binding.textDistance.text = "Distância percorrida: ${distanceStr}"

        holder.binding.caminhadaLayout.setOnClickListener { listener.clickCaminhadaItem(position) }
    }
}