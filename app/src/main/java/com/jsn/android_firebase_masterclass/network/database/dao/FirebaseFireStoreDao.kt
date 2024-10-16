package com.jsn.android_firebase_masterclass.network.database.dao

import android.util.Log
import com.jsn.android_firebase_masterclass.MyApplication
import com.jsn.android_firebase_masterclass.model.Item

object FirebaseFireStoreDao {
    fun addItem(item: Item) {
        val docRef = MyApplication.firestoreCollectionRef.document(item.id)
        docRef.set(item)
            .addOnSuccessListener { Log.d("TAG", "Item added successfully!") }
            .addOnFailureListener { e -> Log.w("TAG", "Error adding item", e) }
    }

    fun getItems(callback: (List<Item>) -> Unit) {
        MyApplication.firestoreCollectionRef
            .get()
            .addOnSuccessListener { documents ->
                val itemList = documents.map { document ->
                    document.toObject(Item::class.java).apply {
                        id = document.id
                    }
                }
                callback(itemList)
            }
            .addOnFailureListener { e -> Log.w("TAG", "Error getting items", e) }
    }

    fun getItemById(itemId: String, callback: (Item?) -> Unit) {
        MyApplication.firestoreCollectionRef.document(itemId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val item = document.toObject(Item::class.java)
                    item?.id = document.id // Set the ID if needed
                    callback(item)
                } else {
                    Log.w("TAG", "Item not found")
                    callback(null) // Return null if the document doesn't exist
                }
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error getting item by ID", e)
                callback(null) // Return null in case of error
            }
    }


    fun updateItem(item: Item) {
        MyApplication.firestoreCollectionRef.document(item.id)
            .set(item)
            .addOnSuccessListener { Log.d("TAG", "Item updated successfully!") }
            .addOnFailureListener { e -> Log.w("TAG", "Error updating item", e) }
    }

    fun deleteItem(itemId: String) {
        MyApplication.firestoreCollectionRef.document(itemId)
            .delete()
            .addOnSuccessListener { Log.d("TAG", "Item deleted successfully!") }
            .addOnFailureListener { e -> Log.w("TAG", "Error deleting item", e) }
    }




}