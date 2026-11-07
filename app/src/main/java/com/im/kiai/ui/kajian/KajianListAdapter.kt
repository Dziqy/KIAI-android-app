package com.im.kiai.ui.kajian

import android.content.Context
import android.content.Intent
import android.location.Location
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.im.kiai.model.Kajian
import com.im.kiai.R
import com.im.kiai.network.ApiService
import kotlinx.android.synthetic.main.item_row.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.round

class KajianListAdapter(var mData: List<Kajian>, private val clickListener: (Kajian) -> Unit): RecyclerView.Adapter<KajianListAdapter.CardViewViewHolder>() {

    private lateinit var ctx: Context
    private  var location: Location? = null

    fun setData(newData: List<Kajian>){
        mData = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        ctx = view.context
        return CardViewViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: CardViewViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val item = mData[position]
        holder.itemView.tv_title.text = item.judul
        holder.itemView.tv_presenter.text = item.asatidz
        holder.itemView.tv_location.text = "${item.nama} (${String.format("%.2f", (item.distance ?: 0.1))} km)\nSidoarjo"

        var dateFormatted = getDateFormat(item.tanggal+" "+item.waktu)
        holder.itemView.tv_date.text = dateFormatted


        var img = "${ApiService.URL}storage/${item.poster}"
        Glide.with(ctx)
            .load(img)
            .into( holder.itemView.iv_poster)
        if(isToday(item.tanggal)) holder.itemView.chip_today.visibility = View.VISIBLE
        if(isTomorrow(item.tanggal)) holder.itemView.chip_tomorrow.visibility = View.VISIBLE
        if(item.jamaah == "Ikhwan") holder.itemView.iv_muslimah.visibility = View.GONE
        if(item.jamaah == "Akhwat") holder.itemView.iv_muslim.visibility = View.GONE
        if(item.streaming!=null){
            holder.itemView.tv_location.visibility = View.GONE
            holder.itemView.iv_online.visibility = View.VISIBLE
//            holder.itemView.iv_live.visibility = View.VISIBLE
        }

//        Log.d("TEST" ,"DATE ${item.waktu}")
//        var datetime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            LocalDateTime.parse( item.waktu )      // Parse an input string in standard ISO 8601 format. Returns a `LocalDateTime` object.
//                .toLocalDate()                            // Extract the date-only portion without the time-of-day. Still no time zone or offset-from-UTC. Returns a `LocalDate` object.
//                .format(                                  // Generate text representing the value of that `LocalDate` object.
//                    DateTimeFormatter                     // Define a pattern to use in generating text.
//                        .ofLocalizedDate( FormatStyle.LONG )  // Automatically localize, specifying how long/abbreviated…
//                        .withLocale( Locale.getDefault() )              // … and what human language and cultural norms to use in localizing.
//                )
//        } else {
//            item.waktu
//        }

        holder.itemView.item.setOnClickListener {

            clickListener(item)
        }


    }


    inner class CardViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


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