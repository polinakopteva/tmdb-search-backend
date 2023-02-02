package com.pkopteva.filmsearchservice

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class FilmController(val filmService: FilmService) {

    @GetMapping("/search")
    fun filmSearch(@RequestParam(name = "query") q: String,
                   @RequestParam(name = "type") type: String): ResponseEntity<List<Film>> {
        val result = filmService.search(q, type) ?: return ResponseEntity.badRequest().build()
        return ResponseEntity.ok(result)
    }
}