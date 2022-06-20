package co.edu.unab.mgads.jdcn.applicht.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import co.edu.unab.mgads.jdcn.applicht.model.entity.Lamp
import co.edu.unab.mgads.jdcn.applicht.model.repository.LampRepository

class ListLampActivityViewModel (application: Application): AndroidViewModel(application) {

    private val lampRepository:LampRepository= LampRepository(application)
    var products: LiveData<List<Lamp>> = lampRepository.lamps

    fun deleteLamps(myProduct: Lamp): LiveData<Boolean> {
        return lampRepository.deleteFirestore(myProduct)
    }

    fun loadLamps(){
        lampRepository.loadAllFirestore()
    }
}