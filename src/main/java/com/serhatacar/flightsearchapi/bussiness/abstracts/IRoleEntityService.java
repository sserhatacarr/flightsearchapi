package com.serhatacar.flightsearchapi.bussiness.abstracts;

import com.serhatacar.flightsearchapi.entity.RoleEntity;

public interface IRoleEntityService {
    RoleEntity findByName(String name);
}
