package co.edu.unab.mgads.jdcn.applicht.model.repository

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.edu.unab.mgads.jdcn.applicht.model.entity.Lamp
import co.edu.unab.mgads.jdcn.applicht.model.remote.LichtAppAPI
import co.edu.unab.mgads.jdcn.applicht.model.remote.service.LampService
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import retrofit2.Retrofit
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class LampRepository  (myContext: Context) {

    private val PRODUCT_COLLECTION:String="products"

    var Lamps: MutableLiveData<List<Lamp>> = MutableLiveData()

    private val firestore: FirebaseFirestore = Firebase.firestore
    private val api: Retrofit =LichtAppAPI.getInstance()!!
    private val productService:LampService = api.create(LampService::class.java)

    init {
    }

    fun loadAllFirestore (){
        firestore.collection(PRODUCT_COLLECTION).get().addOnSuccessListener { result ->
            val productsList:ArrayList<Lamp> = arrayListOf()
            if (!result.isEmpty){
                for (document in result.documents){
                    val myProduct:Lamp?=document.toObject(Lamp::class.java)
                    myProduct?.let {
                        it.id=document.id
                        productsList.add(it)
                    }
                }
            }
            Lamps.value=productsList
        }
    }

    fun listenAllFirestore(){
        firestore.collection(PRODUCT_COLLECTION).addSnapshotListener{snapshot,e ->
            snapshot?.let{
                val productsList:ArrayList<Lamp> = arrayListOf()
                if (!snapshot.isEmpty){
                    for (document in snapshot.documents){
                        val myProduct:Lamp?=document.toObject(Lamp::class.java)
                        myProduct?.let {
                            it.id=document.id
                            productsList.add(it)
                        }
                    }
                }
                Lamps.value=productsList
            }
        }
    }

    fun getByIdFirestore(id:String){
        firestore.collection(PRODUCT_COLLECTION).document(id).get().addOnSuccessListener { result ->
            val myProduct:Lamp?=result.toObject(Lamp::class.java)
            myProduct?.let {
                it.id=id
                Lamps.value= listOf(it)
            }
        }
    }

    fun addFirestore(myProduct: Lamp,photoUri: Uri?): LiveData<String> {
        val productIdObserver: MutableLiveData<String> = MutableLiveData()
        photoUri?.let{
            val storageReference = Firebase.storage.reference.child(PRODUCT_COLLECTION)
            val time = SimpleDateFormat("yyyy-MM-dd-HHmmss", Locale.US).format(Date())
            val name = time + myProduct.name + ".jpg"
            storageReference.child(name).putFile(photoUri).addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener { url->
                    myProduct.image = url.toString()
                    firestore.collection(PRODUCT_COLLECTION).add(myProduct).addOnSuccessListener { response->
                        productIdObserver.value = response.id
                    }.addOnFailureListener{
                        productIdObserver.value=""
                    }
                }
            }
        }?:run{
            firestore.collection(PRODUCT_COLLECTION).add(myProduct).addOnSuccessListener {
                    response->
                productIdObserver.value = response.id
            }.addOnFailureListener{
                productIdObserver.value=""
            }
        }
        return productIdObserver
    }

    fun updateFirestore(myProduct: Lamp): LiveData<Boolean> {
        val stateUpdateObserver: MutableLiveData<Boolean> = MutableLiveData()
        firestore.collection(PRODUCT_COLLECTION).document(myProduct.id).set(myProduct).addOnSuccessListener {
            stateUpdateObserver.value=true
        }.addOnFailureListener{
            stateUpdateObserver.value=false
        }
        return stateUpdateObserver
    }

    fun deleteFirestore(myProduct: Lamp): LiveData<Boolean> {
        val stateDeleteObserver: MutableLiveData<Boolean> = MutableLiveData()
        firestore.collection(PRODUCT_COLLECTION).document(myProduct.id).delete().addOnSuccessListener {
            loadAllFirestore()
            stateDeleteObserver.value=true
        }.addOnFailureListener{
            stateDeleteObserver.value=false
        }
        return stateDeleteObserver
    }

}