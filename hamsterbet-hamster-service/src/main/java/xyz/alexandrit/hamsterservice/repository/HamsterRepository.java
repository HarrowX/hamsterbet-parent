package xyz.alexandrit.hamsterservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.alexandrit.hamsterservice.entity.Hamster;


@Repository
public interface HamsterRepository extends JpaRepository<Hamster, Long> {

}
