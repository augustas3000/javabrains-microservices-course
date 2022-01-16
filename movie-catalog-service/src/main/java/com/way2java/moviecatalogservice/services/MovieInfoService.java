package com.way2java.moviecatalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
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

//    BULKHEAD PATTERN
//    @HystrixCommand(
//            fallbackMethod = "getFallbackCatalogItem",
//            threadPoolKey = "movieInfoPool",
//            threadPoolProperties = {
//                    @HystrixProperty(name = "coreSize", value = "20"),
//                    @HystrixProperty(name = "maxQueueSize", value = "10")
//            },
//            commandProperties = {
//                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
//                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
//                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
//                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
//            })
    @HystrixCommand(
            fallbackMethod = "getFallbackCatalogItem",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
            })
    public CatalogItem getCatalogItem(Rating rating) {
        MovieInfo movieInfo = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), MovieInfo.class);
        return new CatalogItem(movieInfo.getTitle(), movieInfo.getOverview(), rating.getRating());
    }

    private CatalogItem getFallbackCatalogItem(Rating rating) {
        return new CatalogItem("No movie", "", rating.getRating());
    }
}
