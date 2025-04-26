package xyz.alexandrit.hamsterservice.entity;


import jakarta.persistence.*;
import lombok.Data;
import xyz.alexandrit.common.enums.HamsterStatus;

@Data
@Entity
@Table(name = "hamsters")
public class Hamster {
    @Id
    @Column(name = "hamster_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Double weightInGrams;
    @Column(nullable = false)
    private Integer age;

    @Column(name = "wins_count", nullable = false, columnDefinition = "integer default 0")
    private Integer winsCount;

    @Column(name = "losses_count", nullable = false, columnDefinition = "integer default 0")
    private Integer lossesCount;

    @Column(nullable = false, columnDefinition = "varchar default AVALIABLE")
    private HamsterStatus status;



    @PrePersist
    public void initCounts() {
        if (winsCount == null) winsCount = 0;
        if (lossesCount == null) lossesCount = 0;
        if (status == null) status = HamsterStatus.AVALIABLE;
    }
}
