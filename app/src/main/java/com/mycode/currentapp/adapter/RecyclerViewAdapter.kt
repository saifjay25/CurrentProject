package com.mycode.currentapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mycode.currentapp.R
import com.mycode.currentapp.entities.Result


class RecyclerViewAdapter(var context : Context, var burritoList : List<Result>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private lateinit var listener : OnitemClickListener
    var price = ""
    companion object{
        var recyclerCounter = 0
    }

    override fun getItemCount(): Int {
        return burritoList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.burritoPlace.text = burritoList[position].name
        holder.address.text = burritoList[position].address
        if(burritoList[position].price.isNotEmpty()) {
            var num = burritoList[position].price.toInt()
            while (num != 0) {
                price += "$"
                num--
            }

            recyclerCounter++
        }else{
            price = "$"
        }
        burritoList[position].priceSign = price
        holder.priceLevel.text = price+" · "+burritoList[position].rating
        price =""
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.row_layout, parent, false)
        return ViewHolder(v)
    }

    inner class ViewHolder(item : View) :RecyclerView.ViewHolder(item), View.OnClickListener {
        var burritoPlace : TextView
        var address : TextView
        var priceLevel : TextView
        init{
            burritoPlace = item.findViewById(R.id.burritoPlace)
            address = item.findViewById(R.id.address)
            priceLevel = item.findViewById(R.id.priceLevelandRating)
            item.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            if(listener!=null){
                val position:Int = adapterPosition
                if(position!=RecyclerView.NO_POSITION){
                    listener.itemOnClick(position)
                }
            }
        }
    }

    interface OnitemClickListener{
        fun itemOnClick(position : Int)
    }

    fun setOnitemClickListener(listener: OnitemClickListener){
        this.listener = listener
    }
}