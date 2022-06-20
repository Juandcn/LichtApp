package co.edu.unab.mgads.jdcn.applicht.model.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import co.edu.unab.mgads.jdcn.applicht.model.LampStatus
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "Lamps")
data class Lamp(
        @PrimaryKey(autoGenerate = true)
        @JvmField @Exclude
        @Expose(serialize = false,deserialize = false)
        var key:Int?=null,
        @Ignore
        @JvmField @Exclude
        @Expose(serialize = false,deserialize = false)
        var id:String="",
        var name:String="",
        var location:String="",
        @JvmField @PropertyName("image")
        @SerializedName("image")
        var image:String = "https://media.admagazine.com/photos/618a6667a9f7fab6f0622eb2/master/w_1600%2Cc_limit/74440.jpg",
        var description:String="",
        var status: LampStatus = LampStatus.SWITCHOF
    ) : Serializable {
}