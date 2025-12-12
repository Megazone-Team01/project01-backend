package com.mzcteam01.mzcproject01be.domains.user.service;

import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import com.mzcteam01.mzcproject01be.domains.user.entity.UserRole;
import com.mzcteam01.mzcproject01be.domains.user.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public void create( String name ){
        userRoleRepository.findByName( name ).orElseThrow( () -> new CustomException("같은 이름의 Role이 존재합니다") );
        UserRole newRole = UserRole.builder().name( name ).build();
        userRoleRepository.save( newRole );
    }

    public Map<String, Integer> findAll(){
        Map<String, Integer> result = new HashMap<>();
        for( UserRole role : userRoleRepository.findAll() ) result.put( role.getName(), role.getId() );
        return result;
    }
}
