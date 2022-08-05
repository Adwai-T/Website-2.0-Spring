package in.adwait.website.repositories;

import in.adwait.website.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUsername(String username);

    Optional<User> findById(Long id);
}
