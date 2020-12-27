package com.video.lib.model;

import org.springframework.lang.NonNull;

public enum Rating {

    VERY_BAD(0),
    BAD(1),
    MEDIOCRE(2),
    GOOD(3),
    VERY_GOOD(4);

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
