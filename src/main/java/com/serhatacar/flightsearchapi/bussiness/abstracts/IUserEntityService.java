package com.serhatacar.flightsearchapi.bussiness.abstracts;

import com.serhatacar.flightsearchapi.entity.UserEntity;

public interface IUserEntityService {
    boolean isUserExists(String username);
    boolean isUserNameValid(String username);
    UserEntity save(UserEntity userEntity);
}
