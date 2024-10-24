package com.example.arrowdb.services;

import com.example.arrowdb.entity.UserStatus;

import java.util.List;

public interface UserStatusService {
    UserStatus findStatusById(Integer id);
    List<UserStatus> findAllUserStatus();
    UserStatus findUserStatusByStatAnnotation(String name);

}
