package com.jsn.android_firebase_masterclass.ui.item

import ItemRepository
import ItemViewModel
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jsn.android_firebase_masterclass.R
import com.jsn.android_firebase_masterclass.model.Item

class ItemListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter
    private var itemList: MutableList<Item> = mutableListOf()


    // Get a ViewModel instance
    // Create an instance of ItemRepository
    private val itemRepository = ItemRepository()

    private val itemViewModel: ItemViewModel by viewModels { ItemViewModelFactory(itemRepository) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        recyclerView = findViewById(R.id.rvItemList)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = ItemAdapter(itemList) { item ->
            // Handle item click (e.g., open a detail screen)
            Toast.makeText(this@ItemListActivity, "Item is clicked", Toast.LENGTH_LONG).show()
        }
        recyclerView.adapter = adapter

        handlingDBOperationWithoutCleanCode()

        //Observe item from the ViewModel
        itemViewModel.fetchItems()
        itemViewModel.items.observe(this) { items ->
            itemList.clear()
            itemList.addAll(items)
            adapter.notifyDataSetChanged()
        }
    }

//    private fun getItems(callback: (List<Item>) -> Unit) {
//        // Call your Firebase Realtime Database getItems function here
//        FirebaseDao.getItems { items ->
//            itemList.clear()
//            itemList.addAll(items)
//            adapter.notifyDataSetChanged()
//        }
//    }

    private fun handlingDBOperationWithoutCleanCode() {
        //Fetch Real Time DB Data
//        FirebaseDao.getItems { items ->
//            itemList.clear()
//            itemList.addAll(items)
//            adapter.notifyDataSetChanged()
//        }

        //Fetch Fire Store DB Data
//        FirebaseFireStoreDao.getItems { items ->
//            itemList.clear()
//            itemList.addAll(items)
//            adapter.notifyDataSetChanged()
//        }
    }
}