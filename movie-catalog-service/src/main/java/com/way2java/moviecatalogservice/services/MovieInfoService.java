package com.way2java.moviecatalogservice.services;

import com.way2java.moviecatalogservice.models.CatalogItem;
import com.way2java.moviecatalogservice.models.MovieInfo;
import com.way2java.moviecatalogservice.models.Rating;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfoService {

    private final RestTemplate restTemplate;

    public MovieInfoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

//    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
    public CatalogItem getCatalogItem(Rating rating) {
        MovieInfo movieInfo = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), MovieInfo.class);
        return new CatalogItem(movieInfo.getTitle(), movieInfo.getOverview(), rating.getRating());
    }

    private CatalogItem getFallbackCatalogItem(Rating rating) {
        return new CatalogItem("No movie", "", rating.getRating());
    }
}
