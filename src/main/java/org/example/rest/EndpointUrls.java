package org.example.rest;

public class EndpointUrls {
    public static final String SCORE = "/score";
    public static final String USER_POSITION = "/{userId}/position";
    public static final String HIGHSCORELIST = "/highscorelist";

    private EndpointUrls(){
        throw new IllegalStateException("Utility class!");
    }
}
