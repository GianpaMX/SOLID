package io.github.gianpamx.solid.marvel

data class MarvelThumbnail(val path: String, val extension: String)
data class MarvelCharacter(val thumbnail: MarvelThumbnail)
data class MarvelData(val results: List<MarvelCharacter>)
data class MarvelResponse(
    val data: MarvelData
)
