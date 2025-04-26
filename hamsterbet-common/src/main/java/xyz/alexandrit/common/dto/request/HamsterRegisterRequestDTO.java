package xyz.alexandrit.common.dto.request;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.lang.NonNull;

@lombok.Data
public class HamsterRegisterRequestDTO {
    @NotBlank @NonNull
    private String  name;
    @Positive @NonNull
    private Double weightInGrams;
    @Positive @Max(10L) @NonNull
    private Integer age;
}
