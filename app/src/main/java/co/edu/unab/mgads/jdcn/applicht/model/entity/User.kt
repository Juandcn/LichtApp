package co.edu.unab.mgads.jdcn.applicht.model.entity

import com.google.firebase.firestore.Exclude

open class User(
    @JvmField @Exclude
    var id:String,
    var name:String="",
    var document:String="",
    var email:String="") {
}