package co.edu.unab.mgads.jdcn.applicht.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import co.edu.unab.mgads.jdcn.applicht.model.entity.User
import co.edu.unab.mgads.jdcn.applicht.model.repository.UserRepository

class SingUpActivityViewModel : ViewModel(){
    var user: User = User("","","","")
    var password:String="12345"
    private val userRepository = UserRepository()

    fun singUp(): LiveData<User?> {
        return userRepository.singUp(user, password)
    }
}