package com.way2java.moviecatalogservice.resources;

import com.way2java.moviecatalogservice.models.CatalogItem;
import com.way2java.moviecatalogservice.models.UserRating;
import com.way2java.moviecatalogservice.services.MovieInfoService;
import com.way2java.moviecatalogservice.services.UserRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    private final RestTemplate restTemplate;
    private final MovieInfoService movieInfoService;
    private final UserRatingService userRatingService;

    @Autowired
    public MovieCatalogResource(RestTemplate restTemplate, MovieInfoService movieInfoService, UserRatingService userRatingService) {
        this.restTemplate = restTemplate;
        this.movieInfoService = movieInfoService;
        this.userRatingService = userRatingService;
    }

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
        UserRating ratings = userRatingService.getUserRatings(userId);
        return ratings.getRatings().stream().map(movieInfoService::getCatalogItem).collect(Collectors.toList());
    }

}
