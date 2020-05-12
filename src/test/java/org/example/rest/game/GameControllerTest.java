package org.example.rest.game;

import com.google.gson.Gson;
import org.example.application.App;
import org.example.model.User;
import org.example.model.dto.HighscoresDTO;
import org.example.model.dto.ScoreDTO;
import org.example.rest.EndpointUrls;
import org.example.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = App.class)
@AutoConfigureMockMvc
class GameControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    private ScoreDTO scoreDTO = new ScoreDTO(1, 5);

    @Test
    void shouldPostUserScore() throws Exception {
        this.mockMvc.perform(
                post(EndpointUrls.SCORE)
                        .content(new Gson().toJson(scoreDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isAccepted());

    }

    @Test
    void shouldGetUserPosition() throws Exception {
        User user = new User(1, 5, 5);
        when(gameService.getPosition(1l)).thenReturn(user);

        this.mockMvc.perform(
                get(EndpointUrls.USER_POSITION, 1)
        )
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void shouldGetHighscoreList() throws Exception {
        HighscoresDTO highscores = new HighscoresDTO(new ArrayList<>());
        when(gameService.getHighscores()).thenReturn(highscores);

        this.mockMvc.perform(
                get(EndpointUrls.HIGHSCORELIST)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

}