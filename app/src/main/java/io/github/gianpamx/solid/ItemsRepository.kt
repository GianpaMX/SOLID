package io.github.gianpamx.solid

interface ItemsRepository {
    sealed class Result {
        class Success(val items: List<String>) : Result()
        class Failure(val t: Throwable) : Result()
    }

    fun getItems(callback: (Result) -> Unit)
}
