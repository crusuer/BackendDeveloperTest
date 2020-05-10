package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.model.User;
import org.example.model.dto.HighscoresDTO;
import org.example.model.dto.ScoreDTO;
import org.example.rest.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@Slf4j
public class GameService {
    private ConcurrentMap<Long, User> userMap = new ConcurrentHashMap<>();

    public User postScore(ScoreDTO scoreDTO) {
        User user = userMap.getOrDefault(scoreDTO.getUserId(), new User(scoreDTO.getUserId(), 0, 0));
        user.setScore(user.getScore() + scoreDTO.getPoints());
        log.debug(user.toString());

        return userMap.put(scoreDTO.getUserId(), user);
    }

    public User getPosition(Long userId) {
        User user = userMap.get(userId);
        if (user == null) {
            throw new UserNotFoundException(userId);
        }
        return user;
    }

    public HighscoresDTO getHighscores() {
        return new HighscoresDTO();
    }
}
