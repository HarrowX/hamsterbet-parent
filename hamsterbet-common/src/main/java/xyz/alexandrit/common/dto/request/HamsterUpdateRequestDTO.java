package xyz.alexandrit.common.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@lombok.Data
public class HamsterUpdateRequestDTO {
    @NotBlank
    private String  name;
    @Positive
    private Double  weight;
    @Min(1L) @Max(5L)
    private Integer strength;
    @Positive @Max(10L)
    private Integer age;
}
