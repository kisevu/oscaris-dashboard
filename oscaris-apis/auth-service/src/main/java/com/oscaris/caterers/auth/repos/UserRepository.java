package com.oscaris.caterers.auth.repos;

import com.oscaris.caterers.auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Author: kev.Ameda
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
}
