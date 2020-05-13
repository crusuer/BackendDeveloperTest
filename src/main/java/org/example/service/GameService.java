package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.model.User;
import org.example.model.dto.HighscoresDTO;
import org.example.model.dto.ScoreDTO;
import org.example.rest.exception.ScoreInvalidException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Vector;
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
    private Vector<User> userVector = new Vector<>();

    private static Vector<User> sortByValues(ConcurrentMap<Long, User> map) {
        Vector<User> sortedValues = new Vector<>(map.values());
        sortedValues.sort(Comparator.comparing(User::getScore).reversed());
        for (int i = 0; i < sortedValues.size(); i++) {
            sortedValues.get(i).setPosition(i + 1L);
        }
        return sortedValues;
    }

    public void postScore(ScoreDTO scoreDTO) {
        if (scoreDTO == null) {
            throw new ScoreInvalidException();
        }

        executor.submit(() -> {
            User user = userMap.getOrDefault(scoreDTO.getUserId(), new User(scoreDTO.getUserId(), 0, 0));
            user.setScore(user.getScore() + scoreDTO.getPoints());
            log.debug(user.toString());

            userMap.put(scoreDTO.getUserId(), user);
            userVector = sortByValues(userMap);
        });
    }

    public User getPosition(Long userId) {
        return userMap.get(userId);
    }

    public HighscoresDTO getHighscores() {
        return new HighscoresDTO(userVector.stream().limit(MAX_HIGHSCORE).collect(Collectors.toList()));
    }

    public void clearUsers() {
        userVector.clear();
        userMap.clear();
    }
}