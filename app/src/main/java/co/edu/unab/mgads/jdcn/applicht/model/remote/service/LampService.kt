package co.edu.unab.mgads.jdcn.applicht.model.remote.service

import co.edu.unab.mgads.jdcn.applicht.model.entity.Lamp
import retrofit2.Call
import retrofit2.http.*

interface LampService {

    @GET("lamp.json")
    fun getAll(): Call<Map<String, Lamp>>

    @GET("lamp/{id}.json")
    fun getById(@Path("id")id:String): Call<Lamp>

    @POST("lamp.json")
    fun add(@Body product: Lamp): Call<Map<String, String>>

    @PUT("lamp/{id}.json")
    fun update(@Path("id") id: String, @Body product: Lamp): Call<Lamp>

    @DELETE("lamp/{id}.json")
    fun delete(@Path("id") id: String): Call<Unit>
}
