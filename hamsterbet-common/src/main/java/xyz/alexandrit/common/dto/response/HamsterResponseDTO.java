package xyz.alexandrit.common.dto.response;


import xyz.alexandrit.common.enums.HamsterStatus;

@lombok.Data
public class HamsterResponseDTO {

    private Long hamsterId;

    private String name;
    private Double weightInGrams;
    private Integer age;

    private Integer winsCount;
    private Integer lossesCount;

    private HamsterStatus status;
}
