package com.ramgdeveloper.shoppingapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.*
import com.ramgdeveloper.shoppingapp.ShoppingAdapter
import com.ramgdeveloper.shoppingapp.databinding.FragmentHomeBinding
import com.ramgdeveloper.shoppingapp.model.Items
import timber.log.Timber


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var itemList: ArrayList<Items>
        private val adapter by lazy {
        ShoppingAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        databaseReference = FirebaseDatabase.getInstance().getReference("items")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    for (i in p0.children) {
                        val itm = i.getValue(Items::class.java)
                        itemList.add(itm!!)
                        Log.d("HomeFragment", "Snapshot")
                        //Timber.d("Snapshot exists")
                        //itemList!!.add(itm!!)
                        //shimmerFrameLayout.visibility = View.GONE
                       // recyclerView.visibility = View.VISIBLE
                    }

                    adapter.submitList(itemList)
                    binding.recyclerView.adapter = adapter
                    //val adapter = ItemsAdapter(applicationContext, itemList!!)
                    //mRecyclerView.adapter = adapter
                }
                else{
                    Log.d("HomeFragment", "No Snapshot")
                }
            }
        })
        return view
    }

}