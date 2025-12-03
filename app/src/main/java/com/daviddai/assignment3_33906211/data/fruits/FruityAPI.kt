package com.daviddai.assignment3_33906211.data.fruits

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import kotlin.jvm.java

// Reference Week 7 Lab
/**
 * This is the Interface for the FruityAPI
 */
interface FruityAPI {
    @GET("api/fruit/{name}")
    suspend fun getFruitByName(
        @Path("name") name: String
    ): Response<Fruit>

    companion object {

        var BASE_URL = "https://www.fruityvice.com/"

        fun create(): FruityAPI {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(FruityAPI::class.java)

        }
    }


}