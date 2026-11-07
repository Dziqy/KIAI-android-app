package com.im.kiai.ui.location

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.im.kiai.R
import com.im.kiai.network.message.response.Locations
import kotlinx.android.synthetic.main.item_row_location.view.*
import java.util.*

class KajianLocationListAdapter(var mData: List<Locations>, private val onClickListener: (Locations) -> Unit): RecyclerView.Adapter<KajianLocationListAdapter.CardViewViewHolder>() {

    private lateinit var ctx: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_location, parent, false)
        ctx = view.context
        return CardViewViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: CardViewViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val item = mData[position]
        holder.itemView.tv_name.text = item.nama
        holder.itemView.tv_address.text = "${item.alamat}, ${item.kecamatan}, ${item.kabupaten}, ${item.provinsi}"
        holder.itemView.setOnClickListener {
            onClickListener(item)
        }
        
    }


    inner class CardViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){}



}