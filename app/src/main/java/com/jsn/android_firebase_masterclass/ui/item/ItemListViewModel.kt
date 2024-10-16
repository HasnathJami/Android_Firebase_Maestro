import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jsn.android_firebase_masterclass.model.Item
import kotlinx.coroutines.launch

class ItemViewModel(private val repository: ItemRepository) : ViewModel() {

    private val _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>> get() = _items

    private val _operationResult = MutableLiveData<Result<Unit>>()
    val operationResult: LiveData<Result<Unit>> get() = _operationResult

    // Fetch all items
    fun fetchItems() {
        viewModelScope.launch {
            val result = repository.fetchAllItems()
            _items.value = result.getOrNull() ?: emptyList()
        }
    }

    // Add a new item
    fun addItem(item: Item) {
        viewModelScope.launch {
            val result = repository.insertItem(item)
            _operationResult.value = result
        }
    }

    // Update an existing item
    fun updateItem(item: Item) {
        viewModelScope.launch {
            val result = repository.modifyItem(item)
            _operationResult.value = result
        }
    }

    // Delete an item by ID
    fun deleteItem(itemId: String) {
        viewModelScope.launch {
            val result = repository.removeItem(itemId)
            _operationResult.value = result
        }
    }

    // Optionally, you can add methods for handling the result of operations
    fun handleOperationResult() {
        operationResult.value?.let { result ->
            if (result.isSuccess) {
                // Handle success (e.g., show a success message or refresh the item list)
            } else {
                // Handle error (e.g., show an error message)
            }
        }
    }
}
