package org.example.model.dto;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class ScoreDTO {
    @Min(0)
    private final long userId;
    @Min(0)
    private final long points;
}
