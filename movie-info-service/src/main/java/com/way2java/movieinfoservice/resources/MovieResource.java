package com.way2java.movieinfoservice.resources;

import com.way2java.movieinfoservice.models.MovieInfo;
import com.way2java.movieinfoservice.models.MovieSummary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/movies")
public class MovieResource {

    @Value("${api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public MovieResource(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{movieId}")
    public MovieInfo getMovieInfo(@PathVariable("movieId") String movieId) {
        MovieSummary movieSummary =
                restTemplate.getForObject("https://api.themoviedb.org/3/movie/550" + movieId + "?api_key=" + apiKey, MovieSummary.class);

        return new MovieInfo(movieId, movieSummary.getTitle(), movieSummary.getOverview());
    }

}
