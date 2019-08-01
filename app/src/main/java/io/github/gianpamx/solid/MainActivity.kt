package io.github.gianpamx.solid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import io.github.gianpamx.solid.marvel.MarvelRepository
import io.github.gianpamx.solid.marvel.MarvelService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private val mainAdapter = MainAdapter()

    lateinit var itemsRepository: ItemsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = GridLayoutManager(this, 2)

        recyclerView.adapter = mainAdapter

        inject()

        itemsRepository.getItems {
            when (it) {
                is ItemsRepository.Result.Success -> mainAdapter.submitItems(it.items)
                is ItemsRepository.Result.Failure -> Snackbar.make(
                    rootView,
                    it.t.message ?: "Error",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

//    private fun inject() {
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://api.unsplash.com/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        val service = retrofit.create(UnsplashService::class.java)
//        itemsRepository = UnsplashRepository(service, "Client-ID ${BuildConfig.UNSPLASH_ACCESS_KEY}")
//    }

    private fun inject() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://gateway.marvel.com:443/v1/public/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(MarvelService::class.java)
        itemsRepository = MarvelRepository(service, BuildConfig.MARVEL_PUBLIC_KEY, BuildConfig.MARVEL_PRIVATE_KEY)
    }
}
