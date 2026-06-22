package edusale_server.service.impl;


import edusale_server.repository.JpaUserRepository;
import edusale_server.entity.UserEntity;
import edusale_server.model.User;
import edusale_server.repository.UserRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    
    private final JpaUserRepository jpaUserRepository;
    
    public UserRepositoryImpl(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }
    
    @Override
    public User save(User user) {
        UserEntity entity = toEntity(user);
        UserEntity saved = jpaUserRepository.save(entity);
        return toDomain(saved);
    }
    
    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email)
                .map(this::toDomain);
    }
    
    @Override
    public Optional<User> findById(Long id) {
        return jpaUserRepository.findById(id)
                .map(this::toDomain);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }
    
    private UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        entity.setName(user.getName());
        entity.setCreatedAt(user.getCreatedAt());
        return entity;
    }
    
    private User toDomain(UserEntity entity) {
        User user = new User();
        user.setId(entity.getId());
        user.setEmail(entity.getEmail());
        user.setPassword(entity.getPassword());
        user.setName(entity.getName());
        user.setCreatedAt(entity.getCreatedAt());
        return user;
    }
}