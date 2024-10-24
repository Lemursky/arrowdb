package com.example.arrowdb.repositories;

import com.example.arrowdb.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatusRepository extends JpaRepository<UserStatus, Integer> {
    UserStatus findUserStatusByStatAnnotation(String name);
}
