package com.im.kiai.ui.following

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.im.kiai.R
import com.im.kiai.network.message.response.Asatidz
import kotlinx.android.synthetic.main.item_row_following.view.*

class FollowingListAdapter(var mData: List<Asatidz>, private val onClickListener: (Asatidz) -> Unit): RecyclerView.Adapter<FollowingListAdapter.CardViewViewHolder>() {

    private lateinit var ctx: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_following, parent, false)
        ctx = view.context
        return CardViewViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: CardViewViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val item = mData[position]
        holder.itemView.tv_name.text = item.name
        holder.itemView.tv_qty.text = "${item.kajian} kajian"
        holder.itemView.setOnClickListener {
            onClickListener(item)
        }
    }


    inner class CardViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){}




}