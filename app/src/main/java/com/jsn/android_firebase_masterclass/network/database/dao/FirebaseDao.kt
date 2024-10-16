package com.jsn.android_firebase_masterclass.network.database.dao

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.jsn.android_firebase_masterclass.MyApplication
import com.jsn.android_firebase_masterclass.model.Item

object FirebaseDao {
    fun addItem(item: Item) {
        val id = MyApplication.databaseRef.push().key ?: return // Generate a unique ID
        item._id = id
        MyApplication.databaseRef.child(id).setValue(item)
            .addOnSuccessListener { Log.d("TAG", "Item added successfully!") }
            .addOnFailureListener { e -> Log.w("TAG", "Error adding item", e) }
    }

    fun getItems(callback: (List<Item>) -> Unit) {
        MyApplication.databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val itemList = mutableListOf<Item>()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(Item::class.java)
//                    item?.let {
//                        it._id = itemSnapshot.key ?: ""
//                        itemList.add(it)
//                    }
                    item?.let { itemList.add(it) }
                }
                callback(itemList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "Error getting items", error.toException())
            }
        })
    }

    fun getItemById(itemId: String, callback: (Item?) -> Unit) {
        MyApplication.databaseRef.child(itemId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val item = snapshot.getValue(Item::class.java)
                // Optionally set the ID if it's not included in the item
                item?._id = itemId
                callback(item)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "Error getting item by ID", error.toException())
                callback(null) // Return null in case of error
            }
        })
    }

    fun updateItem(item: Item) {
        MyApplication.databaseRef.child(item.id).setValue(item)
            .addOnSuccessListener { Log.d("TAG", "Item updated successfully!") }
            .addOnFailureListener { e -> Log.w("TAG", "Error updating item", e) }
    }

    fun deleteItem(itemId: String) {
        MyApplication.databaseRef.child(itemId).removeValue()
            .addOnSuccessListener { Log.d("TAG", "Item deleted successfully!") }
            .addOnFailureListener { e -> Log.w("TAG", "Error deleting item", e) }
    }


}