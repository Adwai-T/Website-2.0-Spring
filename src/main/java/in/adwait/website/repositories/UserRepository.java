package in.adwait.website.repositories;

import in.adwait.website.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findByUsername(String username);

    User findById(long id);
}
