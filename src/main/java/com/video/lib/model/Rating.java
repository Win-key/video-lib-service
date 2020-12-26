package com.video.lib.model;

import org.springframework.lang.NonNull;

public enum Rating {

    VERY_GOOD(1),
    GOOD(2),
    MEDIOCRE(3),
    BAD(4),
    VERY_BAD(5);

    private int value;

    Rating(int value) {
        this.value = value;
    }
    public int getValue(){
        return this.value;
    }

    public static Rating of(@NonNull int value){
        for (Rating rating : Rating.values()){
            if(rating.getValue() == value){
                return rating;
            }
        }

        return null;
    }
}
