import android.location.Location
import com.example.chensproject.Customer

class Buissnes(
    businessName: String = "",
    aboutMe: String = "",
    prices: String = "",
    businessLocation: String = "",
    location: String =""
) {
    var name: String = businessName
    private var aboutMe: String = aboutMe
    private var customersList = mutableListOf<Customer>()
    private var prices: String = prices
    var location: String = businessLocation
    private var possiblequeues = mutableListOf<com.example.chensproject.Queue>()
    private var queueList = mutableListOf<com.example.chensproject.Queue>()

    // פעולות של המחלקה
    fun changeName(newName: String) {
        name = newName
    }

    fun changePrices(newPrices: String) {
        prices = newPrices
    }

    fun cancelQueue(newQueue: com.example.chensproject.Queue) {
        queueList.remove(newQueue)
        newQueue.getCustomer().cancelQueue(newQueue)
    }

    fun addPossibleQueue(newQueue: com.example.chensproject.Queue) {
        possiblequeues.add(newQueue)
    }

    fun updateQueue(newQueue: com.example.chensproject.Queue) {
        possiblequeues.add(newQueue)
        queueList.remove(newQueue)
    }

    fun changeLocation(newLocation: String) {
        location = newLocation
    }
}

