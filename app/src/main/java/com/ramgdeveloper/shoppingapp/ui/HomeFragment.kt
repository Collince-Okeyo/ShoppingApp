package com.ramgdeveloper.shoppingapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.database.*
import com.mosesaltruism.mosesaltruism.NetworkStates
import com.ramgdeveloper.shoppingapp.ShoppingAdapter
import com.ramgdeveloper.shoppingapp.databinding.FragmentHomeBinding
import com.ramgdeveloper.shoppingapp.model.Items
import timber.log.Timber

class HomeFragment : NetworkStates(){

    private lateinit var binding: FragmentHomeBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var itemList: ArrayList<Items>
        private val adapter by lazy {
        ShoppingAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        databaseReference = FirebaseDatabase.getInstance().getReference("items")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {

                itemList = ArrayList()
                if (p0.exists()) {
                    for (i in p0.children) {
                        val itm = i.getValue(Items::class.java)
                        itemList.add(itm!!)
                        Timber.d("Snapshot")

                    }

                    adapter.submitList(itemList)
                    binding.recyclerView.adapter = adapter
                    // binding.progressBar.isVisible = false
                }
                else{
                    Timber.d("No Snapshot")
                    // binding.progressBar.isVisible = true
                    //binding.button2.isVisible = true
                }
            }
        })

        return view
    }

    private fun addToCat(){
        binding.cartImage.setOnClickListener {
            Toast.makeText(requireContext(), "Cart clicked", Toast.LENGTH_SHORT).show()
        }
    }

}