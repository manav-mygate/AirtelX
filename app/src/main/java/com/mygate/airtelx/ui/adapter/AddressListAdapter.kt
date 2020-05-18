package com.mygate.airtelx.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mygate.airtelx.R
import com.mygate.airtelx.model.AddressList
import kotlinx.android.synthetic.main.item_search_view.view.*
import java.util.*


class AddressListAdapter(val context: Activity, val addressList: ArrayList<AddressList>) :
    RecyclerView.Adapter<AddressListAdapter.MyViewHolder>() {

    companion object {
        private const val TAG = "AddressListAdapter"
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(context)
            .inflate(R.layout.item_search_view, parent, false)
        return MyViewHolder(v)
    }


    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        viewHolder.onBind(position);
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    override fun getItemCount(): Int {
        return  addressList.size
    }


    private fun getItem(position: Int): AddressList {
        return addressList[position]
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvAddressName = itemView.tvAddressName
        fun onBind(position: Int) {
            val data = getItem(position);
            tvAddressName.text = data.addressString
        }

    }

}