package io.github.gianpamx.solid.unsplash

import io.github.gianpamx.solid.ItemsRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UnsplashRepository(private val service: UnsplashService, private val authorization: String) : ItemsRepository {
    override fun getItems(callback: (ItemsRepository.Result) -> Unit) {
        service.photos(authorization).enqueue(object : Callback<List<UnspashPhoto>?> {
            override fun onResponse(call: Call<List<UnspashPhoto>?>, response: Response<List<UnspashPhoto>?>) {
                val body = response.body()
                if (body == null) {
                    callback.invoke(ItemsRepository.Result.Failure(Exception("Error")))
                    return
                }

                callback.invoke((ItemsRepository.Result.Success(body.map { it.urls["small"] ?: "" })))
            }

            override fun onFailure(call: Call<List<UnspashPhoto>?>, t: Throwable) {
                callback.invoke(ItemsRepository.Result.Failure(t))
            }
        })
    }
}
