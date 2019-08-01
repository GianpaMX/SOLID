package io.github.gianpamx.solid.marvel

import io.github.gianpamx.solid.ItemsRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest

class MarvelRepository(
    private val service: MarvelService,
    private val publicKey: String,
    private val privateKey: String
) : ItemsRepository {
    override fun getItems(callback: (ItemsRepository.Result) -> Unit) {
        val ts = System.currentTimeMillis().toString()
        val hash = "$ts$privateKey$publicKey".md5()

        service.characters(publicKey, ts, hash).enqueue(object : Callback<MarvelResponse?> {
            override fun onResponse(call: Call<MarvelResponse?>, response: Response<MarvelResponse?>) {
                val results = response.body()?.data?.results
                if (results == null) {
                    callback.invoke(ItemsRepository.Result.Failure(Exception("Error")))
                    return
                }

                callback.invoke(ItemsRepository.Result.Success(results.map { "${it.thumbnail.path}.${it.thumbnail.extension}" }))
            }

            override fun onFailure(call: Call<MarvelResponse?>, t: Throwable) {
                t.printStackTrace()
                callback.invoke(ItemsRepository.Result.Failure(t))
            }
        })
    }

    fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        val digested = md.digest(toByteArray())
        return digested.joinToString("") {
            String.format("%02x", it)
        }
    }
}
