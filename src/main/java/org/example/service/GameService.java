package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.model.User;
import org.example.model.dto.HighscoresDTO;
import org.example.model.dto.ScoreDTO;
import org.example.rest.exception.ScoreInvalidException;
import org.example.rest.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GameService {
    private static final int MAX_HIGHSCORE = 20000;
    private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
    private ConcurrentMap<Long, User> userMap = new ConcurrentHashMap<>();

    public void postScore(ScoreDTO scoreDTO) {
        if (scoreDTO == null) {
            throw new ScoreInvalidException();
        }
        executor.submit(() -> {
            User user = userMap.getOrDefault(scoreDTO.getUserId(), new User(scoreDTO.getUserId(), 0, 0));
            user.setScore(user.getScore() + scoreDTO.getPoints());
            log.debug(user.toString());

            userMap.put(scoreDTO.getUserId(), user);
        });
    }

    public User getPosition(Long userId) {
        User user = userMap.get(userId);
        if (user == null) {
            throw new UserNotFoundException(userId);
        }
        return user;
    }

    public HighscoresDTO getHighscores() {
        List<User> high = new ArrayList<>(userMap.values());

        Collections.sort(high, Comparator.comparingLong(User::getScore).reversed());

        return new HighscoresDTO(high.stream().limit(MAX_HIGHSCORE).collect(Collectors.toList()));
    }

    public User deleteUser(long userId) {
        return userMap.remove(userId);
    }
}
