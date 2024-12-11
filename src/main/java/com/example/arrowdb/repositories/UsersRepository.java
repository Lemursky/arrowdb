package com.example.arrowdb.repositories;

import com.example.arrowdb.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    @Transactional(readOnly = true)
    Optional<Users> findUsersByUserName(String name);

    @Transactional(readOnly = true)
    @Query(nativeQuery = true, value = "select user_status_enum from users where username=:name")
    int findStatusByUserName(String name);

}
