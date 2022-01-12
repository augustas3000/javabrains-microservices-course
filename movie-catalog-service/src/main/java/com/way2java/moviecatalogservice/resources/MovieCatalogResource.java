package com.way2java.moviecatalogservice.resources;

import com.way2java.moviecatalogservice.models.CatalogItem;
import com.way2java.moviecatalogservice.models.Movie;
import com.way2java.moviecatalogservice.models.Rating;
import com.way2java.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    private final RestTemplate restTemplate;

    @Autowired
    public MovieCatalogResource(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        //get all rated movie IDs - hardcode for now
        //assume thois is a response from rating data service (rating service, for this userId, give me all the ratings)
        UserRating ratings = restTemplate.getForObject("http://localhost:8083/ratingsdata/user/" + userId, UserRating.class);

        //for each movie ID of each rating, call movie info service and get details
        return ratings.getRatings().stream().map(rating -> {
            Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);
            return new CatalogItem(movie.getName(), "Hardcoded description", rating.getRating());
        }).collect(Collectors.toList());
    }

}
