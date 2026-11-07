package com.im.kiai.ui.home;

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.im.kiai.R
import com.im.kiai.model.Kajian
import com.im.kiai.network.ApiService
import com.im.kiai.ui.kajian.DetailKajianActivity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class ImageSlideAdapter( val context: Context, private var imageList: List<Kajian>) : PagerAdapter() {
    override fun getCount(): Int {
        return imageList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View =  (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.item_slider, null)
        val item = view.findViewById<View>(R.id.cv_images)
        val ivImages = view.findViewById<ImageView>(R.id.iv_images)
        val ivLive = view.findViewById<ImageView>(R.id.iv_live)
        val ivOnline = view.findViewById<ImageView>(R.id.iv_online)
        val tvDate = view.findViewById<TextView>(R.id.tv_date)
        val tvTittle = view.findViewById<TextView>(R.id.tv_title)
        val tvPresenter = view.findViewById<TextView>(R.id.tv_presenter)

       imageList[position].let {
           var img = "${ApiService.URL}storage/${it.poster}"
           Glide.with(context)
                    .load(img)
                    .into(ivImages)

           var dateFormatted = getDateFormat(it.tanggal)
           tvDate.text = "$dateFormatted ${it.waktu}"
           tvTittle.text = it.judul
           tvPresenter.text = it.asatidz

           item.setOnClickListener {v ->
               val intent = Intent(context, DetailKajianActivity::class.java)
               intent.putExtra("data", it)
               context.startActivity(intent)
           }

           ivOnline.visibility = if( it.streaming!=null) View.VISIBLE else View.GONE
           ivLive.visibility = if( it.streaming!=null) View.VISIBLE else View.GONE
        }


        val vp = container as ViewPager
        vp.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val view = `object` as View
        vp.removeView(view)
    }

    private fun getDateFormat(string: String?): String? {
        val inputSDF = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputSDF = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
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
}