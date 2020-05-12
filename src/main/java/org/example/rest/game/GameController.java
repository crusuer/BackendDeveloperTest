package org.example.rest.game;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.example.model.User;
import org.example.model.dto.HighscoresDTO;
import org.example.model.dto.ScoreDTO;
import org.example.rest.EndpointUrls;
import org.example.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@Api(value = "Game", tags = {"game"})
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping(path = EndpointUrls.USER_POSITION)
    @ApiOperation(value = "Retrieve user position by id")
    public User getUserPosition(@PathVariable Long userId) {
        return gameService.getPosition(userId);
    }

    @PostMapping(path = EndpointUrls.SCORE)
    @ApiOperation(value = "Post user position by id")
    public ResponseEntity<Void> postUserScore(@Valid @RequestBody ScoreDTO scoreDTO) {
        gameService.postScore(scoreDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping(path = EndpointUrls.HIGHSCORELIST)
    @ApiOperation(value = "Retrieve a list of highest scores")
    public HighscoresDTO getHighestScores() {
        return gameService.getHighscores();
    }

}
