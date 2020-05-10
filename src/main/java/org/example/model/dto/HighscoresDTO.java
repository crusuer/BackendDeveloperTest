package org.example.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.model.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class HighscoresDTO {
    private List<User> highscoresList = new ArrayList<>();
}
