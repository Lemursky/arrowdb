package com.example.arrowdb.repositories;

import com.example.arrowdb.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    @Transactional(readOnly = true)
    Optional<Users> findByUserName (String name);
}
