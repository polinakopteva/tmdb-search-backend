package com.pkopteva.filmsearchservice

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.util.stream.Collectors

@Service
class FilmService(val restTemplate: RestTemplate) {

    @Value("\${tmdb.api.key}")
    lateinit var tmdbApiKey: String

    @Value("\${tmdb.link.movie}")
    lateinit var tmdbLinkMovie: String

    @Value("\${tmdb.link.series}")
    lateinit var tmdbLinkSeries: String

    fun search(q: String, type: String): List<Film>? {
        val searchResult: List<Film>? =  when (type) {
            "movie" -> movieSearch(q)
            "series" -> seriesSearch(q)
            "all" -> allSearch(q)
            else -> null
        }
        return searchResult
    }

    fun movieSearch(q: String): List<Film> {
         val tmdbSearchResponse: TmdbSearchResponse? = restTemplate.getForObject(
            "${tmdbLinkMovie}?api_key={tmdbApiKey}&query={q}",
            TmdbSearchResponse::class.java, tmdbApiKey, q)

        val results: List<TmdbEntity> = tmdbSearchResponse?.results!!
        return results.stream()
            .map { Film(it.title, it.releaseDate, "movie") }
            .limit(5)
            .collect(Collectors.toList())
    }

    fun seriesSearch(q: String): List<Film> {
        val tmdbSearchResponse: TmdbSearchResponse? = restTemplate.getForObject(
            "${tmdbLinkSeries}?api_key={tmdbApiKey}&query={q}",
            TmdbSearchResponse::class.java, tmdbApiKey, q)

        val results: List<TmdbEntity> = tmdbSearchResponse?.results!!
        return results.stream()
            .map { Film(it.name, it.firstAirDate, "series") }
            .limit(5)
            .collect(Collectors.toList())
    }

    fun allSearch(q: String): List<Film> {
        val foundMovies = movieSearch(q)
        val foundSeries = seriesSearch(q)
        val result = mutableListOf<Film>()
        if (foundMovies.isNotEmpty()) {
            result.add(foundMovies[0])
        }
        if (foundSeries.isNotEmpty()) {
            result.add(foundSeries[0])
        }
        return result
    }
}