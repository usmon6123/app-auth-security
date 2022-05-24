package uz.yengilyechim.sign_in_sign_up.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yengilyechim.sign_in_sign_up.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByName(String name);
}
