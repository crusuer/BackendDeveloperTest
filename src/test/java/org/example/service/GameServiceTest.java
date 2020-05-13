package org.example.service;

import org.example.application.App;
import org.example.model.User;
import org.example.model.dto.HighscoresDTO;
import org.example.model.dto.ScoreDTO;
import org.example.rest.exception.ScoreInvalidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
class GameServiceTest {
    @Autowired
    private GameService gameService;

    @BeforeEach
    void setUp() throws Exception {
        gameService.clearUsers();
    }

    @Test
    void shouldPostValidScore() throws Exception {
        ScoreDTO scoreDTO = new ScoreDTO(1, 5);
        gameService.postScore(scoreDTO);
        Thread.sleep(500);

        User result = gameService.getPosition(scoreDTO.getUserId());
        assertNotNull(result);
        assertEquals(1, result.getUserId());
        assertEquals(5, result.getScore());
        assertEquals(1, result.getPosition());
    }

    @Test
    void shouldNotPostInvalidScore() {
        assertThrows(ScoreInvalidException.class, () -> gameService.postScore(null));
    }

    @Test
    void shouldSumScoreOfUser() throws Exception {
        ScoreDTO scoreDTO = new ScoreDTO(2, 4);
        gameService.postScore(scoreDTO);
        gameService.postScore(scoreDTO);
        Thread.sleep(500);

        User result = gameService.getPosition(scoreDTO.getUserId());
        assertNotNull(result);
        assertEquals(2, result.getUserId());
        assertEquals(8, result.getScore());
        assertEquals(1, result.getPosition());
    }

    @Test
    void shouldInvertUserPosition() throws Exception {
        ScoreDTO scoreDTO = new ScoreDTO(1, 4);
        ScoreDTO scoreDTO2 = new ScoreDTO(2, 5);
        gameService.postScore(scoreDTO);
        gameService.postScore(scoreDTO2);
        Thread.sleep(500);

        User result = gameService.getPosition(scoreDTO.getUserId());
        assertEquals(1, result.getUserId());
        assertEquals(2, result.getPosition());

        gameService.postScore(scoreDTO);
        Thread.sleep(500);

        User result2 = gameService.getPosition(scoreDTO.getUserId());
        assertEquals(1, result2.getUserId());
        assertEquals(1, result2.getPosition());
    }

    @Test
    void shouldGetEmptyHighscoreList() {
        HighscoresDTO highscoresDTO = gameService.getHighscores();
        List<User> result = highscoresDTO.getHighscoresList();
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldGetHighscoreList() throws Exception {
        ScoreDTO scoreMin = new ScoreDTO(5, 50);
        ScoreDTO scoreMed = new ScoreDTO(6, 100);
        ScoreDTO scoreMax = new ScoreDTO(7, 200);

        gameService.postScore(scoreMin);
        gameService.postScore(scoreMed);
        gameService.postScore(scoreMax);

        Thread.sleep(3000);

        HighscoresDTO highscoresDTO = gameService.getHighscores();
        List<User> result = highscoresDTO.getHighscoresList();

        assertEquals(3, result.size());

        assertEquals(7, result.get(0).getUserId());
        assertEquals(200, result.get(0).getScore());
        assertEquals(1, result.get(0).getPosition());

        assertEquals(6, result.get(1).getUserId());
        assertEquals(100, result.get(1).getScore());
        assertEquals(2, result.get(1).getPosition());

        assertEquals(5, result.get(2).getUserId());
        assertEquals(50, result.get(2).getScore());
        assertEquals(3, result.get(2).getPosition());
    }
}