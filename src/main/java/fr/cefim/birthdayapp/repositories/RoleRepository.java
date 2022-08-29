package fr.cefim.birthdayapp.repositories;

import fr.cefim.birthdayapp.entities.Role;
import fr.cefim.birthdayapp.models.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(RoleEnum name);

}