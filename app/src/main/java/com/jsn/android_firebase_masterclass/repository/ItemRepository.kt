import com.jsn.android_firebase_masterclass.model.Item

class ItemRepository {

    private val firestoreDao = FirebaseFireStoreDao

    // Fetch all items
    suspend fun fetchAllItems(): Result<List<Item>> {
        return try {
            val itemList = firestoreDao.getItemsWOL() // Using the suspend function
            Result.success(itemList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Add a new item
    suspend fun insertItem(item: Item): Result<Unit> {
        return try {
            firestoreDao.addItemWOL(item) // Assuming addItem is updated to be a suspend function
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Update an existing item
    suspend fun modifyItem(item: Item): Result<Unit> {
        return try {
            firestoreDao.updateItemWOL(item) // Assuming updateItem is updated to be a suspend function
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Delete an item by ID
    suspend fun removeItem(itemId: String): Result<Unit> {
        return firestoreDao.deleteItemWOL(itemId) // Using the suspend deleteItem function
    }

    // Fetch an item by ID
    suspend fun fetchItemById(itemId: String): Result<Item?> {
        return try {
            val item = firestoreDao.getItemByIdWOL(itemId) // Using the suspend function
            Result.success(item)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
