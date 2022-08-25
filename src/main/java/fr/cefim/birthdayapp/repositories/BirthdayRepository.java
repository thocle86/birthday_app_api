package fr.cefim.birthdayapp.repositories;

import fr.cefim.birthdayapp.entities.Birthday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BirthdayRepository extends JpaRepository<Birthday, Long> {

    List<Birthday> findBirthdaysByUserId(Long id);

    Optional<Birthday> findBirthdayByUserIdAndId(Long userId, Long id);

}
