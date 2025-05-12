package pl.zakrzewski.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zakrzewski.restapi.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);

}
