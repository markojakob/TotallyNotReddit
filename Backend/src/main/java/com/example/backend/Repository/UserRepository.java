package com.example.backend.Repository;

import com.example.backend.Model.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsernameAndIdNot(@NotNull String username, Long id);

    boolean existsByEmailAndIdNot(String email, Long id);
    
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User findByUsername(String username);
}
