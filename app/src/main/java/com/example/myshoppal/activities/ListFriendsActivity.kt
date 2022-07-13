package com.example.myshoppal.activities

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myshoppal.R
import com.example.myshoppal.adapter.ListFriendAdapter
import com.example.myshoppal.databinding.ActivityListFriendsBinding
import com.example.myshoppal.ui.viewmodels.ItemsViewModel
import kotlinx.android.synthetic.main.activity_list_friends.*

class ListFriendsActivity  : BaseActivity(){

    private lateinit var binding: ActivityListFriendsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListFriendsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpActionBar()
        setListFriendRecyclerView()

    }

    private fun setUpActionBar() {
        setSupportActionBar(binding.toolbarListFriendActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_color_black_24dp)
        binding.toolbarListFriendActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setListFriendRecyclerView() {
        // this creates a vertical layout Manager
        binding.recyclerview.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false )


        // ArrayList of class ItemsViewModel
        val data = mutableListOf<ItemsViewModel>()
        data.add(ItemsViewModel("Duc", 22, "Bac Ninh"))
        data.add(ItemsViewModel("Trung", 18, "Ha Noi"))
        data.add(ItemsViewModel("Hieu", 17, "Hai Phong"))
        data.add(ItemsViewModel("Hai", 23, "Hoa Binh"))
        data.add(ItemsViewModel("Thanh", 23, "Ha Noi"))

        // This will pass the ArrayList to our Adapter
        val adapter = ListFriendAdapter(mutableListOf())

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

        adapter.setDataList(data)
    }
}