package org.example.model.dto;

import lombok.Data;
import org.example.model.User;

import java.util.List;

@Data
public class HighscoresDTO {
    private final List<User> highscoresList;
}
