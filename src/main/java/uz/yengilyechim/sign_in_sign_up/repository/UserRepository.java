package uz.yengilyechim.sign_in_sign_up.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yengilyechim.sign_in_sign_up.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
