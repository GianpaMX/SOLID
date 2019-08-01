package io.github.gianpamx.solid.unsplash

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface UnsplashService {
    @GET("photos")
    fun photos(@Header("Authorization") authorization: String?): Call<List<UnspashPhoto>>
}
