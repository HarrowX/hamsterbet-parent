package xyz.alexandrit.common.dto.request;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.lang.NonNull;

@lombok.Data
public class HamsterRegisterRequestDTO {
    @NotBlank @NonNull
    private String  name;
    @Positive @NonNull
    private Double  weight;
    @Min(1L) @Max(5L) @NonNull
    private Integer strength;
    @Positive @Max(10L) @NonNull
    private Integer age;
}
