package com.maverick.moviecatalog.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.maverick.moviecatalog.model.CatalogItem;
import com.maverick.moviecatalog.model.Movie;
import com.maverick.moviecatalog.model.Rating;
import com.maverick.moviecatalog.model.UserRating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * MovieCatalogResource
 */
@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        UserRating userRating = restTemplate.getForObject("http://rating-data-service/ratingsdata/users/" + userId, UserRating.class);

        return userRating.getRatingList().stream().map(rating -> {
            Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
            // Movie movie =
            // webClientBuilder.build().get().uri("http://localhost:8081/movies/" +
            // rating.getMovieId())
            // .retrieve().bodyToMono(Movie.class).block();
            return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
        }).collect(Collectors.toList());

        // List<CatalogItem> movieList = new ArrayList<>();
        // movieList.add(new CatalogItem("LOTR", "Story of Middle Earth", 9));
        // movieList.add(new CatalogItem("INCEPTION", "Story of Dreams", 8));

        // return movieList;
    }
}