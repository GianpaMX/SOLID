package io.github.gianpamx.solid.marvel

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelService {
    @GET("characters")
    fun characters(
        @Query("apikey") apikey: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String
    ): Call<MarvelResponse>
}
