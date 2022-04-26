package in.adwait.website.services;

import in.adwait.website.models.security.SimpleUserDetails;
import in.adwait.website.models.User;
import in.adwait.website.repositories.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository repository;
    @Getter
    private User user;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<User> users = repository.findByUsername(username);

        if(users.isEmpty()) throw new UsernameNotFoundException("User with username \"" + username + "\" not Found");
        else this.user = users.get(0);

        return new SimpleUserDetails(user);
    }
}
