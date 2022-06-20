package co.edu.unab.mgads.jdcn.applicht.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import co.edu.unab.mgads.jdcn.applicht.model.entity.User
import co.edu.unab.mgads.jdcn.applicht.model.repository.UserRepository

class MainActivityViewModel: ViewModel() {

    var user: User = User("","","","")
    var password:String=""
    private val userRepository= UserRepository()

    fun login(): LiveData<User?> {
        return userRepository.login(user.email,password)
    }
}