package org.example.service;

import org.example.application.App;
import org.example.model.User;
import org.example.model.dto.HighscoresDTO;
import org.example.model.dto.ScoreDTO;
import org.example.rest.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
class GameServiceTest {
    @Autowired
    private GameService gameService;

    @Test
    void shouldPostValidScore() throws Exception {
        ScoreDTO scoreDTO = new ScoreDTO(1, 5);
        gameService.postScore(scoreDTO);
        Thread.sleep(500);

        User result = gameService.getPosition(scoreDTO.getUserId());
        assertEquals(1, result.getUserId());
        assertEquals(5, result.getScore());

        gameService.deleteUser(scoreDTO.getUserId());
    }

    @Test
    void shouldSumScoreOfUser() throws Exception {
        ScoreDTO scoreDTO = new ScoreDTO(2, 4);
        gameService.postScore(scoreDTO);
        gameService.postScore(scoreDTO);
        Thread.sleep(500);

        User result = gameService.getPosition(scoreDTO.getUserId());
        assertEquals(2, result.getUserId());
        assertEquals(8, result.getScore());

        gameService.deleteUser(scoreDTO.getUserId());
    }

    @Test
    void shouldThrowUserNotFoundExceptionToNonExistingUser() {
        assertThrows(UserNotFoundException.class, () -> gameService.getPosition(0L));
    }

    @Test
    void shouldGetHighscoreList() throws Exception {
        ScoreDTO scoreMin = new ScoreDTO(5, 50);
        ScoreDTO scoreMed = new ScoreDTO(6, 100);
        ScoreDTO scoreMax = new ScoreDTO(7, 200);

        gameService.postScore(scoreMin);
        gameService.postScore(scoreMed);
        gameService.postScore(scoreMax);
        Thread.sleep(500);

        HighscoresDTO highscoresDTO = gameService.getHighscores();
        List<User> result = highscoresDTO.getHighscoresList();

        assertEquals(7, result.get(0).getUserId());
        assertEquals(200, result.get(0).getScore());
        assertEquals(6, result.get(1).getUserId());
        assertEquals(100, result.get(1).getScore());
        assertEquals(5, result.get(2).getUserId());
        assertEquals(50, result.get(2).getScore());

        gameService.deleteUser(scoreMin.getUserId());
        gameService.deleteUser(scoreMed.getUserId());
        gameService.deleteUser(scoreMax.getUserId());
    }
}