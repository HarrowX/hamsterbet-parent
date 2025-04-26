package xyz.alexandrit.common.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import xyz.alexandrit.common.enums.HamsterStatus;

@lombok.Data
public class HamsterUpdateRequestDTO {
    @NotBlank
    private String  name;
    @Positive
    private Double weightInGrams;
    @Positive @Max(10L)
    private Integer age;
    private HamsterStatus status;
}
