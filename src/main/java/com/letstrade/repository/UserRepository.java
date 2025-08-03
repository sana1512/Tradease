package com.letstrade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.letstrade.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

    User findByEmail(String email);
}
