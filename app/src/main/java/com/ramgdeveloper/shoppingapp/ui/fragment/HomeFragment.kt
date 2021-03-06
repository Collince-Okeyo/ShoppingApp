package com.ramgdeveloper.shoppingapp.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import com.google.firebase.database.*
import com.mosesaltruism.mosesaltruism.NetworkStates
import com.ramgdeveloper.shoppingapp.R
import com.ramgdeveloper.shoppingapp.adapter.ShoppingAdapter
import com.ramgdeveloper.shoppingapp.databinding.FragmentHomeBinding
import com.ramgdeveloper.shoppingapp.model.ItemsEntity
import timber.log.Timber

class HomeFragment : NetworkStates(), SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var databaseReference: DatabaseReference
    private var items: ArrayList<ItemsEntity> = arrayListOf()
    private var adapter: ShoppingAdapter = ShoppingAdapter(items)
    //private var items: ArrayList<Items> = arrayListOf()
    private var matchedItems: ArrayList<ItemsEntity> = arrayListOf()

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
                        val itm = i.getValue(ItemsEntity::class.java)
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

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)

        val search = menu?.findItem(R.id.searchMenu)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        TODO("Not yet implemented")
    }

/*    private fun searchItems(){
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
    }*/
}