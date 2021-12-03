package com.ramgdeveloper.shoppingapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.database.*
import com.mancj.materialsearchbar.MaterialSearchBar
import com.mosesaltruism.mosesaltruism.NetworkStates
import com.ramgdeveloper.shoppingapp.ShoppingAdapter
import com.ramgdeveloper.shoppingapp.databinding.FragmentHomeBinding
import com.ramgdeveloper.shoppingapp.model.Items
import timber.log.Timber

class HomeFragment : NetworkStates(){

    private lateinit var binding: FragmentHomeBinding
    private lateinit var databaseReference: DatabaseReference
    private var items: ArrayList<Items> = arrayListOf()
    private var adapter: ShoppingAdapter = ShoppingAdapter(items)
    //private var items: ArrayList<Items> = arrayListOf()
    private var matchedItems: ArrayList<Items> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.cartImage.setOnClickListener {
            Toast.makeText(requireContext(), "Cart Items Clicked", Toast.LENGTH_SHORT).show()
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("items")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {

                items = ArrayList()
                if (p0.exists()) {
                    for (i in p0.children) {
                        val itm = i.getValue(Items::class.java)
                        items.add(itm!!)
                        Timber.d("Snapshot")

                    }

                    adapter.submitList(items)
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

        searchItems()
        return view
    }

    private fun searchItems(){
        binding.materialSearchBar.setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener{
            override fun onSearchStateChanged(enabled: Boolean) {
                search(enabled.toString())
            }

            override fun onSearchConfirmed(text: CharSequence?) {
                search(text.toString())
            }

            override fun onButtonClicked(buttonCode: Int) {

            }
        })
    }

    private fun search(text: String){
        matchedItems = arrayListOf()

        text?.let {
            items.forEach { item ->
                if (item.itemName!!.contains(text, true) || item.itemPrice!!.contains(text, true)){
                    matchedItems.add(item)
                }
                updateRecyclerView()
                if (matchedItems.isEmpty()){
                    Toast.makeText(requireContext(), "No match found", Toast.LENGTH_SHORT).show()
                }
                updateRecyclerView()
            }
        }
    }

    private fun updateRecyclerView() {
        binding.recyclerView.apply {
            this.adapter?.notifyDataSetChanged()
        }
    }
}