package com.pkopteva.filmsearchservice

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class FilmServiceConfiguration {

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }
}