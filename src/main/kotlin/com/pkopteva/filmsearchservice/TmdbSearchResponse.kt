package com.pkopteva.filmsearchservice

import com.fasterxml.jackson.annotation.JsonProperty

data class TmdbSearchResponse(val page: Int, val results: List<TmdbEntity>?,
                              @JsonProperty("total_results") val totalResults: Int,
                              @JsonProperty("total_pages") val totalPages: Int)

data class TmdbEntity(@JsonProperty("release_date") val releaseDate: String?, val title: String?, @JsonProperty("first_air_date") val firstAirDate: String?, val name: String?)