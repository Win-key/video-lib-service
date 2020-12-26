package com.video.lib.model;

public enum UserRole {
    ADMIN, USER;

    public static UserRole of(String value){
        for (UserRole userRole : UserRole.values()){
            if(userRole.toString().equals(value)){
                return userRole;
            }
        }
        return null;
    }

    public static UserRole ifAdmin(boolean isAdmin){
        return isAdmin ? ADMIN : USER;
    }
}
