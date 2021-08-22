package engine.service;

import engine.exception.UserAlreadyExistsException;
import engine.model.User;
import engine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository repository, BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Not found: " + email));
    }

    public void save(User user) {
        repository.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new UserAlreadyExistsException(u.getEmail());
        });

        repository.save(User.builder()
                .email(user.getEmail())
                .password(encoder.encode(user.getPassword()))
                .roles("ROLE_USER")
                .build()
        );
    }
}
