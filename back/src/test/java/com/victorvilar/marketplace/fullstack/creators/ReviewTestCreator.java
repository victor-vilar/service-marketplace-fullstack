package com.victorvilar.marketplace.fullstack.creators;

import com.victorvilar.marketplace.fullstack.domain.Review;
import com.victorvilar.marketplace.fullstack.enums.Reviewer;

import java.util.UUID;

public abstract class ReviewTestCreator {

    public static Review criarReviewDoCustomer(){
        Review review = new Review();
        review.setId(UUID.randomUUID());
        review.setRating(5);
        review.setCommentary("Otimo");
        review.setReviewer(Reviewer.CLIENTE);
        return review;
    }

    public static Review criarReviewDoProvider(){
        Review review = new Review();
        review.setId(UUID.randomUUID());
        review.setRating(5);
        review.setCommentary("Otimo");
        review.setReviewer(Reviewer.PROVEDOR);
        return review;
    }
}
