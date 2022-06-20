package co.edu.unab.mgads.jdcn.applicht.viewModel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import co.edu.unab.mgads.jdcn.applicht.model.entity.Lamp
import co.edu.unab.mgads.jdcn.applicht.model.repository.LampRepository

class FormLampActivityViewModel (application: Application): AndroidViewModel(application) {

    private val lampRepository:LampRepository= LampRepository(application)
    var lamp= Lamp(name = "")

    fun add(myLamp: Lamp,photoUri: Uri?): LiveData<String> {
        return lampRepository.addFirestore(myLamp,photoUri)
    }

    fun update(myLamp: Lamp): LiveData<Boolean> {
        return lampRepository.updateFirestore(myLamp)
    }

    fun getImage(urlString:String){
    }

}