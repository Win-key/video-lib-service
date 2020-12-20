package com.video.lib.model;

import org.springframework.lang.NonNull;

public enum Rating {

    VERY_GOOD(0),
    GOOD(1),
    MEDIOCRE(2),
    BAD(3),
    VERY_BAD(4);

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
