package fr.cefim.birthdayapp.repositories;

import fr.cefim.birthdayapp.entities.Role;
import fr.cefim.birthdayapp.models.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleEnum name);

}