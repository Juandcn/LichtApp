package co.edu.unab.mgads.jdcn.applicht.model.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class LichtAppAPI {
    companion object {
        private var instance: Retrofit? = null
        private const val url: String = "https://storeapp2022-292ae-default-rtdb.firebaseio.com/"

        fun getInstance(): Retrofit? {
            if (instance == null) {
                instance = Retrofit.Builder().baseUrl(url).addConverterFactory(
                    GsonConverterFactory.create())
                    .build()
            }
            return instance
        }
    }
}
