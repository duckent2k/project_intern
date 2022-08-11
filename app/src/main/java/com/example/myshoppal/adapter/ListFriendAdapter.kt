package com.example.myshoppal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myshoppal.activities.ui.viewmodels.ItemsViewModel
import com.myshoppal.R

class ListFriendAdapter(private val mList: MutableList<ItemsViewModel>) :
    RecyclerView.Adapter<ListFriendAdapter.FriendViewHolder>() {
    //danh sach nhung phan tu co chung 1 kieu du lieu
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        // inflates the item_list_friend view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_friend, parent, false)

        return FriendViewHolder(view)//truy cap cac thanh phan cua danh sach
    }

    // binds the list items to a view
    //update data recyclerview
    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val itemsViewModel = mList[position]
        // sets the text to the textview from our itemHolder class
        holder.ten.text = itemsViewModel.name
        holder.tuoi.text = itemsViewModel.age.toString()
        holder.queQuan.text = itemsViewModel.country
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to text
    class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ten: TextView = itemView.findViewById(R.id.id_name)
        val tuoi: TextView = itemView.findViewById(R.id.id_age)
        val queQuan: TextView = itemView.findViewById(R.id.id_country)
    }


    fun setDataList(list: MutableList<ItemsViewModel>) {
        this.mList.addAll(list)
        notifyDataSetChanged()//update data
    }


}
