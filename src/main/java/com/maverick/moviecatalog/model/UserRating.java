package com.maverick.moviecatalog.model;

import java.util.List;

/**
 * UserRating
 */
public class UserRating {

    private List<Rating> ratingList;

    public List<Rating> getRatingList() {
        return ratingList;
    }

    public void setRatingList(List<Rating> ratingList) {
        this.ratingList = ratingList;
    }
}