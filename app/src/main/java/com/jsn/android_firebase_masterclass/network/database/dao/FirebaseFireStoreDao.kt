import android.util.Log
import com.jsn.android_firebase_masterclass.MyApplication
import com.jsn.android_firebase_masterclass.model.Item
import kotlinx.coroutines.tasks.await

object FirebaseFireStoreDao {

    // Insert item

    //Using this method with clean coding architecture
    suspend fun addItemWOL(item: Item): Result<Unit> {
        return try {
            val docRef = MyApplication.firestoreCollectionRef.document(item.id)
            docRef.set(item).await() // Wait for the operation to complete
            Log.d("TAG", "Item added successfully!")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.w("TAG", "Error adding item", e)
            Result.failure(e)
        }
    }

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

    //Using this method with clean coding architecture
    suspend fun getItemsWOL(): List<Item> {
        return try {
            val documents = MyApplication.firestoreCollectionRef.get().await()
            documents.map { document ->
                document.toObject(Item::class.java).apply {
                    id = document.id
                }
            }
        } catch (e: Exception) {
            Log.w("TAG", "Error getting items", e)
            emptyList() // Return an empty list on failure
        }
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

    //Using this method with clean coding architecture
    suspend fun getItemByIdWOL(itemId: String): Item? {
        return try {
            val document = MyApplication.firestoreCollectionRef.document(itemId).get().await()
            if (document.exists()) {
                val item = document.toObject(Item::class.java)
                item?.id = document.id // Set the ID if needed
                item
            } else {
                Log.w("TAG", "Item not found")
                null // Return null if the document doesn't exist
            }
        } catch (e: Exception) {
            Log.w("TAG", "Error getting item by ID", e)
            null // Return null in case of error
        }
    }

    // Update item
    //Using this method with clean coding architecture
    suspend fun updateItemWOL(item: Item): Result<Unit> {
        return try {
            MyApplication.firestoreCollectionRef.document(item.id)
                .set(item).await() // Wait for the operation to complete
            Log.d("TAG", "Item updated successfully!")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.w("TAG", "Error updating item", e)
            Result.failure(e)
        }
    }

    fun updateItem(item: Item) {
        MyApplication.firestoreCollectionRef.document(item.id)
            .set(item)
            .addOnSuccessListener { Log.d("TAG", "Item updated successfully!") }
            .addOnFailureListener { e -> Log.w("TAG", "Error updating item", e) }
    }

    // Delete item
    //Using this method with clean coding architecture
    suspend fun deleteItemWOL(itemId: String): Result<Unit> {
        return try {
            MyApplication.firestoreCollectionRef.document(itemId)
                .delete().await() // Wait for the delete operation to complete
            Log.d("TAG", "Item deleted successfully!")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.w("TAG", "Error deleting item", e)
            Result.failure(e) // Return failure in case of an error
        }
    }

    fun deleteItem(itemId: String) {
        MyApplication.firestoreCollectionRef.document(itemId)
            .delete()
            .addOnSuccessListener { Log.d("TAG", "Item deleted successfully!") }
            .addOnFailureListener { e -> Log.w("TAG", "Error deleting item", e) }
    }
}
