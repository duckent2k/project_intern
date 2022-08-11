package com.example.myshoppal.activities.ui.fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myshoppal.activities.ui.activities.AddProductActivity
import com.example.myshoppal.activities.ui.activities.SettingsActivity
import com.example.myshoppal.adapter.MyProductsListAdapter
import com.example.myshoppal.firestore.FireStoreClass
import com.example.myshoppal.models.Product
import com.myshoppal.R
import com.myshoppal.databinding.FragmentProductsBinding
import kotlinx.android.synthetic.main.fragment_products.*

class ProductsFragment : BaseFragment() {

    private var _binding: FragmentProductsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    fun successProductsListFromFireStore(producList: ArrayList<Product>) {
        hideProgressDialog()

      if (producList.size > 0) {
          rv_my_product_items.visibility = View.VISIBLE
          tv_no_products_found.visibility = View.GONE

          rv_my_product_items.layoutManager = LinearLayoutManager(activity)
          rv_my_product_items.setHasFixedSize(true)
          val adapterProducts = MyProductsListAdapter(requireActivity(), producList)
          rv_my_product_items.adapter = adapterProducts
      } else {
          rv_my_product_items.visibility = View.GONE
          tv_no_products_found.visibility = View.VISIBLE
      }
    }

    override fun onResume() {
        super.onResume()
        getProductsListFromFireStore()
    }


    private fun getProductsListFromFireStore() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FireStoreClass().getProductsList(this)
    }


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val homeViewModel =
//            ViewModelProvider(
//                this,
//                ViewModelProvider.NewInstanceFactory()
//            ).get(HomeViewModel::class.java)

        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_product_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(id) {
            R.id.action_add_product -> {
                startActivity(Intent(activity, AddProductActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}