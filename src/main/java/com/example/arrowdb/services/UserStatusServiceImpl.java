package com.example.arrowdb.services;

import com.example.arrowdb.entity.UserStatus;
import com.example.arrowdb.repositories.UserStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserStatusServiceImpl implements UserStatusService {

    private final UserStatusRepository userStatusRepository;

    @Override
    @Transactional
    public UserStatus findStatusById(Integer id) {
        UserStatus userStatus = null;
        Optional<UserStatus> optional = userStatusRepository.findById(id);
        if(optional.isPresent()){
            userStatus = optional.get();
        }
        return userStatus;
    }

    @Override
    @Transactional
    public List<UserStatus> findAllUserStatus() {
        return userStatusRepository.findAll();
    }

    @Override
    @Transactional
    public UserStatus findUserStatusByStatAnnotation(String name) {
        return userStatusRepository.findUserStatusByStatAnnotation(name);
    }
}
