package com.serhatacar.flightsearchapi.bussiness.concrets;

import com.serhatacar.flightsearchapi.bussiness.abstracts.IRoleEntityService;
import com.serhatacar.flightsearchapi.entity.RoleEntity;
import com.serhatacar.flightsearchapi.repository.RoleEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleEntityManager implements IRoleEntityService {
    private final RoleEntityRepository roleEntityRepository;

    @Autowired
    public RoleEntityManager(RoleEntityRepository roleEntityRepository) {
        this.roleEntityRepository = roleEntityRepository;
    }

    @Override
    public RoleEntity findByName(String name) {
        Optional<RoleEntity> result = roleEntityRepository.findByName(name);

        RoleEntity role = null;
        if (result.isPresent()) {
            role = result.get();
        }
        return role;
    }
}
