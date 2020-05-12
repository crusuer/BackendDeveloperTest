package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.model.User;
import org.example.model.dto.ScoreDTO;

import java.util.concurrent.ConcurrentMap;

@Slf4j
public class PostScoreThread implements Runnable {
    private final ConcurrentMap<Long, User> userMap;
    private final ScoreDTO scoreDTO;

    public PostScoreThread(ConcurrentMap<Long, User> userMap, ScoreDTO scoreDTO) {
        this.userMap = userMap;
        this.scoreDTO = scoreDTO;
    }

    @Override
    public void run() {
        User user = userMap.getOrDefault(scoreDTO.getUserId(), new User(scoreDTO.getUserId(), 0, 0));
        user.setScore(user.getScore() + scoreDTO.getPoints());
        log.debug(user.toString());
    }
}
