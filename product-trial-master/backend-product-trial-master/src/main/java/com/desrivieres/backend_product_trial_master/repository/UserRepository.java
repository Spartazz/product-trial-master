package com.desrivieres.backend_product_trial_master.repository;

import com.desrivieres.backend_product_trial_master.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Trouve un utilisateur par son email
     * @param email l'email de l'utilisateur
     * @return Optional contenant l'utilisateur s'il existe
     */
    Optional<User> findByEmail(String email);

}
