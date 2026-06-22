package edusale_server.repository;


import edusale_server.model.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    boolean existsByEmail(String email);
}