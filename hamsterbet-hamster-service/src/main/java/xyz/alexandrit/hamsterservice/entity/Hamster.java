package xyz.alexandrit.hamsterservice.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "hamsters")
public class Hamster {
    @Id
    @Column(name = "hamster_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double weight;
    private Integer strength;
    private Integer age;
}
