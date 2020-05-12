package org.example.service;

import org.example.application.App;
import org.example.model.User;
import org.example.model.dto.ScoreDTO;
import org.example.rest.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
class GameServiceTest {
    @Autowired
    private GameService gameService;

    @Test
    void shouldPostValidScore() {
        ScoreDTO scoreDTO = new ScoreDTO(1, 5);
        User result = gameService.postScore(scoreDTO);
        assertEquals(1, result.getUserId());
        assertEquals(5, result.getScore());
    }

    @Test
    void shouldSumScoreOfUser() {
        ScoreDTO scoreDTO = new ScoreDTO(2, 4);
        gameService.postScore(scoreDTO);

        User result = gameService.postScore(scoreDTO);
        assertEquals(2, result.getUserId());
        assertEquals(8, result.getScore());
    }

    @Test
    void shouldThrowUserNotFoundExceptionToInexistingUser() {
        assertThrows(UserNotFoundException.class, () -> gameService.getPosition(0L));
    }

    @Test
    void getHighscores() {
    }
}