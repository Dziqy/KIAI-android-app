package com.im.kiai.ui.astidz

import android.content.Context
import android.location.Location
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.im.kiai.R
import com.im.kiai.network.message.response.Asatidz
import kotlinx.android.synthetic.main.item_row_asatidz.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AsatidzListAdapter(var mData: List<Asatidz>, private val onClickFollow: (Asatidz) -> Unit, private val onClickUnfollow: (Asatidz) -> Unit): RecyclerView.Adapter<AsatidzListAdapter.CardViewViewHolder>() {

    private lateinit var ctx: Context
    private  var location: Location? = null

    fun setData(newData: List<Asatidz>){
        mData = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_asatidz, parent, false)
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
        holder.itemView.tv_desc.text = item.deskripsi
        holder.itemView.btn_follow.visibility = if((item.following ?: 0)>0) View.GONE else View.VISIBLE
        holder.itemView.btn_unfollow.visibility = if((item.following ?: 0)>0) View.VISIBLE else View.GONE

        holder.itemView.btn_follow.setOnClickListener {
            onClickFollow(item)
        }
        holder.itemView.btn_unfollow.setOnClickListener {
            onClickUnfollow(item)
        }
    }


    inner class CardViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){}


    private fun getDateFormat(string: String?): String? {
        val inputSDF = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val outputSDF = SimpleDateFormat("EEE, dd MMM yyyy HH:mm", Locale.getDefault())
        var date: Date? = null
        date = try {
            //here you get Date object from string
            print("parse $string")
            inputSDF.parse(string)
        } catch (e: ParseException) {
            print("error ${e.message}")
            Log.d("MainActivity", "error ${e.message}")
            return string
        }
        //after changing date format again you can change to string with changed format
        return outputSDF.format(date)
    }

    private fun isToday(string: String): Boolean{
        val inputSDF = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return try {
            val date = inputSDF.parse(string)
            DateUtils.isToday(date.time )
        } catch (e: ParseException) {
            Log.d("MainActivity", "error ${e.message}")
            false
        }
    }

    private fun isTomorrow(string: String): Boolean{
        val inputSDF = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return try {
            val date = inputSDF.parse(string)
            DateUtils.isToday(date.time - DateUtils.DAY_IN_MILLIS )
        } catch (e: ParseException) {
            Log.d("MainActivity", "error ${e.message}")
            false
        }
    }


}