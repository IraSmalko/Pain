package com.pain.dev14.pain

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.rv_item.view.*

/**
 * Created by dev14 on 07.08.17.
 */


class WeatherRecyclerAdapter(val items: MutableList<Weather> = mutableListOf()) : RecyclerView.Adapter<WeatherRecyclerAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    fun updateAdapter(updateItems: Weather): Unit {
        items.add(updateItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Weather) = with(itemView) {
            tv_title.text = item.query.results.channel.title
            tv_temp.text = item.temp.toString()
        }
    }
}
