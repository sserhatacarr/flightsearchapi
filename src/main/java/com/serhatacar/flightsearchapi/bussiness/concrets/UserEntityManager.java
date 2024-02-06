package com.serhatacar.flightsearchapi.bussiness.concrets;

import com.serhatacar.flightsearchapi.bussiness.abstracts.IUserEntityService;
import com.serhatacar.flightsearchapi.entity.UserEntity;
import com.serhatacar.flightsearchapi.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserEntityManager implements IUserEntityService {
    private final UserEntityRepository userEntityRepository;

    @Autowired
    public UserEntityManager(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public boolean isUserExists(String username) {
        return userEntityRepository.existsByUsername(username);
    }

    @Override
    public boolean isUserNameValid(String username) {
        return !username.trim().isEmpty() && !username.contains(" ");
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        return userEntityRepository.save(userEntity);
    }
}
