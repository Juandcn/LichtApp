package co.edu.unab.mgads.jdcn.applicht.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import co.edu.unab.mgads.jdcn.applicht.model.entity.Lamp
import co.edu.unab.mgads.jdcn.applicht.model.repository.LampRepository

class DetailLampActivityViewModel (application: Application): AndroidViewModel(application) {

    private val lampRepository:LampRepository = LampRepository(application)
    lateinit var lamp: LiveData<Lamp>

    fun getProductByKey(lampKey : Int){
        val lampId: String = lampKey as String
        lampRepository.getByIdFirestore(lampId)
        lamp = lampRepository.lamp

    }

    fun getProductById(mylampId : String){
        lampRepository.getByIdFirestore(mylampId)
        lamp=lampRepository.lamp

    }
}